package com.timmy.startfast.task;

import android.os.Process;

import com.timmy.startfast.exectors.ExectorFactor;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * 一个启动的行为抽象
 * 1。任务执行优先级
 * 2。刚开始执行的时候是需要阻塞等待的--CountDownLatch.await()
 * 3.设置任务的依赖关系，根据依赖任务的数量，设置阻塞等待的计数器 new CountDownLatch(n)
 * 4。父节点任务执行完成后，通知子节点任务，子节点任务阻塞计数器减少，CountDownLatch.countDown
 * 6.标记该任务是否在主线程执行，还是在子线程执行
 * --根据任务的类型（cpu/io）选择使用不同的线程池进行执行，这里由开发人员自我控制
 */
public abstract class AppStartTask implements ITaskInterface {

    private CountDownLatch countDownLatch = new CountDownLatch(dependsOn() == null ? 0 : dependsOn().size());

    public void onWait() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行耗时操作的方法
     */
    public abstract void run();

    public void onNotify() {
        countDownLatch.countDown();
    }


    @Override
    public int priority() {
        return Process.THREAD_PRIORITY_BACKGROUND;
    }

    @Override
    public Executor executorOn() {
        return ExectorFactor.getInstance().getIoThreadPool();
    }

    @Override
    public List<Class<? extends AppStartTask>> dependsOn() {
        return null;
    }

}
