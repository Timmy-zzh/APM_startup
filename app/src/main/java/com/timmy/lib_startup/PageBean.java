package com.timmy.lib_startup;

import android.util.Log;

/**
 * 该类封装了每个页面测速的功能
 * -页面创建时间
 * -页面
 */
public class PageBean {

    private long createTime;    //页面创建时间
    private long drawEndTime;   //界面绘制结束时间

    /**
     * 获取页面开始时间
     * -如果该页面之前打开过，则不可以再次赋值
     */
    public void onCreate() {
        if (createTime > 0) {
            return;
        }
        this.createTime = ApmUtil.getRealTime();
        //todo 是那个页面，每个页面都有对应的唯一key值
        TLog.d("页面创建时间：" + createTime);
    }

    public void onPageDrawEnd() {
        if (drawEndTime > 0) {
            return;
        }
        this.drawEndTime = ApmUtil.getRealTime();
        TLog.d("绘制结束时间：" + drawEndTime);
        TLog.d("绘制耗时：" + (drawEndTime - createTime));

    }
}
