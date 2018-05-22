package com.smartmvc;

import com.smartmvc.helper.*;
import com.smartmvc.util.ClassUtil;

public class HelpLoader {

    public static void init() {
        Class<?>[] classList = {
                // 1:加载应用包名下所有的 Class 文件
                ClassHelper.class,
                // 2:创建所有 Class 文件对应的实例
                BeanHelper.class,
                // 3:创建代理对象
                AopHelper.class,
                // 3:依赖注入
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), true);
        }
    }


}
