package com.smartmvc.helper;

import com.alibaba.fastjson.JSON;
import com.smartmvc.annotation.Action;
import com.smartmvc.bean.Handler;
import com.smartmvc.bean.Request;
import com.smartmvc.util.ArrayUtil;
import com.smartmvc.util.ClassUtil;
import com.smartmvc.util.CollectionUtil;
import com.smartmvc.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHelper.class);

    /**
     * 用于存放请求与处理器的映射关系(简称 Action Map)
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Action.class)) {
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);
                                    ACTION_MAP.put(request, handler);

                                    LOGGER.info("add action map:request={},handler={}", JsonUtil.toJson(request), JsonUtil.toJson(handler));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }

}
