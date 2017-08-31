package com.xlr.demo.utils.executor;

import java.util.concurrent.*;

/**
 * @Author: xlr
 * @Date: Created in 下午2:41 2017/8/31
 */
public class ThreadPool extends ThreadPoolExecutor {
    public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super( corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue );
    }

    public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue <Runnable> workQueue, ThreadFactory threadFactory) {
        super( corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory );
    }

    public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue <Runnable> workQueue, RejectedExecutionHandler handler) {
        super( corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler );
    }

    public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue <Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super( corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler );
    }

}
