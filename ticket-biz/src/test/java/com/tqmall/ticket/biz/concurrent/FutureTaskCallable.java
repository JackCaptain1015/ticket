package com.tqmall.ticket.biz.concurrent;

import java.util.concurrent.Callable;

/**
 * Created by wurenzhi on 2017/05/22.
 */
public class FutureTaskCallable<T> implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        Integer j = 0;
        Thread.currentThread().sleep(1000);
        for (int i = 0; i < 10; i++) {
            j = i;
            System.out.println("j="+j);
        }
        Thread.currentThread().sleep(2000);
        return j;
    }
}
