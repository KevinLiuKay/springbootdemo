package com.kevin.dao.extMapper.sys;

import java.util.List;
import java.util.Map;
import com.kevin.model.ext.sys.SysLogExt;

/**
 * @author lzk
 */
public interface SysLogExtMapper {

	/**
	 * 查询日志详情
	 * @param paramMap
	 * @return
	 */
	List<SysLogExt> querySysLogExtList(Map<String, Object> paramMap);
}
