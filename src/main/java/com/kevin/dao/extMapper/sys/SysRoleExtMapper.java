package com.kevin.dao.extMapper.sys;

import com.kevin.model.SysRole;
import com.kevin.model.SysUserRole;
import com.kevin.model.ext.sys.SysUserExt;
import java.util.List;
import java.util.Map;

/**
 * @author lzk
 */
public interface SysRoleExtMapper {

    /**
     * 通过roleId，userIdList查找全部的关联（复用）
     * @param paramMap
     * @return
     */
    List<SysUserRole> queryRoleUserList(Map<String, Object> paramMap);

    /**
     * 通过userId,roleIdList查询全部的关联（复用）
     * @param paramMap
     * @return
     */
    List<SysUserRole> queryUserRoleList(Map<String, Object> paramMap);

    /**
     * 删除用户角色关联
     * @param sysUserRole
     * @return
     */
    int deleteRoleUser(SysUserRole sysUserRole);

    /**
     * 通过roleId查找user信息
     * @param paramMap
     * @return
     */
    List<SysUserExt> queryUserExtListByRoleId(Map<String, Object> paramMap);

    /**
     * 删除角色菜单关联信息
     * @param paramMap
     * @return
     */
    int deleteRoleMenuList(Map<String, Object> paramMap);

    /**
     * 通过用户id查询当前用户下的角色信息
     * @param paramMap
     * @return
     */
    List<SysRole> queryRoleListByUserId(Map<String, Object> paramMap);
}
