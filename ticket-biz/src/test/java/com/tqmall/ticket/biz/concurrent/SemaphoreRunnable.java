package com.tqmall.ticket.biz.concurrent;

import java.util.concurrent.Semaphore;

/**
 * Created by wurenzhi on 2017/05/18.
 */
public class SemaphoreRunnable implements Runnable {

    private Integer count;
    private Semaphore semaphore;

    public SemaphoreRunnable(Integer count, Semaphore semaphore) {
        this.count = count;
        this.semaphore = semaphore;
    }

    public SemaphoreRunnable() {
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName()+"消费"+count);
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
