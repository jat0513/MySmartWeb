package com.smartmvc.proxy;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

public class ProxyTest {

    public static void main(String[] args) {
        Object proxy = ProxyFactory.createProxy(HelloImpl.class);
        System.out.println(proxy);


        Object proxy2 = Enhancer.create(HelloImpl.class, new HelloInterceptor());
        System.out.println(proxy2);

//        HelloImpl hello = (HelloImpl) Enhancer.create(HelloImpl.class, new HelloInterceptor());
//        hello.say();
//

        Callback[] callbacks = new Callback[]{NoOp.INSTANCE,new HelloInterceptor()};

         Enhancer enhancer = new Enhancer();
         enhancer.setSuperclass(HelloImpl.class);
//         enhancer.setCallback(new HelloInterceptor());
        enhancer.setCallbacks(callbacks);
         enhancer.setCallbackFilter(new CallbackFilter() {
             @Override
             public int accept(Method method) {
                  if ((method.getName().equals("toString") || method.getName().equals("hashCode"))) {
                      return 0;
                  }
                 return 1;
             }
         });
         Object proxy3 = enhancer.create();
         System.out.println(proxy3);
         HelloImpl hello = (HelloImpl) proxy3;
         hello.say();


//         HelloImpl hello2 = (HelloImpl) enhancer.create();
//         hello2.say();

    }


}

class ProxyFactory {
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<T> targetClass) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                System.out.println("Before:" + targetMethod.getName());
                Object result = methodProxy.invokeSuper(targetObject, methodParams);
                System.out.println("After:" + targetMethod.getName());
                return result;
            }
        });
    }
}

class HelloImpl {
    public void say() {
        System.out.println("hello world");
    }
}

class HelloInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
        System.out.println("Before:" + targetMethod.getName());
        Object result = methodProxy.invokeSuper(targetObject, methodParams);
        System.out.println("After:" + targetMethod.getName());
        return result;
    }
}