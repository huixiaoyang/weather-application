##  md file about assigment 002 -Mobile application
This is my md file about assigment 002. My student number is 20151633 and my name is 杨孝辉. Here I will show that I have completed:
- Use the data from my chosen source to populate my version of the weather application and update the content  when the refresh button is pressed.
- Handle the network connection in case of loss and make sure that the application does not crash.


### Use the data from my chosen source to populate my version of the weather application and update the content  when the refresh button is pressed.
I update some code including onReceive function and DownloadUpdate class:

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

The result is this:
![image](https://github.com/huixiaoyang/weather-application/blob/master/demo_1.gif)

###  Handle the network connection in case of loss and make sure that the application does not crash.

I update the onReceive function:

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

When there is not Internet connection, the result is :
![image](https://github.com/huixiaoyang/weather-application/blob/master/demo_2.gif)





