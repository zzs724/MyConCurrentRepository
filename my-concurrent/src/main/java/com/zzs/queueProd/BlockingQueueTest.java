package com.zzs.queueProd;

import java.util.concurrent.*;

public class BlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingDeque<>(10);
//        BlockingQueue<String> queue = new SynchronousQueue<>();

        Producer producer = new Producer(queue);
        Producer producer1 = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(producer);
        executorService.execute(producer1);
        executorService.execute(consumer);

        TimeUnit.SECONDS.sleep(5);

        producer.stop();
        producer1.stop();

        executorService.shutdown();
    }
}
