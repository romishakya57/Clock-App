package com.example.alarmclock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StopWatch extends AppCompatActivity {
    Chronometer chronometer;
    ImageButton btstart, btstop;

    private boolean isResume;
    Handler handler;
    long tMilliSec, tstart, tbuff,  tupdate = 0L;
    int sec, min, millisec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.stopwatch);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.alarm:
                        startActivity(new Intent(getApplicationContext(),AlarmClock.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.timer:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.stopwatch:

                        return true;

                }
                return false;
            }
        });

        chronometer = findViewById(R.id.chronometer);
        btstart = findViewById(R.id.btstart);
        btstop = findViewById(R.id.btstop);

        handler =new Handler();


        btstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isResume){
                    tstart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable,0);
                    chronometer.start();
                    isResume =true;
                    btstop.setVisibility(View.GONE);
                    btstart.setImageDrawable(getResources().getDrawable(
                            R.drawable.ic_baseline_pause_24
                    ));
                }
                else{
                    tbuff += tMilliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    isResume=false;
                    btstop.setVisibility(View.VISIBLE);
                    btstart.setImageDrawable(getResources().getDrawable(
                            R.drawable.ic_baseline_play_arrow_24
                    ));
                }
            }
        });
        btstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isResume){
                    btstart.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24));
                    tMilliSec = 0L;
                    tstart = 0L;
                    tbuff = 0L;
                    tupdate = 0L;
                    sec =0;
                    min = 0;
                    millisec = 0;
                    chronometer.setText("00:00:00");
                }
            }
        });
    }
    public  Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tstart;
            tupdate = tbuff + tMilliSec;
            sec = (int) (tupdate/1000);
            min = sec/60;
            sec =sec%60;
            millisec = (int) (tupdate%100);
            chronometer.setText(String.format("%02d",min)+ ":"
                    + String.format("%02d",sec) + ":" + String.format("%02d", millisec)
            );
            handler.postDelayed(this,60);

        }
    };


}