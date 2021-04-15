package com.timmy.apm_startup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;

/**
 * Android启动优化项目
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("APP", "MainActivity onCreate:" + SystemClock.elapsedRealtime());

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                test();
//            }
//        }, 2000);
    }

    private void test() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Trace.beginSection("123");
        }
        Debug.startMethodTracing("testTrace");
        System.out.println("----testTrace----");
        for (int i = 0; i < 100; i++) {
            Log.d("APM", "item:" + i);
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Debug.stopMethodTracing();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Trace.endSection();
        }
    }
}