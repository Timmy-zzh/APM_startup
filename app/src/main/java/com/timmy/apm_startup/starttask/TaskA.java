package com.timmy.apm_startup.starttask;

import com.timmy.startfast.task.AppStartTask;

import java.util.List;

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
