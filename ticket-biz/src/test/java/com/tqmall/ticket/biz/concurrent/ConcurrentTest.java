package com.tqmall.ticket.biz.concurrent;

import com.alibaba.dubbo.common.utils.PojoUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Lists;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by wurenzhi on 2017/05/16.
 */
public class ConcurrentTest {

    @Test
    public void conTest() throws InterruptedException {
        /***
         * 实现三个窗口各买10张票功能（共30张票）
         */
        ResourceBean resourceBean = new ResourceBean();
        resourceBean.setCount(16);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);

        for (int i = 0; i < 4; i++) {
            new Thread(new MyRunnable(resourceBean,cyclicBarrier)).start();
        }

        Thread.sleep(3000);
    }

    @Test
    public void consumeTest() throws InterruptedException {
        /**
         * 不断产生数据，不断消费数据
         */
        Long begigTime = System.currentTimeMillis();
        Long endTime = 0L;
        Long millDiv = 0L;
        List intList = Lists.newArrayList();
        int j = 0;
        CyclicBarrier cy = new CyclicBarrier(3);
        Boolean isFlag = true;

        ConsumerRunable consumerRunable1 = new ConsumerRunable(intList, cy);
        consumerRunable1.setOpen(isFlag);
        ConsumerRunable consumerRunable2 = new ConsumerRunable(intList, cy);
        consumerRunable2.setOpen(isFlag);
        ConsumerRunable consumerRunable3 = new ConsumerRunable(intList, cy);
        consumerRunable3.setOpen(isFlag);

        Thread thread1 = new Thread(consumerRunable1);
        Thread thread2 = new Thread(consumerRunable2);
        Thread thread3 = new Thread(consumerRunable3);
        thread1.start();
        thread2.start();
        thread3.start();

        while(millDiv < 4000L ){
            consumerRunable1.setBeginIndex(j);
            consumerRunable1.setEndIndex(j+4);
            consumerRunable2.setBeginIndex(j+4);
            consumerRunable2.setEndIndex(j+8);
            consumerRunable3.setBeginIndex(j+8);
            consumerRunable3.setEndIndex(j+12);
            for (int i = j; i < j+12; i++) {
                intList.add(i);
            }
            j += 12;
            millDiv = System.currentTimeMillis() - begigTime;
            Thread.sleep(1000);
        }
    }

    @Test
    public void semaphoreTest() throws InterruptedException {
        Semaphore semaphore = new Semaphore(5);
        Integer count = 8;
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0 ; i < count ; i++) {
            executorService.execute(new SemaphoreRunnable(i,semaphore));
        }
        Thread.sleep(1000L);
    }

    @Test
    public void callableTest() throws InterruptedException {
        FutureTask<Integer> integerFutureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            executorService.execute(integerFutureTask);
        }
        Thread.sleep(3000L);
    }

    @Test
    public void cancelFutureTaskTest() throws InterruptedException, ExecutionException {
        Callable callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                System.out.println(Thread.currentThread().getName());
                return Thread.currentThread().getId();
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<Long>> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            Future submit = executorService.submit(callable);
            list.add(submit);
        }
        for (Future<Long> future : list ) {
            System.out.println("ID:"+future.get());
        }
    }

    @Test
    public void futureTaskTest() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        FutureTask<Integer> futureTask = new FutureTask(new FutureTaskCallable());
        Future<?> future = executorService.submit(futureTask);
        long now = System.currentTimeMillis();
        System.out.println(futureTask.get());
        System.out.println(System.currentTimeMillis() - now);
    }

    @Test
    public void futureTaskExecuteTest() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        FutureTask<Integer> futureTask = new FutureTask(new FutureTaskCallable());
        executorService.execute(futureTask);
        long now = System.currentTimeMillis();
        System.out.println(futureTask.get());
        System.out.println(System.currentTimeMillis() - now);
    }

    @Test
    public void threadParaticeOne() throws InterruptedException {
        /**
         * 子线程循环1次，接着主线程循环2次
         * 以上过程来回循环50次
         */
        ReentrantLock reentrantLock = new ReentrantLock(true);
        Thread thread = new Thread(new LoopRunnable(reentrantLock));
        thread.start();
        for (int i = 0; i < 50; i++) {
            reentrantLock.lock();
            try {
                for (int j = 0; j < 2; j++) {
                    System.out.println("主线程："+j);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }

        Thread.sleep(1000);
    }

    @Test
    public void ConditionTest() throws InterruptedException {
        Lock lock = new ReentrantLock(true);
        Condition providerCondition = lock.newCondition();
        Condition consumerCondition = lock.newCondition();
        Thread consumerThread = new Thread(new ConditionConsumerRunnable(lock, consumerCondition, providerCondition));
        Thread providerThread = new Thread(new ConditionProviderRunnable(lock, consumerCondition, providerCondition));
        consumerThread.start();
        providerThread.start();
        Thread.sleep(2000);


    }

    @Test
    public void reentrantReadWriteLockTest() throws InterruptedException {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        Thread readThread = new Thread(new ReadLockRunnable(reentrantReadWriteLock));
        Thread writeThread = new Thread(new WriteLockRunnable(reentrantReadWriteLock));
        writeThread.start();
        readThread.start();

        Thread.sleep(3000);
    }

    @Test
    public void testSout(){
        System.out.println(70%64);
    }
}
