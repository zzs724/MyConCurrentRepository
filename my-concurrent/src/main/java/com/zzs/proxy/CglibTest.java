package com.zzs.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibTest {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloImpl.class);
        enhancer.setCallback(new CglibProxyDemo());
        HelloImpl hello = (HelloImpl) enhancer.create();
        hello.sayHello("Hello");
        hello.sayHi("Hi");
        HelloImpl cgligbProxy = (HelloImpl) new Enhancer().create(new HelloImpl().getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("匿名内部类");
                method.invoke( new HelloImpl(),objects);
                System.out.println("匿名内部类");
                return null;
            }
        });
        cgligbProxy.sayHello("2222");
    }

}
