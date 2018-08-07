package com.kevin.service.sys;

import com.kevin.common.utils.JsonResult;
import com.kevin.model.SysUser;
import com.kevin.model.SysUserRole;
import com.kevin.model.ext.sys.SysUserExt;
import com.kevin.service.ICommonService;

import java.util.List;
import java.util.Map;

/**
 * @author lzk
 */
public interface ISysUserRoleService extends ICommonService<SysUserRole> {
	/**
	 * 查询当前角色下用户的数量
	 * @param sysUserRole
	 * @return
	 */
	long count(SysUserRole sysUserRole);

	/**
	 * 为角色绑定用户
	 * @param userIdList
	 * @param roleId
	 * @return
	 */
	JsonResult saveRoleUserList(List<String> userIdList, String roleId)throws Exception;

	/**
	 * 为角色删除用户
	 * @param userId
	 * @param roleId
	 * @return
	 */
	int deleteRoleUser(String userId, String roleId) throws Exception;


	/**
	 * 通过roleId查询user信息
	 * @param roleId
	 * @param sysUser
	 * @return
	 */
	List<SysUserExt> queryUserExtListByRoleId(String roleId, SysUser sysUser);
}
