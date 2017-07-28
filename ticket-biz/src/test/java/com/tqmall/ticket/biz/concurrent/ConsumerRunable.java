package com.tqmall.ticket.biz.concurrent;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by wurenzhi on 2017/05/17.
 */
@Getter
@Setter
public class ConsumerRunable implements Runnable {

    private List<Integer> consumerList;

    private CyclicBarrier cyclicBarrier;

    private boolean isOpen;

    private Integer beginIndex;

    private Integer endIndex;

    public ConsumerRunable() {
    }

    public ConsumerRunable(List<Integer> consumerList,CyclicBarrier cyclicBarrier) {
        this.consumerList = consumerList;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        Integer oldSize = 0;
        while(isOpen){
            oldSize = consumerList.size();
            if (oldSize != consumerList.size()){
                try {
                    cyclicBarrier.await();

                    for (int i = beginIndex ; i < endIndex ; i++) {
                        System.out.println(Thread.currentThread().getName()+"当前消费："+ consumerList.get(i));
                    }
                    cyclicBarrier.await();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
