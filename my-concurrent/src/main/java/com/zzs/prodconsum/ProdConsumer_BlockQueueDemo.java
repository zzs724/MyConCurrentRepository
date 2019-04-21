package com.zzs.prodconsum;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用阻塞队列实现生产者消费者
 * volatile/CAS/atomicInteger/BlockQueue/线程通信
 */
public class ProdConsumer_BlockQueueDemo {
    public static void main(String[] args) {
        MyResourceS myResource = new MyResourceS(new ArrayBlockingQueue<>(10));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t生产线程启动");
            try {
                myResource.prod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Prod").start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t消费线程启动");
            System.out.println();
            System.out.println();
            try {
                myResource.consum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Consum").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("5秒时间到，停止活动");
        myResource.stop();
    }
}

class MyResourceS {
    private volatile boolean FLAG = true;//默认开启 进行生产
    private AtomicInteger atomicInteger = new AtomicInteger();
    private BlockingQueue<String> blockingQueue = null;

    public void prod() throws InterruptedException
    {
        String data = null;
        boolean retValue;
        while (FLAG) {
            data = atomicInteger.incrementAndGet()+"";
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (retValue)
            {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t大老板叫停，FLAG=false，生产结束");
    }

    public void consum() throws InterruptedException {
        String result = null;
        while (FLAG)
        {
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (null == result || result.equalsIgnoreCase("")) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t 超过2秒无物，消费退出");
                System.out.println();
                System.out.println();
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费队列" + result + "成功");
            System.out.println("-------------------------------------");
        }
    }

    public void stop(){
        this.FLAG = false;
    }

    public MyResourceS(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public MyResourceS() {
    }
}