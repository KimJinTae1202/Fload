package com.example.dc_project;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.skt.Tmap.TMapCircle;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Handler;
import java.util.logging.LogManager;

import javax.xml.parsers.ParserConfigurationException;

public class FragmentSearch extends Fragment {
    LinearLayout edit_layout;
    EditText start;
    EditText arrive;
    ImageButton search_button;
    Button road_button;
    Button shut_button;
    ImageButton my_locate_button;
    ProgressBar progressBar;
    Button plus_button;
    Button minus_button;

    private TMapGpsManager gps = null;
    TMapPolyLine tMapPolyLine;
    TMapPoint tMapPointEnd;
    LinearLayout test_layout;

    List<Address> address = null;
    Geocoder g = null;
    ArrayList POIItem;
    TMapPOIItem item;
    Double latitude;
    Double longitude;
    int poi_value;
    TMapView tMapView;
    String arrive_text;
    boolean flag;
    boolean start_road;
    Handler replay_handler;
    ArrayList<TMapPoint> re_node_list;

    private static final int DIALOG_REQUEST_CODE = 1234;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_search,container,false);

        //================================================================================================================
        re_node_list=new ArrayList<TMapPoint>();
        re_node_list.add(new TMapPoint(35.232135, 129.084193));
        re_node_list.add(new TMapPoint(35.232093, 129.084389));
        re_node_list.add(new TMapPoint(35.2325113, 129.0844779));
        re_node_list.add(new TMapPoint(35.2329885, 129.0846413));
        re_node_list.add(new TMapPoint(35.2333445, 129.0847411));
        re_node_list.add(new TMapPoint(35.2332966, 129.0849021));
        re_node_list.add(new TMapPoint(35.2341953, 129.0851365));
        re_node_list.add(new TMapPoint(35.2340646, 129.0858302));
        re_node_list.add(new TMapPoint(35.2339299, 129.0866165));
        re_node_list.add(new TMapPoint(35.2353483, 129.0869899));
        re_node_list.add(new TMapPoint(35.2365889, 129.0873166));
        re_node_list.add(new TMapPoint(35.2366264, 129.0871103));
        re_node_list.add(new TMapPoint(35.2380016, 129.0872556));
        re_node_list.add(new TMapPoint(35.2379833 , 129.0869376));
        re_node_list.add(new TMapPoint(35.238968, 129.0869355));
        re_node_list.add(new TMapPoint(35.2399632 , 129.0869373));
        re_node_list.add(new TMapPoint(35.23994202, 129.087422));
        re_node_list.add(new TMapPoint(35.23989548, 129.0879029));
        re_node_list.add(new TMapPoint(35.23981384, 129.0885349));
        re_node_list.add(new TMapPoint(35.23958117, 129.0903363));
        re_node_list.add(new TMapPoint(35.2385329, 129.0903435));
        re_node_list.add(new TMapPoint(35.2378263, 129.0903478));
        re_node_list.add(new TMapPoint(35.23746, 129.090381));


        //================================================================================================================


        start=view.findViewById(R.id.start_text);
        arrive=view.findViewById(R.id.arrive_text);
        search_button=view.findViewById(R.id.search_button);
        road_button=view.findViewById(R.id.road_button);
        shut_button=view.findViewById(R.id.shut_button);
        edit_layout=view.findViewById(R.id.edit_layout);
        my_locate_button=view.findViewById(R.id.my_locate_button);
        progressBar=view.findViewById(R.id.progressBar);
        plus_button=view.findViewById(R.id.plus_button);
        minus_button=view.findViewById(R.id.minus_button);
        test_layout=view.findViewById(R.id.test_layout);

        flag=false;
        start_road=false;

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();


                }else{
                    Toast.makeText(getActivity(),"위치 바꼈는데 프로바이더가 없음",Toast.LENGTH_LONG).show();
                }
//                TMapPoint tp = new TMapPoint(latitude, longitude);
                tMapView.setLocationPoint(longitude,latitude);
                tMapView.setCenterPoint(longitude,latitude);
