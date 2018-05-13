package com.smartmvc.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return !StringUtil.isEmpty(str);
    }

    public static String[] splitString(String str, String separatorChar) {
        return StringUtils.split(str, separatorChar);
    }

}
