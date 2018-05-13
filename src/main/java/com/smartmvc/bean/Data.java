package com.smartmvc.bean;

/**
 * 返回数据对象
 */
public class Data {

    /**
     * 模型数据
     */
    private Object model;

    public Data() {
    }

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }
}
