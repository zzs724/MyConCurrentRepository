package com.zzs.queueProd;

import java.util.concurrent.*;

public class BlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingDeque<>(10);

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(producer);
        executorService.execute(consumer);

        TimeUnit.SECONDS.sleep(5);

        producer.stop();

        executorService.shutdown();
    }
}
