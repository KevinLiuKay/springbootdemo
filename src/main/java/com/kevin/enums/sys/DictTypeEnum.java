package com.kevin.enums.sys;

import java.util.List;
import java.util.Map;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralEnum;
import com.kevin.common.utils.EnumUtil;
import com.kevin.model.SysDict;
import org.apache.commons.lang3.StringUtils;

public enum DictTypeEnum implements GeneralEnum<String> {

	UserGender("userGender", "用户性别", "sys"),
	UserRole("userRole","用户角色", "sys");

	public static Map<String, String> sysDictIdMap ;
	public static Map<String, List<SysDict>> sysListDictMap ;

	public String getDictNameById(String id){
		return sysDictIdMap.get(getId()+"."+id);
	}

	public List<SysDict> getSysDictList(){
		return sysListDictMap.get(getId());
	}

	private final String id;
	private final String name;
	private final String wsid;
	private Integer level = 1;

	DictTypeEnum(String id,String name,String wsid) {
		this.id = id;
		this.name = name;
		this.wsid = wsid;
	}

	DictTypeEnum(String id,String name,String wsid,int level) {
		this.id = id;
		this.name = name;
		this.wsid = wsid;
		this.level = level;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getWsid(){
		return wsid;
	}

	public static String getNameById(String id) {
		if(StringUtils.isBlank(id)){
			return GlobalConstant.EMPTY;
		}
		return EnumUtil.getById(id, DictTypeEnum.class).getName();
	}

	public int getLevel() {
		return level;
	}
}
