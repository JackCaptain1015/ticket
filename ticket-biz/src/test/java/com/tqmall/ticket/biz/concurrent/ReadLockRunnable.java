package com.tqmall.ticket.biz.concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by wurenzhi on 2017/05/20.
 */
public class ReadLockRunnable implements Runnable {

    private ReentrantReadWriteLock reentrantReadWriteLock;

    public ReadLockRunnable(ReentrantReadWriteLock reentrantReadWriteLock) {
        this.reentrantReadWriteLock = reentrantReadWriteLock;
    }

    @Override
    public void run() {
        reentrantReadWriteLock.readLock().lock();
        for (int i = 0; i < 20; i++) {
            System.out.println("ReadRunnable读锁正在写，因为读锁不互斥");
        }
        reentrantReadWriteLock.writeLock().lock();
        for (int i = 0; i < 20; i++) {
            System.out.println("ReadRunnable写锁正在写");
        }
        reentrantReadWriteLock.writeLock().unlock();

    }
}
