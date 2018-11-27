package com.kevin.service.sys;

import com.kevin.model.SysHtml;
import com.kevin.service.ICommonService;

public interface ISysHtmlService extends ICommonService<SysHtml> {
    /**
     * 富文本保存为html文件
     * @param sysHtml
     * @return
     */
    int saveSysHtml(SysHtml sysHtml);
}
