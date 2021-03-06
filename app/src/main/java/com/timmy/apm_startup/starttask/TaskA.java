package com.timmy.apm_startup.starttask;

import com.timmy.startfast.task.AppStartTask;

import java.util.List;

/**
 * A 任务在主线程中执行，
 * 并且没有生层节点任务依赖
 */
public class TaskA extends AppStartTask {

    @Override
    public List<Class<? extends AppStartTask>> dependsOn() {
        return super.dependsOn();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isRunOnMainThread() {
        return true;
    }
}
