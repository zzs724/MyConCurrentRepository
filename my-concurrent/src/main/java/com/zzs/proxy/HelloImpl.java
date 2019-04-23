package com.zzs.proxy;

public class HelloImpl implements Hello{
    @Override
    public String sayHello(String string) {
        System.out.println("sayHello-->"+string);
        return null;
    }
    final public String sayHi(String string) {
        System.out.println("sayHi-->"+string);
        return null;
    }
}
