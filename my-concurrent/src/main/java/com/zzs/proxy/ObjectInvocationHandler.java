package com.zzs.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ObjectInvocationHandler implements InvocationHandler{

    private Object object;

    public Object bind(Object o) {
        this.object = o;
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("ObjectInvocationHandler....invoke...start...");
        Object obj = method.invoke(object, args);
        return obj;
    }

    public static void main(String[] args) {
        Hello hello = (Hello) new ObjectInvocationHandler().bind(new HelloImpl());
        System.out.println(hello.sayHello("Hahahaha"));
    }
}
