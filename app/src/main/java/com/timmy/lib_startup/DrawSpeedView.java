package com.timmy.lib_startup;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

public class DrawSpeedView extends FrameLayout {

    private final int pageKey;

    /**
     * 将 Activity的原始界面上，添加一层ViewGroup
     */
    public static View wrap(Context context,int pageKey, int layoutId){
        DrawSpeedView wrapView = new DrawSpeedView(context,pageKey);
        View childView = LayoutInflater.from(context).inflate(layoutId, null);
        wrapView.addView(childView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        return wrapView;
    }

    public DrawSpeedView(@NonNull Context context, int pageKey) {
        super(context);
        this.pageKey = pageKey;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        TLog.d("DrawSpeedView-dispatchDraw:" + ApmUtil.getRealTime());
        StartUpTrace.getInstance().onPageDrawEnd(this.pageKey);
    }
}
