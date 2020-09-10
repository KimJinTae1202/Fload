package com.example.dc_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    TextView head_text;
    TextView sky_text;
    TextView temp_text;
    TextView highlow_text;
    Button map_button;
    Button http_button;
    Button tmap_button;
    ImageView imageView;

    ProgressBar progressbar;
    List<Address> address = null;
    Geocoder g = null;
    URL url;
    URLConnection conn;
    JSONParser parser;
    JSONArray jArr;
    JSONObject jobj;
    double longitude;
    double latitude;
    int pop;
    String sky;
    int t3h;
    int reh;
    int tmn;
    int tmx;
    String code = "";
    BufferedReader br;
    LocationManager lm;
    double x_code;
    double y_code;

    Calendar calBase;
    int hour;
    int lastBaseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        head_text=(TextView)findViewById(R.id.head_text);
        map_button = (Button) findViewById(R.id.map_button);
        http_button = (Button) findViewById(R.id.http_button);
        tmap_button=(Button)findViewById(R.id.tmap_button);
        imageView=(ImageView)findViewById(R.id.imageview);
        temp_text=(TextView)findViewById(R.id.temp_text);
        sky_text=(TextView)findViewById(R.id.sky_text);
        highlow_text=(TextView)findViewById(R.id.highlow_text);

        g = new Geocoder(this);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Intent intent=getIntent();
        latitude=intent.getExtras().getDouble("lat");
        longitude=intent.getExtras().getDouble("long");
        pop=intent.getExtras().getInt("pop");
        sky=intent.getExtras().getString("sky");
        t3h=intent.getExtras().getInt("t3h");
        reh=intent.getExtras().getInt("reh");
        tmn=(int)intent.getExtras().getDouble("tmn");
        tmx=(int)intent.getExtras().getDouble("tmx");
        set_address(latitude,longitude);

        String[] head_address=address.get(0).getAddressLine(0).split(" ");

        Log.e("위도 경도",latitude+"/"+longitude);
        Log.e("intent로 넘어온 pop", pop+"/"+sky+"/"+t3h+"/"+reh+"/"+tmn+"/"+tmx);

        Drawable sunny= getDrawable(R.drawable.sun);
        Drawable little_clouds=getDrawable(R.drawable.little_clouds);
        Drawable cloudy=getDrawable(R.drawable.cloud);
        Drawable rain=getDrawable(R.drawable.rain);
        Calendar cal=Calendar.getInstance();

        head_text.setText(head_address[1]+" "+head_address[2]+" "+head_address[3]);
        temp_text.setText(Integer.toString(t3h)+"\u00B0");
        sky_text.setText(sky);
        highlow_text.setText("\u2191"+Integer.toString(tmx)+"\u00B0"+"   "+"\u2193"+Integer.toString(tmn)+"\u00B0");
        //sky = Sunny, Little Clouds Cloudy, Rain

        if(sky=="Sunny")
            imageView.setImageDrawable(sunny);
        else if(sky=="Little Clouds")
            imageView.setImageDrawable(little_clouds);
        else if(sky=="Cloudy")
            imageView.setImageDrawable(cloudy);
        else
            imageView.setImageDrawable(rain);


//        ========================================================================================================
        http_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       //세번째 버튼 "위치 서버로 보내기"
                NetworkThread nt = new NetworkThread();     //NetworkThread 로 위치 보냄. 아직 몇번주기로, 비동기 구현, 스레드 풀 까진 구현안함
                nt.start();
            }
        });
        map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       //첫번째 버튼 "BUTTON"
                Log.d("ACTIVITY_LC", "MAP 호출");
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                startActivity(intent);
                //서브 액티비티로 인텐트 넘겨서 화면전환
            }
        });
        tmap_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TmapActivity.class);
                startActivity(intent);
            }
        });

    }

    public void set_address(double latitude, double longitude){
        try {
            address=g.getFromLocation(latitude,longitude,10);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//---------------------------------------------------------------------------------------------------------------------------------
    //사용자의 위치를 서버로 보내기 위해 스레드로 구현하였음
    class NetworkThread extends Thread {
        @Override
        public void run() {
        try {
            String sendMsg, receiveMsg;
            String str;

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        0);
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

            } else {
                Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                String provider = location.getProvider();
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                //여기까진 위와 동일

                String full_url="https://9a13cbbfe422.ngrok.io/?start_lat="+longitude+"&start_long="+latitude+"&arrive_lat="+x_code+"&arrive_long="+y_code;
                URL url = new URL(full_url);
                //URL 호출 : url+위도+경도+x좌표+y좌표

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");


                if (conn.getResponseCode() == conn.HTTP_OK) {
                    //이부분은 아직 테스트 중이라 안보셔도 될거같습니다~
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();


                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    Log.d("msg", receiveMsg);
                } else {
                    Log.d("http", "failed!!!!!!!!");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }

}
