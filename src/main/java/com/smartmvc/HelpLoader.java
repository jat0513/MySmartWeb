package com.smartmvc;

import com.smartmvc.helper.BeanHelper;
import com.smartmvc.helper.ClassHelper;
import com.smartmvc.helper.ControllerHelper;
import com.smartmvc.helper.IocHelper;
import com.smartmvc.util.ClassUtil;

public class HelpLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), true);
        }
    }


}
