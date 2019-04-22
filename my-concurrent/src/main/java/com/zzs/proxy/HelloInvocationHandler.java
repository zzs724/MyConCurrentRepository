package com.zzs.proxy;

import javax.sound.midi.Soundbank;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class HelloInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("sayHello".equals(method.getName())) {
            System.out.println("--"+ Arrays.toString(args));
        }
        return method.invoke(hello, args);
    }

    private Hello hello;

    public HelloInvocationHandler(Hello hello) {
        this.hello = hello;
    }

    public static void main(String[] args) {
        Hello hello = (Hello) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class<?>[]{Hello.class},
                new HelloInvocationHandler(new HelloImpl()));
        System.out.println(hello.sayHello("HelloImpl"));
    }
    public static void main2(String[] args) {
        Hello hello = new HelloImpl();
        InvocationHandler invocationHandler = new HelloInvocationHandler(hello);
        Hello proxy = (Hello) Proxy.newProxyInstance(hello.getClass().getClassLoader(),
                hello.getClass().getInterfaces(),
                invocationHandler);
        System.out.println(proxy.sayHello("HelloImpl"));
    }
}
