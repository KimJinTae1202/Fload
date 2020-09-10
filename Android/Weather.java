package com.example.dc_project;

import android.util.Log;


import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Weather {

    private final int BASE_DATE = 3;
    private final int BASE_TIME = 5;
    private final int NX = 7;
    private final int NY = 9;

    private final String[] uri = {
            "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey=",
            "RCJvefvvjSLjNvTo8MJE4IZF2kbnaBn0Xt%2BxyXgRzxaFapgo%2F%2BsUgtQtcDXpivNNJTtgwCFFi8XtSOM27K%2FAMg%3D%3D&pageNo=1&numOfRows=62&dataType=JSON",
            "&base_date=", "", "&base_time=", "", "&nx=", "", "&ny=", ""
    };
    private Calendar calBase = null;
    private int hour;
    private int lastBaseTime;

    public Weather(){
        calBase = Calendar.getInstance(); // 현재시간 가져옴
        calBase.set(Calendar.MINUTE, 0); // 분, 초 필요없음
        calBase.set(Calendar.SECOND, 0);
        hour = calBase.get(Calendar.HOUR_OF_DAY);
        lastBaseTime = getLastBaseTime(hour);
    }

    private int getLastBaseTime(int t) {
        if (t >= 0) {
            if (t < 2) {
                calBase.add(Calendar.DATE, -1);
                calBase.set(Calendar.HOUR_OF_DAY, 23);
                return 23;
            } else {
                calBase.set(Calendar.HOUR_OF_DAY, t - t% 3);
                return t - t % 3;
            }
        } else
            return -1;
    }

    private String getBaseTime() {
        if (lastBaseTime / 10 > 0) // 두자리수이면
            return lastBaseTime + "00";
        else // 한자리수이면
            return "0" + lastBaseTime + "00";
    }

    public WeatherSet getWeatherSet(String nx, String ny){
        String sUrl = new String();
        StringBuffer jsonHtml = new StringBuffer();
        JSONArray jsonArr = null;
        JSONObject jsonObj = null;
        String[] saAttribName = { "response", "body", "items" };
        WeatherSet ws = null;
        int pop = -1, sky = -1 ,t3h=-1, reh=-1;
        Double tmn=-1.0 ,tmx=-1.0;

        uri[BASE_DATE] = new SimpleDateFormat("yyyyMMdd").format(calBase.getTime());
        uri[BASE_TIME] = "0200";
        uri[NX] = nx;
        uri[NY] = ny;

        for (int i = 0; i < uri.length; i++)
            sUrl += uri[i];

        Log.e("url",sUrl);
        try {
            URL u=new URL(sUrl);
            URLConnection conn=u.openConnection();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                jsonHtml.append(line);
            }
            br.close();
            is.close();

            jsonObj = (JSONObject) JSONValue.parse(jsonHtml.toString());

            Log.e("test",jsonObj.toString());

            for (int i = 0; i < saAttribName.length; i++)
                jsonObj = (JSONObject) jsonObj.get(saAttribName[i]);
            jsonArr = (JSONArray) jsonObj.get("item");

            String base_time=getBaseTime();
            Log.e("base time",base_time);

            for (int i = 0; i < jsonArr.size(); i++) {
                JSONObject jobj = (JSONObject) jsonArr.get(i);
                if (((String) jobj.get("category")).equals("POP") && ((String) jobj.get("fcstTime")).equals(base_time))
                    pop = Integer.valueOf((String)jobj.get("fcstValue"));
                else if (((String) jobj.get("category")).equals("SKY") && ((String) jobj.get("fcstTime")).equals(base_time))
                    sky = Integer.valueOf((String)jobj.get("fcstValue"));
                else if (((String) jobj.get("category")).equals("T3H") && ((String) jobj.get("fcstTime")).equals(base_time))
                    t3h = Integer.valueOf((String)jobj.get("fcstValue"));
                else if (((String) jobj.get("category")).equals("REH") && ((String) jobj.get("fcstTime")).equals(base_time))
                    reh = Integer.valueOf((String)jobj.get("fcstValue"));
                else if (((String) jobj.get("category")).equals("TMN"))
                    tmn = Double.valueOf((String)jobj.get("fcstValue"));
                else if (((String) jobj.get("category")).equals("TMX"))
                    tmx = Double.valueOf((String)jobj.get("fcstValue"));
            }

            Date bd = calBase.getTime();
            ws = new WeatherSet(pop, sky, t3h,reh,tmn,tmx,bd);


        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return ws;
    }
}
