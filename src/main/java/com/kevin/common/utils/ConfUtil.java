package com.kevin.common.utils;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfUtil {

    private static Logger logger = LoggerFactory.getLogger(ConfUtil.class);

    public static String getProperty(String key) {
        Properties configProperties = (Properties) SpringContextUtil.getBean("configProperties", Properties.class);
        return configProperties.getProperty(key);
    }

}
