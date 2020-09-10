package com.example.dc_project;

import android.util.Log;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class WeatherThread extends Thread {
    private boolean isDone=false;
    double latitude;

    public boolean isDone() {
        return isDone;
    }

    double longitude;
    String x_code;
    String y_code;
    int pop;
    String sky;
    int t3h;
    int reh;
    Double tmn=0.0;
    Double tmx=0.0;

    public WeatherThread(double latitude,double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }
    @Override
    public void run() {
        LatXLngY lxly=new LatXLngY();
        lxly.convertGRID_GPS(this.latitude,this.longitude);
        x_code=Integer.toString((int)lxly.x);
        y_code=Integer.toString((int)lxly.y);

        WeatherSet ws=null;
        Weather weather=new Weather();
        ws=weather.getWeatherSet(x_code,y_code);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 정각");

//        Log.e("time_test",sdf.format(ws.getFcstDate()));
//        Log.e("pop_test",Integer.toString(ws.getPop()));//강수확률
//        Log.e("sky_test",ws.getSky());
//        Log.e("t3h_test",Integer.toString(ws.getT3h()));
//        Log.e("reh_test",Integer.toString(ws.getReh()));
//        Log.e("tmn_test",Double.toString(ws.getTmn()));
//        Log.e("tmx_test",Double.toString(ws.getTmx()));

        this.pop=ws.getPop();
        this.sky=ws.getSky();
        this.t3h=ws.getT3h();
        this.reh=ws.getReh();
        this.tmn=ws.getTmn();
        this.tmx=ws.getTmx();

        isDone=true;

    }

    public List getWeatherList(){
        List weather_list=new ArrayList();
        weather_list.add(pop);
        weather_list.add(sky);
        weather_list.add(t3h);
        weather_list.add(reh);
        weather_list.add(tmn);
        weather_list.add(tmx);
        return weather_list;
    }
}
