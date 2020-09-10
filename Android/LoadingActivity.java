package com.example.dc_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapPoint;

import java.util.List;

public class LoadingActivity extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback {
    LocationManager lm;
    double longitude;
    double latitude;
    TMapGpsManager tmapgps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        startLoading();
    }

    private void startLoading() {
//        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(LoadingActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    0);
//            ActivityCompat.requestPermissions(LoadingActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
//            //안드로이드 사용자에게 위치 권한 사용설정 물어보는 조건문
//            Log.e("사용자권한","없음");
//        }else{
//            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            try {
//                Thread.sleep(4000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Double lon = location.getLongitude();
//            Double lat = location.getLatitude();
//            try {
//                Thread.sleep(4000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Log.e("lon lat",lon+"/"+lat);
//            tmapgps = new TMapGpsManager(this);
//            try {
//                Thread.sleep(4000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            tmapgps.setProvider(TMapGpsManager.GPS_PROVIDER);
//            try {
//                Thread.sleep(4000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            tmapgps.OpenGps();
//            try {
//                Thread.sleep(4000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            TMapPoint point = tmapgps.getLocation();
//            Log.e("point",point.toString());
//        }

//
//        latitude=35.230874;
//        longitude=129.0821744;
//        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(LoadingActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    0);
//            ActivityCompat.requestPermissions(LoadingActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
//            //안드로이드 사용자에게 위치 권한 사용설정 물어보는 조건문
//
//        } else {
//            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            if(location==null) {
//                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            }
//            if(location!=null){
//                longitude = location.getLongitude();
//                latitude = location.getLatitude();
//                Log.e("location",latitude+"/"+longitude);
//            }else{
//                    Log.e("location","location 객체 없음");
//                    latitude=35.256164;
//                    longitude=129.072966;
//            }
//        }

//        WeatherThread wt=new WeatherThread(latitude,longitude);
//        wt.start();

//        while(true){
//            if(wt.isDone()){
//                List weather_list=wt.getWeatherList();
//                pop= (int) weather_list.get(0);
//                sky= (String) weather_list.get(1);
//                t3h= (int) weather_list.get(2);
//                reh= (int) weather_list.get(3);
//                tmn= (double) weather_list.get(4);
//                tmx= (double) weather_list.get(5);
//                break;
//            }
//        }


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), TabActivity.class);
//                intent.putExtra("lat",latitude);
//                intent.putExtra("long",longitude);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }

    @Override
    public void onLocationChange(Location location) {
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        TMapPoint point = tmapgps.getLocation();
        Log.e("lat lon",lat+"/"+lon);

    }
}
