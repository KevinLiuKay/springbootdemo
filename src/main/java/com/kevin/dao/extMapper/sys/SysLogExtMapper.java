package com.kevin.dao.extMapper.sys;

import java.util.List;
import java.util.Map;
import com.kevin.model.ext.sys.SysLogExt;

public interface SysLogExtMapper {
	
	List<SysLogExt> querySysLogExtList(Map<String, Object> paramMap);
}
