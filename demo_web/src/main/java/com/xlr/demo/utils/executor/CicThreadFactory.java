package com.xlr.demo.utils.executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xianliru on 2017/7/19.
 */
public class CicThreadFactory implements ThreadFactory {

        private int counter;
        private String name;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        public CicThreadFactory(String name) {
            this.name = name;
        }

        public Thread newThread(Runnable run) {
            counter = threadNumber.getAndIncrement();
            Thread t = new Thread(run, name + "-Thread-" + counter);
            return t;
        }
}

