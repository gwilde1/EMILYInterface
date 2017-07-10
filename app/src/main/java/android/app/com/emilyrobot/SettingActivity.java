package android.app.com.emilyrobot;

import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

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

        TextView cameraIpAddress = (TextView) findViewById(R.id.cameraIpAddress);
        cameraIpAddress.setText(Settings.camera_ip_address);

        TextView batteryThreshold = (TextView) findViewById(R.id.batteryThreshold);
        batteryThreshold.setText(Integer.toString(Settings.low_battery_threshold));

        TextView wifiThreshold = (TextView) findViewById(R.id.wifiThreshold);
        wifiThreshold.setText(Integer.toString(Settings.low_wifi_threshold));

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
