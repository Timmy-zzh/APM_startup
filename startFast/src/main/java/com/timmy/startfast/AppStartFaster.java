package com.timmy.startfast;

import com.timmy.startfast.task.AppStartTask;

import java.util.ArrayList;
import java.util.List;

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
    public AppStartFaster start() {
        //根据原始任务队列，获取拓扑关系

        return this;
    }

    /**
     * 通知该任务startTask的子节点任务，阻塞计数器减少
     */
    public void notifyChildNode(AppStartTask startTask) {
        //
    }

    public void taskFinish(AppStartTask startTask) {
        
    }
}
