package com.kevin.service.sys;

import com.kevin.model.SysMenu;
import com.kevin.model.ext.sys.SysMenuExt;
import com.kevin.service.ICommonService;
import java.util.List;

public interface ISysMenuService extends ICommonService<SysMenu> {
    /**
     * 查询全部菜单树
     * @param sysMenu
     * @return
     */
     List<SysMenuExt> queryMenuTree(SysMenu sysMenu);

    /**
     * 通过roleId查询菜单
     * @param roleId
     * @return
     */
    List<SysMenuExt> queryMenuTreeByRoleId(String roleId);
}
