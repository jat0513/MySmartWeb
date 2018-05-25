package com.smartmvc.proxy;

import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理链
 */
public class ProxyChain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyChain.class);

    private final Class<?> targetClass;
    private final Object targetObject;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] methodParams;

    private List<Proxy> proxyList = new ArrayList<Proxy>();

    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    /**
     * 递归调用
     *
     * @return
     * @throws Throwable
     */
    public Object doProxyChain() throws Throwable {
        Object methodResult;
        if (proxyIndex < proxyList.size()) {
            LOGGER.info(String.format("doProxyChain.doProxy start:proxyIndex=%d,%s.%s", proxyIndex, targetClass.getName(), targetMethod.getName()));
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
            LOGGER.info(String.format("doProxyChain.doProxy end:proxyIndex=%d,%s.%s", proxyIndex, targetClass.getName(), targetMethod.getName()));
        } else {
            LOGGER.info(String.format("doProxyChain.invokeSuper start:proxyIndex=%d,%s.%s", proxyIndex, targetClass.getName(), targetMethod.getName()));
            methodResult = methodProxy.invokeSuper(targetObject, methodParams);
            LOGGER.info(String.format("doProxyChain.invokeSuper end:proxyIndex=%d,%s.%s", proxyIndex, targetClass.getName(), targetMethod.getName()));
        }
        return methodResult;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }
}
