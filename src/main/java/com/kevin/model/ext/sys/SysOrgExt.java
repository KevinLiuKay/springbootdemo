package com.kevin.model.ext.sys;

import com.kevin.model.SysOrg;

import java.util.*;

public class SysOrgExt extends SysOrg {
    /**
     * 子菜单
     */
    private List<SysOrgExt> children = new ArrayList<SysOrgExt>();

    public List<SysOrgExt> getChildren() {
        return children;
    }

    public void setChildren(List<SysOrgExt> children) {
        this.children = children;
    }

    public void sortChildren() {
        Collections.sort(children, new Comparator<SysOrgExt>() {
            @Override
            // 按照节点排序值进行排序
            public int compare(SysOrgExt n1, SysOrgExt n2) {
                return (n1.getSortKey() < n2.getSortKey() ? -1 : ((n1.getSortKey()).equals(n2.getSortKey())  ? 0 : 1));
            }

        });
        // 对每个节点的下一层节点进行排序
        for (Iterator<SysOrgExt> it = children.iterator(); it.hasNext();) {
            it.next().sortChildren();
        }
    }
}
