package com.timmy.apm_startup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;

/**
 * Android启动优化项目
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("aljsfljad");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                test();
            }
        }, 2000);
    }

    private void test() {
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
    }
}