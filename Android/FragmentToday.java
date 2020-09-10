package com.example.dc_project;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.skt.Tmap.TMapView;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;


public class FragmentToday extends Fragment {
    Button ring_button;
    Boolean red_flag=false;
    LinearLayout test_layout;
    Animation anim=null;
    TMapView tMapView;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_today,container,false);

        test_layout=view.findViewById(R.id.test_layout);
        LinearLayout linearLayoutTmap = (LinearLayout)view.findViewById(R.id.linearLayoutTmap);
        tMapView = new TMapView(getContext());
        tMapView.setSKTMapApiKey( "l7xxf4f33a79b8ad49cfbdf7e207020b0426" );
        linearLayoutTmap.addView( tMapView );

        final Thread ani_thread=new Thread(){
            @Override
            public void run() {
                anim=new AlphaAnimation(0.0f,1.0f);
                anim.setDuration(100);
                anim.setStartOffset(40);
                anim.setRepeatMode(Animation.REVERSE);
                anim.setRepeatCount(20);
                test_layout.startAnimation(anim);
            }
        };
        Thread ring_thread=new Thread(){
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
        };



        ani_thread.start();
        ring_thread.start();


        return view;
    }
}
