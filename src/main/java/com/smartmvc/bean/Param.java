package com.smartmvc.bean;

import com.smartmvc.util.CastUtil;

import java.util.Map;

/**
 * 请求参数对象
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public int getInt(String name) {
        return CastUtil.castInt(paramMap.get(name));
    }

    public Map<String, Object> getMap() {
        return paramMap;
    }
}
