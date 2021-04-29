package com.timmy.lib_startup;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.timmy.apm_startup.BuildConfig;

import java.util.Arrays;

public class TLog {

    /****************************新增log打印方式****************************/
    private static final String TAG_DEFAULT = ">>StartUp<<";
    private static final int MSG_MAX_LENGTH = 4000;
    public static boolean isShowThread = false;
    public static boolean logEnable = BuildConfig.DEBUG;
    private static final char HORIZONTAL_LINE = '│';

    private static final int MIN_STACK_OFFSET = 2;

    public static void d(@Nullable Object object) {
        log(Log.DEBUG, "", toString(object));
    }

    public static void d(String tag, @Nullable Object object) {
        log(Log.DEBUG, tag, toString(object));
    }

    @NonNull
    private static String createMessage(@NonNull String message, @Nullable Object... args) {
        return args == null || args.length == 0 ? message : String.format(message, args);
    }

    private synchronized static void log(int priority, String tag, @NonNull String msg, @Nullable Object... args) {
        if (!logEnable) {
            return;
        }
        checkNotNull(msg);
        tag = TextUtils.isEmpty(tag) ? TAG_DEFAULT : tag;
        //长度控制，太多就不打印了，粗暴处理
        if (msg.length() > MSG_MAX_LENGTH) {
            msg = msg.substring(0, MSG_MAX_LENGTH);
        }
        String message = createMessage(msg, args);
        StringBuilder builder = new StringBuilder();

        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (isShowThread) {
            builder.append(HORIZONTAL_LINE + "Thread:" + Thread.currentThread().getName());
        }
        int stackOffset = getStackOffset(trace);
        int methodCount = 1;
        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            builder.append(" " + HORIZONTAL_LINE + " ")
                    .append(getSimpleClassName(trace[stackIndex].getClassName()))  //类名
                    .append(".")
                    .append(trace[stackIndex].getMethodName())   //方法名
                    .append(" ")
                    .append(" (")
                    .append(trace[stackIndex].getFileName())   //
                    .append(":")
                    .append(trace[stackIndex].getLineNumber())
                    .append(")");
        }
        builder.append(HORIZONTAL_LINE + " " + message);

        Log.println(priority, tag, builder.toString());
    }

    private static int getStackOffset(@NonNull StackTraceElement[] trace) {
        checkNotNull(trace);
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(TLog.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

    private static String getSimpleClassName(@NonNull String name) {
        checkNotNull(name);
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    /******************************工具方法***************************************/
    public static String toString(Object object) {
        if (object == null) {
            return "null";
        }
        if (!object.getClass().isArray()) {
            return object.toString();
        }
        if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        }
        if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        }
        if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        }
        if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        }
        if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        }
        if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        }
        if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        }
        if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        }
        if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        }
        return "Couldn't find a correct type for the object";
    }

    @NonNull
    private static <T> T checkNotNull(@Nullable final T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }

}
