package com.smartmvc.helper;

import com.smartmvc.ConfigConstant;
import com.smartmvc.util.PropsUtil;

import java.util.Properties;

public class ConfigHelper {

    private static final Properties CONFIG_PROPERTIES = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUsername() {
        return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.JDBC_PASSWORD);
    }

    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.APP_BASE_PACKAGE);
    }

    public static String getAppJspPath() {
        return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
    }

    public static String getAppAssertPath() {
        return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.APP_ASSERT_PATH, "/asset/");
    }

}