//

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= 23) { // Marshmallow
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                Log.e("위치 권한","허용 안됨");
            } else {

            }
        }else{
            Log.e("위치 권한","허용됨");
            Location mylocation =locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(mylocation!=null){
                latitude=mylocation.getLatitude();
                longitude=mylocation.getLongitude();
                Log.e("my lat lon",latitude+"/"+longitude);
                Log.e("PROVIDER TEST","GPS 프로바이더");
            }else{
                mylocation=locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                if(mylocation !=null){
                    latitude=mylocation.getLatitude();
                    longitude=mylocation.getLongitude();
                    Log.e("PROVIDER TEST","NET 프로바이더");
                    Log.e("my lat lon",latitude+"/"+longitude);
                } else{
                    latitude=35.2299034;
                    longitude=129.0912895;
                    Log.e("PROVIDER TEST","프로바이더가 없음");
                }
            }



        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

        g = new Geocoder(getContext());

        try {
            address=g.getFromLocation(latitude,longitude,10);
            String[] head_address=address.get(0).getAddressLine(0).split(" ");
//            start.setText(head_address[1]+" "+head_address[2]+" "+head_address[3]+" "+head_address[4]);
            start.setText("부산시 장전동 NC백화점 부산대점");
        } catch (IOException e) {
            e.printStackTrace();
        }

        LinearLayout linearLayoutTmap = (LinearLayout)view.findViewById(R.id.linearLayoutTmap);
        tMapView = new TMapView(getContext());
        tMapView.setSKTMapApiKey( "l7xxf4f33a79b8ad49cfbdf7e207020b0426" );
        linearLayoutTmap.addView( tMapView );

        //========================================================================
        setAll_Circle();


        TMapPoint arrive_point = new TMapPoint(35.23746, 129.090381);
        Bitmap bitmap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_my_location_black_24dp);
        TMapMarkerItem markerItem1 = new TMapMarkerItem();
        markerItem1.setIcon(bitmap1); // 마커 아이콘 지정
        markerItem1.setPosition(0.5f, 0.5f); // 마커의 중심점을 중앙, 하단으로 설정
        markerItem1.setTMapPoint( arrive_point ); // 마커의 좌표 지정
        markerItem1.setVisible(markerItem1.VISIBLE);
        markerItem1.setName("도착지"); // 마커의 타이틀 지정
        tMapView.addMarkerItem("markerItem1", markerItem1); // 지도에 마커 추가

        TMapPoint start_point = new TMapPoint(35.23746, 129.090381);
        Bitmap bitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_my_location_black_24dp);
        TMapMarkerItem markerItem2 = new TMapMarkerItem();
        markerItem2.setIcon(bitmap2); // 마커 아이콘 지정
        markerItem2.setPosition(0.5f, 0.5f); // 마커의 중심점을 중앙, 하단으로 설정
        markerItem2.setTMapPoint( start_point ); // 마커의 좌표 지정
        markerItem2.setVisible(markerItem2.VISIBLE);
        markerItem2.setName("도착지"); // 마커의 타이틀 지정
        tMapView.addMarkerItem("markerItem2", markerItem2); // 지도에 마커 추가

        tMapView.setCenterPoint(longitude, latitude);
        tMapView.setIconVisibility(true);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        tMapView.setMapPosition(TMapView.POSITION_NAVI);
        tMapView.setCompassMode(false); //나침반 모드

        tMapView.setZoomLevel(15);
