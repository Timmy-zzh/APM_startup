package com.timmy.startfast.exectors;

import com.timmy.startfast.TLog;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工厂：
 * 提供两种线程池，cpu密集型和io密集型
 */
public class ExectorFactor {

    private static final int CPU_SIZE = Runtime.getRuntime().availableProcessors();
    //    private static final int CORE_SIZE = CPU_SIZE;
    private static final int CORE_SIZE = Math.max(2, Math.min(CPU_SIZE - 1, 5));
    private static final int MAX_POOL_SIZE = CORE_SIZE;

    private static volatile ExectorFactor mInstance;
    private final Executor cpuThreadPool;

    //1。单例模式
    public static ExectorFactor getInstance() {
        if (mInstance == null) {
            synchronized (ExectorFactor.class) {
                if (mInstance == null) {
                    mInstance = new ExectorFactor();
                }
            }
        }
        return mInstance;
    }

    public ExectorFactor() {
        TLog.d("CPU_SIZE:" + CPU_SIZE + " ,CORE_SIZE:" + CORE_SIZE);
        cpuThreadPool = new ThreadPoolExecutor(CORE_SIZE, MAX_POOL_SIZE,
                60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        Executors.newCachedThreadPool();
                    }
                });
    }

    public Executor getCpuThreadPool() {
        return cpuThreadPool;
    }

    public Executor getIoThreadPool() {
        return cpuThreadPool;
    }


}
