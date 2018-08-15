package com.kevin.enums.sys;


import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralEnum;
import com.kevin.common.utils.EnumUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lzk
 */

public enum LogTypeEnum implements GeneralEnum<String> {
	
	Login("login","登录"),
	Logout("logout","退出"),
	;

	private final String id;
	private final String name;
	
	LogTypeEnum(String id,String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	public static String getNameById(String id) {
		if(StringUtils.isBlank(id)){
			return GlobalConstant.EMPTY;
		}
		return EnumUtil.getById(id, LogTypeEnum.class).getName();
	}
}
