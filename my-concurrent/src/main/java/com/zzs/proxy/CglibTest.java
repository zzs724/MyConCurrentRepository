package com.zzs.proxy;

import net.sf.cglib.proxy.Enhancer;

public class CglibTest {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloImpl.class);
        enhancer.setCallback(new CglibProxyDemo());
        HelloImpl hello = (HelloImpl) enhancer.create();
        hello.sayHello("Hello");
        hello.sayHi("Hi");
    }
}
