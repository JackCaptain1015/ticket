package com.tqmall.ticket.biz.concurrent;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by wurenzhi on 2017/05/16.
 */
@Getter
@Setter
public class MyRunnable implements Runnable{

    private CyclicBarrier cyclicBarrier;
    private ResourceBean resourceBean;
    public MyRunnable(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public MyRunnable(ResourceBean resourceBean) {
        this.resourceBean = resourceBean;
    }

    public MyRunnable(ResourceBean resourceBean,CyclicBarrier cyclicBarrier) {
        this.resourceBean = resourceBean;
        this.cyclicBarrier = cyclicBarrier;
    }

    public MyRunnable() {
    }


    @Override
    public void run() {
        while(resourceBean.getCount() > 0){
            try {
                System.out.println(Thread.currentThread().getName()+"目前有"+resourceBean.getCount()+"张票");
                cyclicBarrier.await();
                resourceBean.setCount(resourceBean.getCount() - 1);
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName()+"卖出一张票后，目前有"+resourceBean.getCount()+"张票");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
