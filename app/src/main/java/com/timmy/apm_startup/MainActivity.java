package com.timmy.apm_startup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;


import com.timmy.launchtrace.DrawSpeedView;
import com.timmy.launchtrace.LaunchTrace;
import com.timmy.launchtrace.LaunchUtil;
import com.timmy.launchtrace.TLog;

import java.util.concurrent.CountDownLatch;

/**
 * Android启动优化项目
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LaunchTrace.getInstance().onPageCreate(this);
        super.onCreate(savedInstanceState);
        long startTime = SystemClock.elapsedRealtime();
        TLog.d("MainActivity onCreate before: " + startTime);
//        setContentView(R.layout.activity_main);
        setContentView(DrawSpeedView.wrap(this, LaunchUtil.getPageKey(this), R.layout.activity_main));
        TLog.d("MainActivity onCreate after: " + SystemClock.elapsedRealtime());
        TLog.d("MainActivity onCreate diff: " + (SystemClock.elapsedRealtime() - startTime));

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