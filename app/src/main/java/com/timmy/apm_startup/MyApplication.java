package com.timmy.apm_startup;

import android.app.Application;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.timmy.lib_startup.StartupSdk;

public class MyApplication extends Application {

    private long codeStartTime;

    public MyApplication() {
        this.codeStartTime = SystemClock.elapsedRealtime();
        Log.d("APP", "MyApplication init:" + codeStartTime);
    }

    @Override
    protected void attachBaseContext(Context base) {
        Log.d("APP", "MyApplication attachBaseContext:" + SystemClock.elapsedRealtime());
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        Log.d("APP", "MyApplication onCreate:" + SystemClock.elapsedRealtime());
    }

    private void init() {
        //各种sdk的初始化
        StartupSdk.getInstance().init(codeStartTime);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
