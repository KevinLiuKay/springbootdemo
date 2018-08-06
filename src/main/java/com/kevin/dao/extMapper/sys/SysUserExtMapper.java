package com.kevin.dao.extMapper.sys;

import com.kevin.model.SysUser;
import com.kevin.model.ext.sys.SysUserExt;

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

	/**
	 * 查询用户所有信息包括部门（角色信息通过queryUserRoleList接口调用，避免一对多分页问题）
	 * @param map
	 * @return
	 */
	List<SysUserExt> queryUserExtList(Map<String, Object> map);

	/**
	 * 查询当前用户下角色信息
	 * @param map
	 * @return
	 */
	List<SysUserExt> queryUserRoleList(Map<String, Object> map);
}
