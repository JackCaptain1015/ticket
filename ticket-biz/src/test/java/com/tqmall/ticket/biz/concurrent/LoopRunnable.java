package com.tqmall.ticket.biz.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wurenzhi on 2017/05/20.
 */
public class LoopRunnable implements Runnable {

    private ReentrantLock reentrantLock;

    public LoopRunnable(ReentrantLock reentrantLock) {
        this.reentrantLock = reentrantLock;
    }

    public LoopRunnable() {
    }

    @Override
    public void run() {
        for (int j = 0; j < 50; j++) {
            reentrantLock.lock();
            try {
                for (int i = 0; i < 1; i++) {
                    System.out.println("子线程循环："+i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                reentrantLock.unlock();
            }
        }
    }
}
