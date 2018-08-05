package com.kevin.model.ext.sys;


import com.kevin.model.SysOrg;
import com.kevin.model.SysRole;
import com.kevin.model.SysUser;

import java.util.List;

/**
 * @author lzk
 */
public class SysUserExt extends SysUser {
    private List<SysRole> roleList;
    private SysOrg sysOrg;

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }

    public SysOrg getSysOrg() {
        return sysOrg;
    }

    public void setSysOrg(SysOrg sysOrg) {
        this.sysOrg = sysOrg;
    }
}
