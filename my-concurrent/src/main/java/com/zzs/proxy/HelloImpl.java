package com.zzs.proxy;

public class HelloImpl implements Hello{
    @Override
    public String sayHello(String string) {
        return "-->"+string;
    }
}
