package com.timmy.apm_startup.starttask;

import com.timmy.startfast.task.AppStartTask;
import java.util.ArrayList;
import java.util.List;

public class TaskB extends AppStartTask {

    @Override
    public List<Class<? extends AppStartTask>> dependsOn() {
        List<Class<? extends AppStartTask>> dependsList = new ArrayList<>();
        dependsList.add(TaskA.class);
        return dependsList;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isRunOnMainThread() {
        return true;
    }
}
