package com.kevin.service.sys;

import com.kevin.model.SysLog;
import com.kevin.model.ext.sys.SysLogExt;
import com.kevin.service.ICommonService;

import java.util.List;
import java.util.Map;

public interface ISysLogService extends ICommonService<SysLog> {
	/**
	 * 通用扩展查询
	 * @param paramMap
	 * @param
	 * @return
	 */
	public List<SysLogExt> querySysLogExtList(Map<String, Object> paramMap);
}
