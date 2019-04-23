package com.zzs.proxy;

public class HelloImpl implements Hello{
    @Override
    public String sayHello(String string) {
        System.out.println("-->"+string);
        return null;
    }
}
