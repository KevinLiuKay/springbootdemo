package com.kevin.dao.extMapper.sys;

import com.kevin.model.SysUser;

import java.util.List;
import java.util.Map;

public interface SysUserExtMapper {

	/**
	 * 批量新增用户
	 * @param list
	 * @return
	 */
	int batchInsert(List<SysUser> list);
	/**
	 * 批量逻辑删除用户
	 * @param map
	 * @return
	 */
	int batchLogicDelete(Map<String, Object> map);

}
