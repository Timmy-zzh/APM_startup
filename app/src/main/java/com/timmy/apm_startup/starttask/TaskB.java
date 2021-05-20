package com.timmy.apm_startup.starttask;

import com.timmy.startfast.task.AppStartTask;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务B在任务A之后执行
 */
public class TaskB extends AppStartTask {

    @Override
    public List<Class<? extends AppStartTask>> dependsOn() {
        List<Class<? extends AppStartTask>> dependsList = new ArrayList<>();
        dependsList.add(TaskA.class);
        dependsList.add(TaskC.class);
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
