package com.tqmall.ticket.common;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Author: wurenzhi
 * Date:  16/9/19 下午7:54
 */
@Component
public class ThreadHandler {

    private static ExecutorService executorService = Executors.newCachedThreadPool(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    public void excute(Runnable command){
        executorService.execute(command);
    }
}
