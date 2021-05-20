package com.timmy.startfast.runnable;

import android.os.Process;

import com.timmy.startfast.AppStartFaster;
import com.timmy.startfast.task.AppStartTask;

/**
 * 该Runnable表示真正的执行：执行的是任务Task中的run方法
 * -在执行前先要进行任务的阻塞
 * -不阻塞的话，则会继续调用Task中的run方法
 * -run方法执行完成后，需要通知当前任务的子节点门，调用子节点的countDown方法
 * --由AppStartFaster进行获取任务的子节点，并进行调度
 */
public class AppStartRunnable implements Runnable {

    private AppStartTask startTask;
    private AppStartFaster appStartFaster;

    public AppStartRunnable(AppStartTask appStartTask, AppStartFaster appStartFaster) {
        this.startTask = appStartTask;
        this.appStartFaster = appStartFaster;
    }

    @Override
    public void run() {
        Process.setThreadPriority(startTask.priority());
        startTask.onWait();
        startTask.run();
        appStartFaster.notifyChildNode(startTask);
        //告诉框架当前任务完成了，主线程阻塞计数器减少
        appStartFaster.taskFinish(startTask);
    }
}
