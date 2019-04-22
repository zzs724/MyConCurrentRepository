package com.zzs.threadlocal;


import java.util.concurrent.*;

public class ThreadLocalDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread myThread = new MyThread();

        ExecutorService executorService = Executors.newCachedThreadPool();
        FutureTask futureTask = new FutureTask(myThread);
        FutureTask futureTask2 = new FutureTask(myThread);
        executorService.execute(futureTask);
        executorService.execute(futureTask2);
        while (true) {
            if (futureTask.isDone()&&futureTask2.isDone()) {
                executorService.shutdown();
                System.out.println(futureTask.get());
                System.out.println(futureTask2.get());
                break;
            }
        }
//        ExecutorService executorService1 = new ThreadPoolExecutor(2,5,10,TimeUnit.SECONDS,new LinkedBlockingQueue<>(3),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
    }
}

class MyThread implements Callable<Integer> {
    private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();


    @Override
    public Integer call() throws Exception {
        threadLocal.set((int) (Math.random()*100));
        return threadLocal.get();
    }
}
