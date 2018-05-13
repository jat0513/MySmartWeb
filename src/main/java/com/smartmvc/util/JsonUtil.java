package com.smartmvc.util;

import com.alibaba.fastjson.JSON;

public class JsonUtil {


    public static <T> String toJson(T obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T fromJson(String json, Class<T> type) {
        return JSON.parseObject(json, type);
    }

}
