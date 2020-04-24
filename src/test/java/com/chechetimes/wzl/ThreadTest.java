package com.chechetimes.wzl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * author:WangZhaoliang
 * Date:2020/4/20 22:32
 */
public class ThreadTest {

    private static int num = 0;
    private static Object obj = new Object();

    static class Producter implements Runnable{
        private ArrayBlockingQueue blockingDeque;
        public Producter(ArrayBlockingQueue blockingDeque) {
            this.blockingDeque = blockingDeque;
        }

        @Override
        public void run() {
            try {
                while (num <= 100) {
                    synchronized (obj) {
                        num++;
                        blockingDeque.put(num);
                        System.out.println(Thread.currentThread().getName() + "生产了iphone SE2, 编号： " + num);
                    }
                    Thread.sleep(1000L);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer implements Runnable{
        private ArrayBlockingQueue<Object> blockingDeque;
        public Consumer(ArrayBlockingQueue<Object> blockingDeque) {
            this.blockingDeque = blockingDeque;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    int num = (int) blockingDeque.take();
                    System.out.println(Thread.currentThread().getName() + "====消费了iphone SE2, 编号： " + num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadFactory taskThreadFactory = new ThreadFactoryBuilder().setNameFormat("TaskManager-pool-%d").build();
        ExecutorService taskPool = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.MINUTES, new LinkedBlockingDeque<>(),
                taskThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(5);
        Producter producter = new Producter(blockingQueue);
        Producter producter2 = new Producter(blockingQueue);
        Producter producter3 = new Producter(blockingQueue);
        taskPool.submit(producter);
        taskPool.submit(producter2);
        taskPool.submit(producter3);

        Consumer consumer = new Consumer(blockingQueue);
        Consumer consumer1 = new Consumer(blockingQueue);
        Consumer consumer2 = new Consumer(blockingQueue);
        Consumer consumer3 = new Consumer(blockingQueue);
        Consumer consumer4 = new Consumer(blockingQueue);
        taskPool.submit(consumer);
        taskPool.submit(consumer1);
        taskPool.submit(consumer2);
        taskPool.submit(consumer3);
        taskPool.submit(consumer4);
    }

}
