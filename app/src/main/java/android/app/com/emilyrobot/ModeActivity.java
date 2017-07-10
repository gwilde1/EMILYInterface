package android.app.com.emilyrobot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/*
public class ModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Get the Intent that started this activity and extract the string
        //Intent intent = getIntent();
        //String message = intent.getStringExtra(HomeActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        //TextView textView = (TextView) findViewById(R.id.textView);
        //textView.setText(message);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                saveSettings();
                finish();
            }
        });

        //TextView cameraIpAddress = (TextView) findViewById(R.id.cameraIpAddress);
        //cameraIpAddress.setText(Settings.camera_ip_address);

        Spinner spinner = (Spinner) findViewById(R.id.arm_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.armedDisarmed_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        TextView batteryThreshold = (TextView) findViewById(R.id.batteryThreshold);
        batteryThreshold.setText(Integer.toString(Settings.low_battery_threshold));


    }

    private void leaveSetting() {
        //onBackPressed();
    }

    private void saveSettings() {
        TextView cameraIpAddress = (TextView) findViewById(R.id.cameraIpAddress);
        Settings.camera_ip_address = cameraIpAddress.getText().toString();

        TextView batteryThreshold = (TextView) findViewById(R.id.batteryThreshold);
        Settings.low_battery_threshold = Integer.parseInt(batteryThreshold.getText().toString());

        TextView wifiThreshold = (TextView) findViewById(R.id.wifiThreshold);
        Settings.low_wifi_threshold = Integer.parseInt(wifiThreshold.getText().toString());
    }
}
*/


public class ModeActivity extends AppCompatActivity {

    private Spinner armspinner;
    private Spinner modespinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        // Get the Intent that started this activity and extract the string
        //Intent intent = getIntent();
        //String message = intent.getStringExtra(HomeActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        //TextView textView = (TextView) findViewById(R.id.textView);
        //textView.setText(message);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                saveSettings();
                finish();
            }
        });

        armspinner = (Spinner) findViewById(R.id.arm_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterArm = ArrayAdapter.createFromResource(this,
                R.array.armedDisarmed_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterArm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        armspinner.setAdapter(adapterArm);
        if(Settings.is_armed==true)
            armspinner.setSelection(adapterArm.getPosition("ARM"));
        else
            armspinner.setSelection(adapterArm.getPosition("DISARM"));


        modespinner = (Spinner) findViewById(R.id.mode_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterMode = ArrayAdapter.createFromResource(this,
                R.array.mode_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        modespinner.setAdapter(adapterMode);
        modespinner.setSelection(adapterMode.getPosition(Settings.mode));

        /*
        TextView cameraIpAddress = (TextView) findViewById(R.id.cameraIpAddress);
        cameraIpAddress.setText(Settings.camera_ip_address);

        TextView batteryThreshold = (TextView) findViewById(R.id.batteryThreshold);
        batteryThreshold.setText(Integer.toString(Settings.low_battery_threshold));

        TextView wifiThreshold = (TextView) findViewById(R.id.wifiThreshold);
        wifiThreshold.setText(Integer.toString(Settings.low_wifi_threshold));*/

    }

    private void leaveSetting() {
        //onBackPressed();
    }

    private void saveSettings() {
        Settings.userArm=armspinner.getSelectedItem().toString();
        Settings.userMode=modespinner.getSelectedItem().toString();
        Settings.needToSend=true;
    }
}
