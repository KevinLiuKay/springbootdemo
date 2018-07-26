package com.kevin.common.core;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

import com.kevin.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kevin.common.GlobalConstant.GlobalConstant;

/**
 * 新增或修改记录工具类
 */
public class GeneralMethod {
	
	private static Logger logger = LoggerFactory.getLogger(GeneralMethod.class);
	
	public static void setRecordInfo(Object obj,  boolean isInsert) {
		SysUser currUser = HttpServletContext.getCurrentUser();
		Class<?> clazz = obj.getClass();
		try {
			if(isInsert){//insert
				Method setRecordState = clazz.getMethod("setRecordState", Integer.class);
				setRecordState.invoke(obj, GlobalConstant.Y);
				Method setCreateTime = clazz.getMethod("setCreateTime", Date.class);
				setCreateTime.invoke(obj,  Calendar.getInstance().getTime());
				if(currUser != null){
					Method setCreateUserId = clazz.getMethod("setCreateUserId", String.class);
					setCreateUserId.invoke(obj, currUser.getUserId());
				}
			}
			//update
			Method setModifyTime = clazz.getMethod("setUpdateTime", Date.class);
			setModifyTime.invoke(obj, Calendar.getInstance().getTime());
			if(currUser != null){
				Method setModifyUserId = clazz.getMethod("setUpdateUserId", String.class);
				setModifyUserId.invoke(obj,currUser.getUserId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	
}
