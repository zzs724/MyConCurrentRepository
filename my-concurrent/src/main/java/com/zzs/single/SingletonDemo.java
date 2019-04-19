package com.zzs.single;

public class SingletonDemo
{
    private static volatile SingletonDemo singletonDemo ;

    public static SingletonDemo getSingleInstance(){
        if (singletonDemo == null){
            synchronized (SingletonDemo.class) {
                if (singletonDemo == null) {
                    singletonDemo = new SingletonDemo();
                }
            }
        }
        return singletonDemo;
    }

    public SingletonDemo() {
    }
}
