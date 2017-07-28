package com.tqmall.ticket.biz.concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by wurenzhi on 2017/05/20.
 */
public class WriteLockRunnable implements Runnable {

    private ReentrantReadWriteLock reentrantReadWriteLock;

    public WriteLockRunnable(ReentrantReadWriteLock reentrantReadWriteLock) {
        this.reentrantReadWriteLock = reentrantReadWriteLock;
    }

    @Override
    public void run() {
        reentrantReadWriteLock.writeLock().lock();
        for (int i = 0; i < 2; i++) {
            System.out.println("WriteRunnable写锁正在写");
        }
        reentrantReadWriteLock.readLock().lock();
        for (int i = 0; i < 2; i++) {
            System.out.println("WriteRunnable此时写锁没有被释放又获取了读锁");
        }
        reentrantReadWriteLock.writeLock().unlock();
        for (int i = 0; i < 50; i++) {
            System.out.println("WriteRunnable获取读锁后释放了写锁，马上释放读锁");
        }
        reentrantReadWriteLock.readLock().unlock();
    }
}
