package com.tqmall.ticket.biz.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by wurenzhi on 2017/05/20.
 */
public class ConditionConsumerRunnable implements Runnable {

    private Lock lock;

    private Condition consumerCondition;
    private Condition providerCondition;

    public ConditionConsumerRunnable(Lock lock, Condition consumerCondition, Condition providerCondition) {
        this.lock = lock;
        this.consumerCondition = consumerCondition;
        this.providerCondition = providerCondition;
    }

    public ConditionConsumerRunnable(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            System.out.println("consumer一半");
            consumerCondition.await();
            System.out.println("consumer完");
            providerCondition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
