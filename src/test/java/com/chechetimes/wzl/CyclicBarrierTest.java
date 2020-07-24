package com.chechetimes.wzl;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author:WangZhaoliang
 * Date:2020/7/4 12:16
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> {
            System.out.println("裁判到位");
            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("裁判吹了口哨");
        });
        Runner runner1 = new Runner("张三", cyclicBarrier);
        Runner runner2 = new Runner("李四", cyclicBarrier);
        Runner runner3 = new Runner("王五", cyclicBarrier);
        Runner runner4 = new Runner("sss", cyclicBarrier);

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.execute(runner1);
        executorService.execute(runner2);
        executorService.execute(runner3);
        executorService.execute(runner4);
        executorService.shutdown();
    }


    static class Runner implements Runnable {
        private String name;
        private CyclicBarrier cyclicBarrier;

        public Runner(String name, CyclicBarrier cyclicBarrier) {
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
        }


        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(name + "准备ok");
                cyclicBarrier.await();
                System.out.println(name + "开炮");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
