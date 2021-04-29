package com.timmy.apm_startup;

import android.app.Application;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.timmy.lib_startup.StartupSdk;
import com.timmy.lib_startup.TLog;

public class MyApplication extends Application {

    private long codeStartTime;

    public MyApplication() {
        // 返回系统启动到现在的毫秒数，包含休眠时间。
        this.codeStartTime = SystemClock.elapsedRealtime();

        TLog.d("MyApplication init:" + codeStartTime);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        TLog.d("MyApplication attachBaseContext:" + SystemClock.elapsedRealtime());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        //模拟各种sdk的初始化
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TLog.d("MyApplication onCreate:" + SystemClock.elapsedRealtime());
        StartupSdk.getInstance().init(codeStartTime);
    }

}
