package com.zzs.prodconsum;

import java.util.concurrent.TimeUnit;

public class ProConsumer_Demo {
    public static void main(String[] args) {
        Myresources myresource = new Myresources();
        while (true) {
            new Thread(() -> {
                myresource.prod();
            }).start();
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myresource.consum();
            }).start();
        }
    }
}

class Myresources {
    private boolean flag = false;
    private int num = 0;

    public synchronized void prod() {
        while (flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        num++;
        flag = true;
        System.out.println("生产--------");
        notify();

    }

    public synchronized void consum() {
        while (!flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        num--;
        flag = false;
        System.out.println("消费");
        notify();

    }

}