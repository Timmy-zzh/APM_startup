package com.timmy.lib_startup;

import android.os.SystemClock;

public class ApmUtil {


    public static int getPageKey(Object page) {
        return page.hashCode();
    }

    public static long getRealTime() {
        return SystemClock.elapsedRealtime();
    }
}
