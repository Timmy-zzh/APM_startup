package com.timmy.launchtrace;

import android.os.SystemClock;

public class LaunchUtil {


    public static int getPageKey(Object page) {
        return page.hashCode();
    }

    public static long getRealTime() {
        return SystemClock.elapsedRealtime();
    }
}
