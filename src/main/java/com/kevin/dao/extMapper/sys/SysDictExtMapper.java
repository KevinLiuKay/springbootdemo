package com.kevin.dao.extMapper.sys;

import com.kevin.model.SysDict;

import java.util.Map;



public interface SysDictExtMapper {
	
	/**
	 * 批量 排序码+1
	 * @param paramMap
	 * @return
	 */
	int setSortKeyAddOne(Map<String, Object> paramMap);

	/**
	 * 批量 排序码-1
	 * @param paramMap
	 * @return
	 */
	int setSortKeySubOne(Map<String, Object> paramMap);

	/**
	 * 查询最大值
	 * @param sysDict
	 * @return
	 */
	int maxSortKey(SysDict sysDict);

	/**
	 * 保存学校
	 * @param paramMap
	 * @return
	 */
	int saveSort(Map<String, Object> paramMap);
	
	
}
