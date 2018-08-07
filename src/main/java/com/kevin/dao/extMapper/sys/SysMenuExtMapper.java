package com.kevin.dao.extMapper.sys;

import com.kevin.model.ext.sys.SysMenuExt;
import java.util.List;
import java.util.Map;
/**
 * @author lzk
 */
public interface SysMenuExtMapper {
    /**
     * 通过roleId查询角色下的菜单
     * @param list
     * @return
     */
    List<SysMenuExt> queryMenuByRoleId(List<String> list);
}
