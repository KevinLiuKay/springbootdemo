package com.kevin.service.sys;

import com.kevin.model.SysCfg;
import com.kevin.service.ICommonService;

import java.util.List;

/**
 * @author lzk
 */
public interface ISysCfgService extends ICommonService<SysCfg> {
    /**
     *
     * @param sysCfgList
     * @return
     */
    int saveSysCfg(List<SysCfg> sysCfgList);
}
