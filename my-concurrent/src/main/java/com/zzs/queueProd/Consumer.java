package com.zzs.queueProd;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {

    private BlockingQueue<String> queue;


    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override

    public void run() {
        System.out.println("消费者线程启动！");
        boolean isRunning = true;
        try {
            while (isRunning) {
                String data = queue.poll(2, TimeUnit.SECONDS);
                if (null != data) {
                    System.out.println("消费数据：" + data);
                    TimeUnit.SECONDS.sleep(1);
                } else {
                    isRunning = false;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("退出消费者线程！");
        }
    }
}
