package com.kevin.service.sys;

import com.kevin.model.SysMenu;
import com.kevin.model.ext.sys.SysMenuExt;
import com.kevin.service.ICommonService;
import java.util.List;

public interface ISysMenuService extends ICommonService<SysMenu> {
    /**
     * 查询菜单树
     * @param sysMenu
     * @return
     */
    public List<SysMenuExt> queryMenuTree(SysMenu sysMenu);
}
