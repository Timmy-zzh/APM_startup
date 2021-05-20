package com.timmy.startfast;

import android.os.SystemClock;

import com.timmy.startfast.runnable.AppStartRunnable;
import com.timmy.startfast.task.AppStartTask;
import com.timmy.startfast.topo.SortUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 应用冷启动优化框架：
 * 1。将所有启动初始化操作封装成Task，后面的任务调度以Task为粒度进行操作执行
 * 2。任务优先级问题：
 * -根据任务的依赖关系，进行拓扑排序，先执行优先级高的（入度为0）
 * -所有任务都是通过异步线程执行，在子线程执行过程中，要让底优先级的任务先进行等待，等高优先级任务执行完后，
 * --再通知后面依赖的任务进行执行 -- 使用CountDownLatch.await()方法进行控制（线程的共享模式）
 * 2.1.核心点：
 * -拓扑排序，确定依赖关系
 * -无依赖关系者，先进行阻塞，等待其他线程先执行完， CountDownLatch.await()
 * -有依赖关系着，前面的依赖者执行完成后，需要通知后面依赖的任务，CountDownLatch.countDown()阻塞计数器减少1
 * --等计数器减少为0时，该线程不再阻塞，继续执行
 * 3.线程池选择
 * -所有任务都是在子线程中执行，有的任务是CPU密集型，有的是IO密集型
 * -难点1：如何区分？ --根据SysTrace确定该任务执行是cpuTime的占比
 * -难理解2：知道了任务的类型，选择何种线程池呢？
 * --原则：不能阻塞住线程的执行
 */
public class AppStartFaster {

    private static volatile AppStartFaster mInstance;
    //保存所有需要执行的任务
    private List<AppStartTask> allExecutorTask;
    // Class 与 AppStartTask的关系
    Map<Class<? extends AppStartTask>, AppStartTask> classTaskMap = new HashMap<>();
    //临街表
    Map<Class<? extends AppStartTask>, List<Class<? extends AppStartTask>>> adj = new HashMap<>();
    private long startTime;
    private CountDownLatch mainThreadDownLatch;


    //1。单例模式
    public static AppStartFaster getInstance() {
        if (mInstance == null) {
            synchronized (AppStartFaster.class) {
                if (mInstance == null) {
                    mInstance = new AppStartFaster();
                }
            }
        }
        return mInstance;
    }

    public AppStartFaster() {
        allExecutorTask = new ArrayList<>();
    }

    public AppStartFaster addTask(AppStartTask startTask) {
        allExecutorTask.add(startTask);
        return this;
    }

    /**
     * 开始执行
     * 1。将所有任务根据依赖关系进行拓扑排序
     * 2。分发--将所有需要在子线程中执行的任务，放在线程池中进行执行
     */
    public AppStartFaster onStart() {
        startTime = SystemClock.elapsedRealtime();
        //根据原始任务队列，获取拓扑关系
        List<? extends AppStartTask> appStartTasks = SortUtil.topoSort(allExecutorTask, classTaskMap, adj);
        //找到需要在主线程执行的任务个数，然后每当一个任务执行完成后，CountDownLatch执行countDown方法
        mainThreadDownLatch = new CountDownLatch(1);

        //任务分发
        for (AppStartTask appStartTask : appStartTasks) {
            if (appStartTask.isRunOnMainThread()) {
                //普通方法
                new AppStartRunnable(appStartTask, this).run();
            } else {
                //交给线程池去调度
                appStartTask.executorOn().execute(new AppStartRunnable(appStartTask, this));
            }
        }
        return this;
    }

    /**
     * 通知该任务startTask的子节点任务，阻塞计数器减少
     */
    public void notifyChildNode(AppStartTask startTask) {
        List<Class<? extends AppStartTask>> depends = adj.get(startTask.getClass());
        //下层节点任务计数器减少
        for (Class<? extends AppStartTask> depend : depends) {
            classTaskMap.get(depend).onNotify();
        }
    }

    public void taskFinish(AppStartTask startTask) {
        TLog.d("执行完成：" + startTask.getClass().getSimpleName() + " diff:" + (SystemClock.elapsedRealtime() - startTime));
        if (startTask.isRunOnMainThread()) {
            mainThreadDownLatch.countDown();
        }
    }

    public void onWait() {
        try {
            if (mainThreadDownLatch != null) {
                mainThreadDownLatch.await(1000, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
