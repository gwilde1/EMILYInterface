package android.app.com.emilyrobot;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;

import static android.app.com.emilyrobot.HomeActivity.RightjoyStickAngle;
import static android.app.com.emilyrobot.HomeActivity.RightjoyStickStrength;
import static android.app.com.emilyrobot.HomeActivity.LeftjoyStickAngle;
import static android.app.com.emilyrobot.HomeActivity.LeftjoyStickStrength;
import static android.content.Context.SEARCH_SERVICE;

/**
 * Created by vinay on 4/20/2017.
 */

public class NetworkConnection extends AsyncTask<Void, Void, Void> {

    private final String ack_message = "OK from EMILY interface";

    String IPaddress;
    int Port;
    String response = "";


    public NetworkConnection(String IPaddress, int Port){
        this.IPaddress = IPaddress;
        this.Port = Port;

    }

    private JSONObject convertStringToJson(String data) throws JSONException {
        JSONObject jsondata = new JSONObject(data);

        return jsondata;
    }

    private void parseJSONdata(JSONObject jsonData){

        Iterator<String> keys_iter = jsonData.keys();

        while (keys_iter.hasNext()){

               String key = keys_iter.next();
               try {
                   Object value = jsonData.get(key);

                   Mydata.setCurrentStatus(key, value);


               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }



    }



    @Override
    protected void onPostExecute(Void aVoid) {
        Settings.is_connected = false;
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Socket socket = null;

        try{
            socket = new Socket(IPaddress, Port);

            OutputStream outputStream = socket.getOutputStream();

            byte[] buffer = new byte[2048];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();

            outputStream.write(ack_message.getBytes(), 0, ack_message.length());

			/*
             * notice: inputStream.read() will block if no data return
			 */
			int Rudderpwm=1500;
            int Thrustpwm=1500;
            boolean goDown=false;
            while (((bytesRead = inputStream.read(buffer)) != -1) && Settings.is_connected) {

                try {
                    JSONObject jsonData = convertStringToJson(new String(buffer));
                    //Log.i("Jsondata",jsonData.toString());

                    parseJSONdata(jsonData);

                    int RightStrength=HomeActivity.RightjoyStickStrength;
                    int RightAngle=HomeActivity.RightjoyStickAngle;
                    int LeftStrength=HomeActivity.LeftjoyStickStrength;
                    int LeftAngle=HomeActivity.LeftjoyStickAngle;
                    double Rightvalue=RightStrength*Math.cos(Math.toRadians(RightAngle));
                    double Leftvalue=LeftStrength*Math.sin(Math.toRadians(LeftAngle));

                    Rudderpwm=(int)(4*(Rightvalue)+1500);
                    Thrustpwm=(int)(4*(Leftvalue)+1500);
                    Rudderpwm = ((Rudderpwm+5)/10)*10;
                    Thrustpwm = ((Thrustpwm+5)/10)*10;
                    if (Thrustpwm<1500)
                        Thrustpwm=1500;
                    String returnArm="", returnMode="";
                    if(Settings.needToSend==true) {
                        returnArm = Settings.userArm;
                        returnMode=Settings.userMode;
                    }
                    else
                    {
                        if(Settings.is_armed==true)
                            returnArm="ARM";
                        else
                            returnArm="DISARM";
                        returnMode=Settings.mode;
                    }
                    String data=""+Rudderpwm+","+Thrustpwm+","+returnArm+","+returnMode;
                    outputStream.write(data.getBytes(), 0, data.length());
                    Settings.needToSend=false;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                    Settings.is_connected = false;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


}
