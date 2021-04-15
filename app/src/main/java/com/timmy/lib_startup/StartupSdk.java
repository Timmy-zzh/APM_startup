package com.timmy.lib_startup;

/**
 * app 启动耗时监控sdk
 * 1。监听应用启动耗时
 * -从Application的构造函数开始计时，比从Application的onCreate方法开始更加准确
 * --因为在Application的 attachBaseContext方法中会进行其他的加载操作
 * 2。监听页面渲染时间
 * -主要时setContentView() 方法的耗时，
 */
public class StartupSdk {

    private static volatile StartupSdk mInstance;
    private long codeStartTime;

    public static StartupSdk getInstance() {
        if (mInstance == null) {
            synchronized (StartupSdk.class) {
                if (mInstance == null) {
                    mInstance = new StartupSdk();
                }
            }
        }
        return mInstance;
    }

    public void init(long codeStartTime) {
        this.codeStartTime = codeStartTime;
    }

    public void onPageCreate(Object page){

    }

}
