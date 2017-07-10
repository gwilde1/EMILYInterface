package android.app.com.emilyrobot;

/**
 * Created by jjjj222 on 4/21/17.
 */

public class Settings {
    static int detail_refresh_rate = 1000;
    static String cameraurl = "http://192.168.204.19:4747/mjpegfeed?640x480";
    static int low_battery_threshold = 20;
    static int low_wifi_threshold = 20;
    static int low_gps_threshold = 20;
    static boolean is_low_battery = false;
    static boolean is_connected = false;
    static boolean is_display_video = false;
    static String ip_address = "10.201.154.202";
    static String camera_ip_address = "0.0.0.0";
    static int port_number = 8000;
    static String status_message = "Disconnected";
    static String mode="Auto";

    static String userArm="DISARM";
    static String userMode="Manual";
    static boolean needToSend=false;

    static boolean is_play_low_battery_warning = false;
    static boolean is_play_low_wifi_warning = false;
    static boolean is_armed=false;
}
