package com.timmy.startfast.task;

import android.os.Process;

import java.util.List;
import java.util.concurrent.Executor;

import androidx.annotation.IntRange;

public interface ITaskInterface {

    /**
     * 设置任务执行优先级
     */
    @IntRange(from = Process.THREAD_PRIORITY_FOREGROUND, to = Process.THREAD_PRIORITY_BACKGROUND)
    int priority();

    /**
     * 返回该任务执行的线程池
     */
    Executor executorOn();

    /**
     * 确定该任务依赖于那些任务，返回当前任务的父节点
     */
    List<Class<? extends AppStartTask>> dependsOn();

    /**
     * 标记是否在主线程中执行
     */
    boolean isRunOnMainThread();

}
