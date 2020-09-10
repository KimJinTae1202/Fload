package com.example.dc_project;

import java.util.Calendar;
import java.util.Date;

public class WeatherSet {
    private int pop;
    private int sky;
    private int t3h;
    private int reh;
    private Double tmn;
    private Double tmx;
    private Date baseDate = null;
    private Date fcstDate = null;

    public WeatherSet(int p, int s,int t3,int r, Double tm, Double tn, Date bd) {
        pop = p;
        sky = s;
        t3h = t3;
        reh = r;
        tmn = tm;
        tmx = tn;
        baseDate = bd;
        Calendar calBase = Calendar.getInstance();
        calBase.setTime(baseDate);
        calBase.add(Calendar.HOUR_OF_DAY, 4);
        fcstDate = calBase.getTime();
    }

    public int getT3h() {
        return t3h;
    }

    public int getReh() {
        return reh;
    }

    public Double getTmn() {
        return tmn;
    }

    public Double getTmx() {
        return tmx;
    }

    public int getPop() {
        return pop;
    }

    public void setT3h(int t3h) {
        this.t3h = t3h;
    }

    public void setReh(int reh) {
        this.reh = reh;
    }

    public void setTmn(Double tmn) {
        this.tmn = tmn;
    }

    public void setTmx(Double tmx) {
        this.tmx = tmx;
    }

    public void setPop(int p) {
        pop = p;
    }

    public int getSkyValue(){
        return sky;
    }

    public String getSky() {
        String retMsg = null;
        switch (sky) {
            case 1:
                retMsg = "Sunny";
                break;
            case 2:
                retMsg = "Little Clouds";
                break;
            case 3:
                retMsg = "Cloudy";
                break;
            case 4:
                retMsg = "Rain";
                break;
            default:
                retMsg = "Error";
                break;
        }
        return retMsg;
    }

    public Date getBaseDate() {
        return baseDate;
    }

    public Date getFcstDate() {
        return fcstDate;
    }

    public void setSky(int s) {
        sky = s;
    }
}