//=========================================================================================

        int MapZoomLevel = tMapView.getZoomLevel();
        tMapView.setPOIRotate(true);
        Log.e("zoom level",Integer.toString(MapZoomLevel));
        //========================================================================
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(getContext(),"돋보기 클릭",Toast.LENGTH_SHORT);
                toast.show();
                arrive_text=arrive.getText().toString();

                if(arrive_text==null)
                    Toast.makeText(getActivity(), "도착지를 입력하세요",Toast.LENGTH_LONG).show();
                else{
                    Thread poi_thread=new Thread(){
                        @Override
                        public void run() {
                            try {
                                TMapData tmapdata=new TMapData();
                                POIItem=tmapdata.findAllPOI(arrive_text);

                                if(POIItem!=null){
                                    TMapPOIItem item= (TMapPOIItem) POIItem.get(0);

                                    Log.e("검색 주소 개수",Integer.toString(POIItem.size()));
//                                    Log.e("POI Item 테스트", item.getPOIName().toString()+"/"+
//                                            item.getPOIAddress().toString()+"/"+item.getPOIPoint().toString());

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParserConfigurationException e) {
                                e.printStackTrace();
                            } catch (SAXException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    poi_thread.start();
                    try {
                        poi_thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    show();
                }
            }
        });

        road_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "경로안내시작",Toast.LENGTH_LONG).show();
                plus_button.setVisibility(View.VISIBLE);

                Thread test_thread=new Thread(){
                    @Override
                    public void run() {
                        ArrayList<TMapPoint> node_list=new ArrayList<TMapPoint>();
                        node_list.add(new TMapPoint(35.232135, 129.084193));
                        node_list.add(new TMapPoint(35.232093, 129.084389));
                        node_list.add(new TMapPoint(35.2325113, 129.0844779));
                        node_list.add(new TMapPoint(35.2329885, 129.0846413));
                        node_list.add(new TMapPoint(35.2333445, 129.0847411));
                        node_list.add(new TMapPoint(35.2332966, 129.0849021));
                        node_list.add(new TMapPoint(35.2341953, 129.0851365));
                        node_list.add(new TMapPoint(35.2340646, 129.0858302));
                        node_list.add(new TMapPoint(35.2339299, 129.0866165));
                        node_list.add(new TMapPoint(35.2353483, 129.0869899));
                        node_list.add(new TMapPoint(35.2365889, 129.0873166));
                        node_list.add(new TMapPoint(35.2366264, 129.0871103));
                        node_list.add(new TMapPoint(35.2380016, 129.0872556));
                        node_list.add(new TMapPoint(35.2380115, 129.0874278));  //
                        node_list.add(new TMapPoint(35.2389779, 129.0874249));  //
                        node_list.add(new TMapPoint(35.23994202, 129.087422));
                        node_list.add(new TMapPoint(35.23989548, 129.0879029));
                        node_list.add(new TMapPoint(35.23981384, 129.0885349));
                        node_list.add(new TMapPoint(35.23958117, 129.0903363));
                        node_list.add(new TMapPoint(35.2385329, 129.0903435));
                        node_list.add(new TMapPoint(35.2378263, 129.0903478));
                        node_list.add(new TMapPoint(35.23746, 129.090381));

                        TMapPolyLine tpolyline=new TMapPolyLine();
                        tpolyline.setLineColor(Color.BLUE);
                        tpolyline.setLineWidth(2);

                        for(int i=0;i<node_list.size();i++)
                            tpolyline.addLinePoint(node_list.get(i));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        tMapView.setLocationPoint(35.232135, 129.084193);
                        tMapView.setCenterPoint(35.232135, 129.084193);
                        tMapView.setZoomLevel(15);
                        tMapView.addTMapPolyLine("ddd",tpolyline);
//                          가라 방식 여기까지

//                        TMapPoint tMapPointStart = new TMapPoint(latitude, longitude); // SKT타워(출발지)
//                        TMapPoint tMapPointEnd = new TMapPoint(35.23746, 129.090381); // N서울타워(목적지)
//
//                        try {
//                            TMapPolyLine tMapPolyLine = new TMapData().findPathData(tMapPointStart, tMapPointEnd);
//                            tMapPolyLine.setLineColor(Color.BLUE);
//                            tMapPolyLine.setLineWidth(2);
//                            tMapView.addTMapPolyLine("Line1", tMapPolyLine);
//
//                        }catch(Exception e) {
//                            e.printStackTrace();
//                        }

                    }
                };
                test_thread.start();
                try {
                    test_thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flag=true;

                edit_layout.setVisibility(View.INVISIBLE);
                shut_button.setVisibility(View.VISIBLE);
                road_button.setVisibility(View.INVISIBLE);
                my_locate_button.setVisibility(View.INVISIBLE);

                Thread repeat_road_thread=new Thread(){
                    @Override
                    public void run() {
                        while (flag){
                            Log.e("서버 통신 테스트","서버에 위치보내기 시작");
                            String full_url="https://388782973638.ngrok.io/?my_lat="+latitude+"&my_long="+longitude;

                            try {
                                String receiveMsg;
                                String str;
                                URL url = new URL(full_url);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                                conn.setRequestMethod("GET");

                                if (conn.getResponseCode() == conn.HTTP_OK) {
                                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                                    BufferedReader reader = new BufferedReader(tmp);
                                    StringBuffer buffer = new StringBuffer();

                                    while ((str = reader.readLine()) != null) {
                                        buffer.append(str);
                                    }
                                    receiveMsg = buffer.toString();
                                    if(receiveMsg.equals("replay")){
                                        flag=false;
                                        Log.e("응답메시지", receiveMsg);

                                        mHandler.sendEmptyMessage(0);
                                        tMapView.removeAllTMapPolyLine();
                                        TMapPolyLine tpolyline=new TMapPolyLine();
                                        tpolyline.setLineColor(Color.BLUE);
                                        tpolyline.setLineWidth(2);
                                        for(int i=0;i<re_node_list.size();i++)
                                            tpolyline.addLinePoint(re_node_list.get(i));
                                        tMapView.addTMapPolyLine("re_node",tpolyline);
                                        Log.e("스레드 쉬기 시작","쉰다");
                                        Thread.sleep(6000);
                                        Log.e("스레드 쉬기 끝","다쉬었다");
                                        mHandler.sendEmptyMessage(1);
//                                        flag=true;

                                    }else if(receiveMsg.equals("danger")){
                                        flag=false;
                                        dHandler.sendEmptyMessage(0);
//                                        tMapView.removeAllTMapPolyLine();
//                                        anim=new AlphaAnimation(0.0f,1.0f);
//                                        anim.setDuration(100);
//                                        anim.setStartOffset(40);
//                                        anim.setRepeatMode(Animation.REVERSE);
//                                        anim.setRepeatCount(20);
//                                        test_layout.startAnimation(anim);
//                                        MediaPlayer player=MediaPlayer.create(getContext(),R.raw.sound2);
//                                        player.start();
//                                        Thread.sleep(10000);
//                                        player.stop();
                                        Log.e("응답메시지", receiveMsg);
                                    }
                                    else if(receiveMsg.equals("safe")){
                                        Log.e("응답메시지", receiveMsg);
                                    }
                                } else {
                                    Log.d("http", "failed!!!!!!!!");
                                }
                                Thread.sleep(1000);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (ProtocolException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                };
                repeat_road_thread.start();
            }
        });

        shut_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=false;
                tMapView.removeAllTMapPolyLine();
                edit_layout.setVisibility(View.VISIBLE);
                shut_button.setVisibility(View.INVISIBLE);
                road_button.setVisibility(View.VISIBLE);
                my_locate_button.setVisibility(View.VISIBLE);
            }
        });
        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tMapView.MapZoomIn();
            }
        });
        minus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tMapView.MapZoomOut();
            }
        });

        return view;
    }


    public void show(){
        Bundle poi_item=new Bundle();
        poi_item.putParcelableArrayList("poi_item",POIItem);
        DialogFragment newFragment = new DialogFragmentExample();
        newFragment.setArguments(poi_item);
        newFragment.setTargetFragment(this, DIALOG_REQUEST_CODE );
        newFragment.show(getFragmentManager(), "dialog"); //"dialog"라는 태그를 갖는 프래그먼트를 보여준다.
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DIALOG_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String arrive_text = data.getExtras().getString("arrive_text");
                arrive.setText(arrive_text);
                poi_value=data.getIntExtra("item",0);
                Toast.makeText(getActivity(), arrive_text+"를 선택하셨습니다.",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void setAll_Circle(){

        ArrayList<TMapPoint> list_point=new ArrayList<TMapPoint>();
        list_point.add(new TMapPoint(35.2299034, 129.0912895));
        list_point.add(new TMapPoint(35.230946,129.091425));
        list_point.add(new TMapPoint(35.2407734,129.0927976));
        list_point.add(new TMapPoint(35.2315607,129.0888408));
        list_point.add(new TMapPoint(35.2344444,129.0892001));
        list_point.add(new TMapPoint(35.2311319,129.0889154));
        list_point.add(new TMapPoint(35.2291821,129.0891883));

        list_point.add(new TMapPoint(35.2303031,129.0890596));
        list_point.add(new TMapPoint(35.2334748,129.0892394));
        list_point.add(new TMapPoint(35.2324448,129.0889248));
        list_point.add(new TMapPoint(35.2292518,129.0892049));
        list_point.add(new TMapPoint(35.23354,129.0892515));
        list_point.add(new TMapPoint(35.2329861,129.0891244));
        list_point.add(new TMapPoint(35.2339325,129.0892903));
        list_point.add(new TMapPoint(35.2273317,129.0886136));

        list_point.add(new TMapPoint(35.2320217,129.08881));
        list_point.add(new TMapPoint(35.2289486,129.0907516));
        list_point.add(new TMapPoint(35.2349792,129.0890025));
        list_point.add(new TMapPoint(35.2351395,129.0881828));
        list_point.add(new TMapPoint(35.2352533,129.0875709));
        list_point.add(new TMapPoint(35.2415914,129.0929164));
        list_point.add(new TMapPoint(35.2416439,129.0924743));
        list_point.add(new TMapPoint(35.2305665,129.0875999));
        list_point.add(new TMapPoint(35.2303897,129.088601));
        list_point.add(new TMapPoint(35.2341745,129.087924));

        list_point.add(new TMapPoint(35.2337132,129.0878002));
        list_point.add(new TMapPoint(35.2311864,129.0871225));
        list_point.add(new TMapPoint(35.2326943,129.0875269));
        list_point.add(new TMapPoint(35.2322625,129.0874111));
        list_point.add(new TMapPoint(35.2318329,129.0872959));
        list_point.add(new TMapPoint(35.2346347,129.0880474));
        list_point.add(new TMapPoint(35.2332374,129.0876726));
        list_point.add(new TMapPoint(35.2311709,129.0876314));
        list_point.add(new TMapPoint(35.2375583,129.0880492));
        list_point.add(new TMapPoint(35.238037,129.0878723));
        list_point.add(new TMapPoint(35.2335293,129.0893018));
        list_point.add(new TMapPoint(35.2448829,129.0898168));
        list_point.add(new TMapPoint(35.2447142,129.0903505));

        list_point.add(new TMapPoint(35.2445578,129.0909102));
        list_point.add(new TMapPoint(35.24203,129.0929796));
        list_point.add(new TMapPoint(35.2448626,129.0939172));
        list_point.add(new TMapPoint(35.2433085,129.0931682));
        list_point.add(new TMapPoint(35.2274994,129.0899389));
        list_point.add(new TMapPoint(35.2342543,129.0903736));
        list_point.add(new TMapPoint(35.2343184,129.0903824));
        list_point.add(new TMapPoint(35.2313747,129.0914713));
        list_point.add(new TMapPoint(35.2300061,129.0913029));
        list_point.add(new TMapPoint(35.2431078,129.0931334));
        list_point.add(new TMapPoint(35.242549,129.093049));
        list_point.add(new TMapPoint(35.2363763,129.0884861));
        list_point.add(new TMapPoint(35.2365152,129.087722));



        for(int i=0; i<list_point.size();i++){
            String circle_id="circle"+Integer.toString(i+1);
            TMapCircle circle1= new TMapCircle();
            circle1.setCenterPoint( list_point.get(i));
            circle1.setRadius(20);
            circle1.setCircleWidth(2);
            circle1.setLineColor(Color.BLACK);
            circle1.setAreaColor(Color.RED);
            circle1.setAreaAlpha(150);

            tMapView.addTMapCircle(circle_id, circle1);
        }
    }
    final Handler mHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==0){
                Log.e("핸들러","들어옴");
                progressBar.setVisibility(View.VISIBLE);
                plus_button.setVisibility(View.INVISIBLE);
                minus_button.setVisibility(View.INVISIBLE);
            }else{
                progressBar.setVisibility(View.INVISIBLE);
                plus_button.setVisibility(View.VISIBLE);
                minus_button.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "안전한 경로 재탐색 완료하였습니다.",Toast.LENGTH_LONG).show();

                new Thread(){
                    @Override
                    public void run() {
                        boolean arrive_flag=false;
                        while(!arrive_flag){
                            try {
                                Log.e("서버 통신 테스트","서버에 위치 다시 다시!! 보내기 시작");
                                String full_url="https://388782973638.ngrok.io/?my_lat="+latitude+"&my_long="+longitude;
                                String receiveMsg;
                                String str;
                                URL url = new URL(full_url);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                                conn.setRequestMethod("GET");
                                if (conn.getResponseCode() == conn.HTTP_OK) {
                                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                                    BufferedReader reader = new BufferedReader(tmp);
                                    StringBuffer buffer = new StringBuffer();

                                    while ((str = reader.readLine()) != null) {
                                        buffer.append(str);
                                    }
                                    receiveMsg = buffer.toString();
                                    if(receiveMsg.equals("going"))
                                        flag=false;
                                    else if(receiveMsg.equals("arrive")){
                                        Toast.makeText(getActivity(), "목적지에 도착하셨습니다.",Toast.LENGTH_LONG).show();
                                        flag=true;
                                    }
                                    Thread.sleep(1500);
                                }else{
                                    Log.e("서버 통신 테스트","서버에 위치 다시 다시!! 보내기 실패");
                                }

                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }

        }
    };

    final Handler dHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==0){
                new AnimThread().start();
                new RingThread().start();
            }
        }
    };
    class AnimThread extends Thread{
        @Override
        public void run() {
            Animation anim=new AlphaAnimation(0.0f,1.0f);
            anim.setDuration(100);
            anim.setStartOffset(40);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(40);
            test_layout.startAnimation(anim);
        }
    }
    class RingThread extends Thread{
        @Override
        public void run() {
            MediaPlayer player=MediaPlayer.create(getContext(),R.raw.sound2);
            player.start();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            player.stop();
        }
    }
}
