package com.tqmall.ticket.biz.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by wurenzhi on 2017/05/20.
 */
public class ConditionProviderRunnable implements Runnable {

    private Lock lock;

    private Condition consumerCondition;
    private Condition providerCondition;

    public ConditionProviderRunnable(Lock lock, Condition consumerCondition, Condition providerCondition) {
        this.lock = lock;
        this.consumerCondition = consumerCondition;
        this.providerCondition = providerCondition;
    }

    public ConditionProviderRunnable(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            System.out.println("provider一半");
            consumerCondition.signal();
            providerCondition.await();
            System.out.println("provider完");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
