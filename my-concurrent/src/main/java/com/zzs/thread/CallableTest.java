package com.zzs.thread;

import java.util.concurrent.*;

public class CallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable myCallable = new MyCallable();

        FutureTask<String> futureTask = new FutureTask<>(myCallable);
        Thread thread = new Thread(futureTask, "AAAA");
        thread.start();
        System.out.println(futureTask.get());

        FutureTask<String> futureTask2 = new FutureTask<>(myCallable);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(futureTask2);
        while (true) {
            if (futureTask2.isDone()) {
                executorService.shutdown();
                System.out.println(futureTask2.get());
                break;
            }
        }

        ExecutorService executorService2 = Executors.newCachedThreadPool();

        Future<String> future = executorService2.submit(myCallable);

        executorService2.shutdown();
        System.out.println(future.get());

    }


}

class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        return Thread.currentThread().getName();
    }
}
