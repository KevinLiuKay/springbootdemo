package com.kevin.dao.extMapper.sys;

import com.kevin.model.SysRole;
import com.kevin.model.SysUser;
import com.kevin.model.SysUserRole;
import com.kevin.model.ext.sys.SysRoleExt;
import com.kevin.model.ext.sys.SysUserExt;

import java.util.List;
import java.util.Map;

public interface SysRoleExtMapper {

    /**
     * 通过roleId，userIdList查找全部的关联（复用）
     * @param paramMap
     * @return
     */
    List<SysUserRole> queryRoleUserList(Map<String, Object> paramMap);

    /**
     * 修改SysUserRole关联表中，某个角色下绑定的用户,删除状态改为使用（复用）
     * @param paramMap
     * @return
     */
    int updateRecordStateN2Y(Map<String, Object> paramMap);

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
}
