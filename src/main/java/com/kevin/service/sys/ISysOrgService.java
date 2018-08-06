package com.kevin.service.sys;

import com.kevin.model.SysOrg;
import com.kevin.model.ext.sys.SysMenuExt;
import com.kevin.model.ext.sys.SysOrgExt;
import com.kevin.service.ICommonService;

import java.util.List;

public interface ISysOrgService extends ICommonService<SysOrg> {
    /**
     * 查询组织树
     * @param sysOrg
     * @return
     */
    List<SysOrgExt> queryOrgTree(SysOrg sysOrg);
}
