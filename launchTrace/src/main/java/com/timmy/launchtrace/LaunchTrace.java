package com.timmy.launchtrace;

import android.os.SystemClock;
import android.util.SparseArray;

/**
 * app 启动耗时监控sdk
 * 1。监听应用启动耗时
 * -从Application的构造函数开始计时，比从Application的onCreate方法开始更加准确
 * --因为在Application的 attachBaseContext方法中会进行其他的加载操作
 * 2。监听页面渲染时间
 * -主要时setContentView() 方法的耗时，
 * <p>
 * <p>
 * // 返回系统启动到现在的毫秒数，包含休眠时间。
 * SystemClock.elapsedRealtime();
 * <p>
 * 3。TODO ：获取各种启动耗时，并将数据上传到后台
 */
public class LaunchTrace {

    private static volatile LaunchTrace mInstance;
    private long codeStartTime;
    private SparseArray<PageBean> activityPages;

    public static LaunchTrace getInstance() {
        if (mInstance == null) {
            synchronized (LaunchTrace.class) {
                if (mInstance == null) {
                    mInstance = new LaunchTrace();
                }
            }
        }
        return mInstance;
    }

    public LaunchTrace() {
        activityPages = new SparseArray<>();
    }

    /**
     * 耗时统计监控框架sdk初始化
     * -获取Application从初始化到调用onCreate方法的耗时
     *
     * @param codeStartTime ：Applicaiton初始化的时间
     */
    public void init(long codeStartTime) {
        this.codeStartTime = codeStartTime;
        //Application的初始化时间
        TLog.d("Application启动耗时: " + (SystemClock.elapsedRealtime() - codeStartTime));
    }

    /**
     * 页面开始时间：
     * -以该页面对象的hasncode为key值，创建耗时统计封装类
     */
    public void onPageCreate(Object page) {
        int pageKey = LaunchUtil.getPageKey(page);
        //判断配置项中是否需要测速该页面
        PageBean pageBean = activityPages.get(pageKey);
        if (pageBean == null) {
            pageBean = new PageBean();
            pageBean.onCreate();
            activityPages.put(pageKey, pageBean);
        }
    }

    /**
     * 根据key，找到给页面的PageBean，并设置绘制结束时间
     */
    public void onPageDrawEnd(int pageKey) {
        PageBean pageBean = activityPages.get(pageKey);
        pageBean.onPageDrawEnd();
    }
}
