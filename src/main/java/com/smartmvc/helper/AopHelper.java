package com.smartmvc.helper;

import com.smartmvc.annotation.Service;
import com.smartmvc.proxy.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 方法拦截助手类
 */
public final class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);

                LOGGER.info("set proxy bean:targetClass={},proxyClass={},proxyObject={}", targetClass.getName(), proxy.getClass(), proxy);
            }
        } catch (Exception e) {
            LOGGER.error("init aop helper failure", e);
        }
    }

    /**
     * 获取目标类集合
     *
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null && ! annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    /**
     * 获取代理类及其目标类集合之间的映射关系
     *
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        LOGGER.info("createProxyMap start:");

        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);

        LOGGER.info("createProxyMap end:");
        return proxyMap;
    }

    /**
     * 获取 Aspect 代理类及其目标类集合之间的映射关系
     *
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, Set<Class<?>>> addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        LOGGER.info("addAspectProxy start:");

        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }

        LOGGER.info("addAspectProxy end:");
        return proxyMap;
    }

    /**
     * 获取 Aspect 代理类及其目标类集合之间的映射关系
     *
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, Set<Class<?>>> addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        LOGGER.info("addTransactionProxy start:");

        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class, serviceClassSet);

        LOGGER.info("addTransactionProxy end:");
        return proxyMap;
    }

    /**
     * 获取目标类和代理类对象之间的映射关系
     *
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        LOGGER.info("createTargetMap start:");

        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClasset = proxyEntry.getValue();
            for (Class<?> targetClass : targetClasset) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }

        LOGGER.info("createTargetMap end:");
        return targetMap;
    }

}
