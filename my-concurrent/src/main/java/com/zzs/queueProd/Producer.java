package com.zzs.queueProd;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {

    private volatile boolean isRunning = true;

    private BlockingQueue queue;
    private static AtomicInteger atomicInteger = new AtomicInteger();

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    public void run() {
        String data = null;
        System.out.println("生产者线程启动！");


        try {
            while (isRunning)
            {
                Thread.sleep(1000);
                data = "data: " + atomicInteger.incrementAndGet();
                //放入队列
                if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.out.println("放入数据失败");
                } else {
                    System.out.println("生产数据：" + data);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("退出生产者线程！");
        }

    }
}
