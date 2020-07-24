package com.chechetimes.wzl;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author:WangZhaoliang
 * Date:2020/7/4 11:54
 */
public class CountDownLatchTest {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testWork() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Worker worker = new Worker("张三", 2000L, countDownLatch);
        Worker worker1 = new Worker("李四", 3000L, countDownLatch);
        System.out.println(LocalDateTime.now().format(dateTimeFormatter));
        worker.startWork();
        worker1.startWork();
        countDownLatch.await();
        System.out.println("两个人工作完毕" + LocalDateTime.now().format(dateTimeFormatter));
    }


    class Worker {

        private String name;
        private Long time;
        private CountDownLatch countDownLatch;

        public Worker(String name, Long time, CountDownLatch countDownLatch) {
            this.name = name;
            this.time = time;
            this.countDownLatch = countDownLatch;
        }

        public void startWork() {
            ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
            singleThreadExecutor.execute(() -> {
                System.out.println(name + "开始工作，当前时间：" + LocalDateTime.now().format(dateTimeFormatter));
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name + "工作结束，当前时间: " + LocalDateTime.now().format(dateTimeFormatter));
                countDownLatch.countDown();
            });
        }
    }


}

