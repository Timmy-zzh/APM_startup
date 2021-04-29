package com.timmy.apm_startup;


import com.timmy.lib_startup.TLog;

public class Util {

    public static void test() {
        int a = 1;
        int b = 2;
        int c = a + b;
        TLog.d( "test : " + c);
    }
}
