##  md file about assigment 001 -Mobile application
This is my md file about assigment 001. My student number is 20151633 and my name is 杨孝辉. Here I will show that I have completed:
- adding the new icon to the application,
- adding the blue background,
- adding the refresh button,when the button is pressed,the temperature,the date and the day of the week are all updated.

### adding the new icon to the app
I put a picture named "icon.png" ![image](https://github.com/huixiaoyang/weather-application/blob/master/icon.png)that I search on the Internet into the drawable folder. Then I modify AndroidManifest.xml.

    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android" 
    
    package="mg.studio.weatherappdesign">
   
    <uses-permission
    
    android:name="android.permission.INTERNET"/>
    <application
        android:allowBackup="true"
        android:icon="@drawable/icon"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/Theme.AppCompat.NoActionBar">
        <activity android:name=".MainActivity"
            android:screenOrientation="portrait">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
    </manifest>

In the follow picture, the bule icon of WeatherApplication is that I used.
![image](E://myicon.JPG)

### adding the blue backgroung
I create a xml file named "textview_shape.xml".

    <?xml version="1.0" encoding="utf-8"?>
    <shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="oval"
    android:useLevel="false">
    
    <solid android:color="#31a7dc" />
    <corners android:radius="360dp" />
    <padding
        android:bottom="1dp"
        android:left="1dp"
        android:right="1dp"
        android:top="1dp" />
    <size 
        android:width="15dp"
        android:height="15dp" />
    </shape>


Then I modify the TextView.

    <TextView
        android:layout_width="48dp"
        android:layout_height="48dp"
        android:gravity="center"
        android:text="mon"
        android:background="@drawable/textview_shape"
        android:textAllCaps="true"
        android:textColor="#909090" />
        
The result is the follow picture:
![image](E://add_blue_bg.jpg)


### adding the refresh button,when the button is pressed,the temperature,the date and the day of the week are all updated.
       
First, I put a picture named "imagebtn_update.png" ![image](E://imagebtn_update.png)into drawable folder.Then, add code in suittable position in "activity_main.xml".

    <LinearLayout
        android:id="@+id/linearLayout_update"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_toRightOf="@+id/linearLayout"
        android:gravity="center_vertical"
        android:orientation="vertical">

        <Button
        android:id="@+id/button_update"
        android:layout_width="30dp"
        android:layout_height="30dp"
        android:background="@drawable/imagebtn_update"
        android:onClick="btnClick" />
    </LinearLayout>
Next,add android:id="@+id/curweekday" in the TextView which shows "SUNDAY".
    
Next, I modify the btnClick function in "MainActivity.java".

    import java.text.SimpleDateFormat;
    import java.util.Date;
    public void btnClick(View view) {
        new DownloadUpdate().execute();
        
        SimpleDateFormat formatter_date = new SimpleDateFormat("MM/dd/yyyy ");
        Date curDate = new Date(System.currentTimeMillis());
        String str= formatter_date.format(curDate);
        ((TextView) findViewById(R.id.tv_date)).setText(str);

        SimpleDateFormat formatter_week = new SimpleDateFormat("EEEE");
        Date curWeek = new Date(System.currentTimeMillis());
        String str2= formatter_week.format(curWeek);
        ((TextView) findViewById(R.id.curweekday)).setText(str2);
    }
Finally, run this application:

![image](E://press_before.JPG)

Press the refresh button. We will find:

![image](E://press_after.JPG)





