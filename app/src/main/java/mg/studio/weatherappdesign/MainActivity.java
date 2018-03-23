package mg.studio.weatherappdesign;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import java.util.Date;
import java.text.SimpleDateFormat;
import org.json.*;
import java.lang.String;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public  void onReceive(Context context) {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo  mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo  wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            Toast.makeText(getApplicationContext(), "please check your Internet", Toast.LENGTH_LONG).show();
        }else {
            new DownloadUpdate().execute();

            SimpleDateFormat formatter_date = new SimpleDateFormat("MM/dd/yyyy ");
            Date curDate = new Date(System.currentTimeMillis());
            String str= formatter_date.format(curDate);
            ((TextView) findViewById(R.id.tv_date)).setText(str);

            SimpleDateFormat formatter_week = new SimpleDateFormat("EEEE");
            Date curWeek = new Date(System.currentTimeMillis());
            String str2= formatter_week.format(curWeek);
            ((TextView) findViewById(R.id.curweekday)).setText(str2);
            ((TextView) findViewById(R.id.tv_location)).setText("chongqing");
        }
    }

    public void btnClick(View view) {
        Context context = getApplicationContext();
        onReceive(context);
    }
    
    private class DownloadUpdate extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            //String stringUrl = "http://mpianatra.com/Courses/info.txt";
            String stringUrl ="https://api.seniverse.com/v3/weather/now.json?key=snjr8qqdaam5soy1&location=chongqing&language=zh-Hans&unit=c";
            HttpURLConnection urlConnection = null;
            BufferedReader reader;

            try {
                URL url = new URL(stringUrl);

                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Mainly needed for debugging
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                String  strWeatherData=buffer.toString();
                try {
                    JSONObject jsonWeatherData = new JSONObject(strWeatherData);
                    String Temperature=jsonWeatherData.getJSONArray("results").getJSONObject(0).getJSONObject("now").getString("temperature");
                    //The temperature
                    return Temperature;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String temperature) {
            //Update the temperature displayed
            ((TextView) findViewById(R.id.temperature_of_the_day)).setText(temperature);
        }
    }
}
