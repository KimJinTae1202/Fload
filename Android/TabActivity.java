package com.example.dc_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TabActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentSearch fragmentSearch;
    private FragmentToday fragmentToday;
    private FragmentCall fragmentCall;

    String sky;
    double longitude;
    double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        Intent intent=getIntent();
        sky=intent.getStringExtra("sky");
        latitude=intent.getDoubleExtra("lat",0);
        longitude=intent.getDoubleExtra("long",0);

        fragmentSearch= new FragmentSearch();
        Bundle bundle=new Bundle();
        bundle.putString("sky",sky);
        bundle.putDouble("lat",latitude);
        bundle.putDouble("long",longitude);
        fragmentSearch.setArguments(bundle);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentSearch).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            
            switch(item.getItemId())
            {
                case R.id.searchItem:
                    if(fragmentSearch==null){
                        fragmentSearch=new FragmentSearch();
                        fragmentManager.beginTransaction().add(R.id.frameLayout,fragmentSearch).commit();
                    }
                    if(fragmentSearch!=null)
                        fragmentManager.beginTransaction().show(fragmentSearch).commit();
                    if(fragmentCall!=null)
                        fragmentManager.beginTransaction().hide(fragmentCall).commit();
                    if(fragmentToday!=null)
                        fragmentManager.beginTransaction().hide(fragmentToday).commit();
                    break;
                case R.id.todayItem:
                    if(fragmentToday==null){
                        fragmentToday=new FragmentToday();
                        fragmentManager.beginTransaction().add(R.id.frameLayout,fragmentToday).commit();
                    }
                    if(fragmentSearch!=null)
                        fragmentManager.beginTransaction().hide(fragmentSearch).commit();
                    if(fragmentCall!=null)
                        fragmentManager.beginTransaction().hide(fragmentCall).commit();
                    if(fragmentToday!=null)
                        fragmentManager.beginTransaction().show(fragmentToday).commit();
                    break;
                case R.id.callItem:
                    if(fragmentCall==null){
                        fragmentCall=new FragmentCall();
                        fragmentManager.beginTransaction().add(R.id.frameLayout,fragmentCall).commit();
                    }
                    if(fragmentSearch!=null)
                        fragmentManager.beginTransaction().hide(fragmentSearch).commit();
                    if(fragmentCall!=null)
                        fragmentManager.beginTransaction().show(fragmentCall).commit();
                    if(fragmentToday!=null)
                        fragmentManager.beginTransaction().hide(fragmentToday).commit();
                    break;
            }
            return true;
        }
    }
}
