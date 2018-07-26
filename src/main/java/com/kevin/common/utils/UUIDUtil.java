package com.kevin.common.utils;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用唯一识别码(Universally Unique Identifier) 工具类
 * @author Tiger Mo
 *
 */
public class UUIDUtil {
	
	private static Logger logger = LoggerFactory.getLogger(UUIDUtil.class);

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static void main(String [] args){
		for(int i=0;i<100;i++){
			logger.debug("-----> getUUID() : "+getUUID());			
		}
	}
	
}
