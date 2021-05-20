package com.timmy.apm_startup.starttask;

import com.timmy.startfast.task.AppStartTask;

import java.util.ArrayList;
import java.util.List;

public class TaskE extends AppStartTask {

    @Override
    public List<Class<? extends AppStartTask>> dependsOn() {
        List<Class<? extends AppStartTask>> dependsList = new ArrayList<>();
        dependsList.add(TaskC.class);
        dependsList.add(TaskB.class);
        return dependsList;
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
        return false;
    }
}
