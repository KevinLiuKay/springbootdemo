package com.kevin.dao.extMapper.sys;

import com.kevin.model.SysUserRoleRel;
import com.kevin.model.ext.sys.SysUserExt;

import java.util.List;
import java.util.Map;

public interface SysRoleExtMapper {
    /**
     * 查询用户信息（包括角色，部门）
     * @param paramMap
     * @return
     */
    List<SysUserExt> queryUserExtList(Map<String, Object> paramMap);
}
