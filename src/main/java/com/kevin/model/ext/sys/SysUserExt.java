package com.kevin.model.ext.sys;

import com.kevin.model.SysMenu;
import com.kevin.model.SysRole;
import com.kevin.model.SysUser;
import com.kevin.model.SysUserRoleRel;

public class SysUserExt extends SysUser {
    private SysUserRoleRel sysUserRole;//为角色配置用户
    private SysRole sysRole;

    public SysUserRoleRel getSysUserRole() {
        return sysUserRole;
    }

    public void setSysUserRole(SysUserRoleRel sysUserRole) {
        this.sysUserRole = sysUserRole;
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }
}
