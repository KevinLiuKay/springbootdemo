package com.kevin.model.ext.sys;

import com.kevin.model.SysMenu;

import java.util.*;

public class SysMenuExt extends SysMenu {
    /**
     * 子菜单
     */
    private List<SysMenuExt> children = new ArrayList<SysMenuExt>();

    public List<SysMenuExt> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenuExt> children) {
        this.children = children;
    }

    public void sortChildren() {
        Collections.sort(children, new Comparator<SysMenuExt>() {
            @Override
            // 按照节点排序值进行排序
            public int compare(SysMenuExt n1, SysMenuExt n2) {
                return (n1.getMenuSort() < n2.getMenuSort() ? -1 : ((n1.getMenuSort()).equals(n2.getMenuSort())  ? 0 : 1));
            }

        });
        // 对每个节点的下一层节点进行排序
        for (Iterator<SysMenuExt> it = children.iterator(); it.hasNext();) {
            it.next().sortChildren();
        }
    }
}
