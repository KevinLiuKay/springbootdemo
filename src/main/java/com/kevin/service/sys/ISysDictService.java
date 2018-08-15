package com.kevin.service.sys;

import com.kevin.model.SysDict;
import com.kevin.service.ICommonService;
import java.util.List;


public interface ISysDictService extends ICommonService<SysDict> {

	/**
	 * 检查字典唯一
	 * @param sysDict
	 * @return
	 */
	List<SysDict> checkUnique(SysDict sysDict);

}
