package com.timmy.apm_startup;

import android.app.Application;
import android.content.Context;
import android.os.SystemClock;
import android.os.Trace;

import com.timmy.apm_startup.starttask.TaskA;
import com.timmy.apm_startup.starttask.TaskB;
import com.timmy.apm_startup.starttask.TaskC;
import com.timmy.apm_startup.starttask.TaskD;
import com.timmy.apm_startup.starttask.TaskE;
import com.timmy.launchtrace.LaunchTrace;
import com.timmy.launchtrace.TLog;
import com.timmy.startfast.AppStartFaster;


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
        tastE();
        Trace.endSection();
        TLog.d("MyApplication onCreate:" + SystemClock.elapsedRealtime());
        LaunchTrace.getInstance().init(codeStartTime);

        long elapsedRealtime = SystemClock.elapsedRealtime();
        AppStartFaster.getInstance()
                .addTask(new TaskA())
                .addTask(new TaskB())
                .addTask(new TaskC())
                .addTask(new TaskD())
                .addTask(new TaskE())
                .onStart()
                .onWait();
        TLog.d("all task finish:" + (SystemClock.elapsedRealtime() - elapsedRealtime));
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
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tastC() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tastD() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tastE() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
