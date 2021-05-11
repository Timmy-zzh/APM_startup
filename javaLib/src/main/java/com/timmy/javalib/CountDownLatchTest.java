package com.timmy.javalib;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    private static int size = 5;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(size);
        long startTime = System.currentTimeMillis();

        System.out.println("----start----startTime:" + startTime);
        //启动五个子线程
        for (int i = 0; i < size; i++) {
            new WorkThread(countDownLatch).start();
        }

        System.out.println("1111");
        //让主线程等待
        countDownLatch.await();
        long diff = System.currentTimeMillis() - startTime;
        //继续执行主线程后面的操作
        System.out.println(" main thread do something  -- diff:" + diff);
    }

    private static class WorkThread extends Thread {
        private final CountDownLatch countDownLatch;

        public WorkThread(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " do consumer 1000ms ");
                //子线程执行完毕，CountDownLatch的计数器减少
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
