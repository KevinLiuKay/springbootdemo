package com.kevin.service.sys;

import com.kevin.model.SysLog;
import com.kevin.model.ext.sys.SysLogExt;
import com.kevin.service.ICommonService;

import java.util.List;
import java.util.Map;

/**
 * @author lzk
 */
public interface ISysLogService extends ICommonService<SysLog> {
	/**
	 * 查询日志详情
	 * @param sysLog
	 * @param
	 * @return
	 */
	 List<SysLogExt> querySysLogExtList(SysLog sysLog);
}
