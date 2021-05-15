package com.timmy.apm_startup;

import android.app.Application;
import android.content.Context;
import android.os.SystemClock;
import android.os.Trace;

import com.timmy.launchtrace.LaunchTrace;
import com.timmy.launchtrace.TLog;


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
        Trace.beginSection("applicaiton start");
        tastA();
        tastB();
        tastC();
        tastD();
        Trace.endSection();
        TLog.d("MyApplication onCreate:" + SystemClock.elapsedRealtime());
        LaunchTrace.getInstance().init(codeStartTime);
    }

    private void tastA() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tastB() {
        try {
            Thread.sleep(1050);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tastC() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tastD() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
