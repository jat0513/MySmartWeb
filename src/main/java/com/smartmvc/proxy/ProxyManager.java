package com.smartmvc.proxy;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理器
 */
public class ProxyManager {

    /**
     * 根据目标类和代理连生成代理
     *
     * @param targetClass
     * @param proxyList
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<T> targetClass, final List<Proxy> proxyList) {
        Callback[] callbacks = new Callback[]{
                // NoOp 是指不做任何事情的拦截器
                NoOp.INSTANCE,
                // 拦截方法的切面类
                new MethodInterceptor() {
                    @Override
                    public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                        return new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList).doProxyChain();
                    }
                }
        };
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallbacks(callbacks);
        // 设置回调过滤器:根据规则匹配哪些方法拦截,哪些方法不拦截
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                // 不拦截 toString hashCode equals 方法
                String methodName = method.getName();
                if ((methodName.equals("toString") || methodName.equals("hashCode") || methodName.equals("equals"))) {
                    // callbacks[0]
                    return 0;
                }
                // callbacks[1]
                return 1;
            }
        });
        return (T) enhancer.create();
    }

}
