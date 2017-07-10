package android.app.com.emilyrobot;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class HomeActivity extends AppCompatActivity {
    TextView statusMessage;

    Handler h;
    Integer tmp = 30;
    NetworkConnection client;
    ImageButton connectButton;
    ImageButton cameraButton;
    MediaPlayer mPlayer;
    SurfaceView cameraDisplay;

    private SurfaceView cameraPreview;

    private TextView mTextViewAngleLeft;
    private TextView mTextViewStrengthLeft;

    private TextView mTextViewAngleRight;
    private TextView mTextViewStrengthRight;

    public static int RightjoyStickAngle;
    public static int RightjoyStickStrength;

    public static int LeftjoyStickAngle;
    public static int LeftjoyStickStrength;

    public static Spinner Armspinner;
    public static ArrayAdapter<CharSequence> adapter;

    private static final boolean useHttpCamera = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_home);

        Toolbar mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.low_wifi);
        statusMessage = (TextView) findViewById(R.id.statusMessage);
        connectButton = (ImageButton) findViewById(R.id.connectButton);
        cameraButton = (ImageButton) findViewById(R.id.cameraButton);


        connectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!Settings.is_connected) {
                    showConnectButtonPopupAlert();

                } else {
                    stopConnection();
                }
            }
        });

        if (useHttpCamera) {

            cameraDisplay = new HttpCameraPreview(this,300, 300);

        }else{
            cameraDisplay = (SurfaceView) findViewById(R.id.cameraview);
        }




        cameraButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



                if (Settings.is_display_video) {

                    cameraDisplay.setVisibility(View.INVISIBLE);


                    Settings.is_display_video = false;
                } else {
                    cameraDisplay.setVisibility(View.VISIBLE);
                    Settings.is_display_video = true;
                }

            }
        });



        h = new Handler();


        h.postDelayed(new Runnable(){
            public void run(){
                tmp--;
                if (tmp < 0) {
                    tmp = 30;
                }
                //Mydata.setBatteryStatusForDev(tmp);


                if (Settings.is_low_battery) {
                    Settings.status_message = "Warning: Low Battery !!!";
                }

                if (Settings.is_play_low_battery_warning) {
                    Settings.is_play_low_battery_warning = false;

                    mPlayer.start();
                }


                if (Settings.is_play_low_wifi_warning) {
                    Settings.is_play_low_wifi_warning = false;

                if(!mPlayer.isPlaying())
                    mPlayer.start();
                }else{
                    if(mPlayer.isPlaying())
                        mPlayer.stop();
                }

                if(!Settings.is_connected)
                    stopConnection();
                statusMessage.setText(Settings.status_message);
                //do something
                h.postDelayed(this, Settings.detail_refresh_rate);

            }
        }, Settings.detail_refresh_rate);



        //mTextViewAngleLeft = (TextView) findViewById(R.id.textView_angle_left);
        //mTextViewStrengthLeft = (TextView) findViewById(R.id.textView_strength_left);

        JoystickView joystickLeft = (JoystickView) findViewById(R.id.joystickView_left);
        joystickLeft.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
               // mTextViewAngleLeft.setText(angle + "°");
               // mTextViewStrengthLeft.setText(strength + "%");
                LeftjoyStickAngle=angle;
                LeftjoyStickStrength=strength;

            }
        });


        //mTextViewAngleRight = (TextView) findViewById(R.id.textView_angle_right);
        //mTextViewStrengthRight = (TextView) findViewById(R.id.textView_strength_right);

        JoystickView joystickRight = (JoystickView) findViewById(R.id.joystickView_right);
        joystickRight.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                //mTextViewAngleRight.setText(angle + "°");
                //mTextViewStrengthRight.setText(strength + "%");

                RightjoyStickAngle=angle;
                RightjoyStickStrength=strength;
            }
        });
        /*
        Armspinner = (Spinner) findViewById(R.id.arm_spinner);
        Armspinner.setPrompt("ARM/DISARM");

        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.armedDisarmed_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        Armspinner.setAdapter(adapter);
        Armspinner.setSelection(1);
        Armspinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        */
    }
    /*
    public  void updateArmSpinner(boolean value)
    {
        List<String> list = new ArrayList<String>();
        list.add("ARM");
        list.add("DISARM");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        adapter.notifyDataSetChanged();
        if(value==true)
            Armspinner.setSelection(0);
        else
            Armspinner.setSelection(1);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }



    private void showConnectButtonPopupAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("IP address:Port number");

        // Set up the input
        final EditText input = new EditText(HomeActivity.this);
        input.setText(Settings.ip_address+":"+Settings.port_number);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String result[] = input.getText().toString().split(":");
                String ip_address = result[0];
                int port = Integer.parseInt(result[1]);
                Log.i("ip:port",ip_address+","+port);
                setupConnection(ip_address,port);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void setupConnection(String ip, int port) {
        Settings.ip_address = ip;
        Settings.port_number = port;
        Settings.status_message = ip;
        statusMessage.setText(ip);
        Settings.is_connected = true;

        connectButton.setBackgroundResource(R.drawable.connected_48);

        client = new NetworkConnection(Settings.ip_address,Settings.port_number);
        client.execute();
    }

    public void stopConnection() {
        Settings.is_connected = false;
        Settings.status_message = "Disconnected";
        statusMessage.setText("Disconnected");

        connectButton.setBackgroundResource(R.drawable.disconnected_48);
    }





    public void openSettingsPage(View view) {
        Intent intent = new Intent(this, SettingActivity.class);

        startActivity(intent);
    }

    public void openModePage(View view){
        Intent intent = new Intent(this, ModeActivity.class);

        startActivity(intent);
    }
}
