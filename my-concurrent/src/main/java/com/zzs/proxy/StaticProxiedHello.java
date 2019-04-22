package com.zzs.proxy;

public class StaticProxiedHello implements Hello{
    private Hello hello = new HelloImpl();
    @Override
    public String sayHello(String string) {
        return hello.sayHello(string);
    }

    public static void main(String[] args) {
        StaticProxiedHello staticProxiedHello = new StaticProxiedHello();
        System.out.println(staticProxiedHello.sayHello("HelloImpl"));
    }
}
