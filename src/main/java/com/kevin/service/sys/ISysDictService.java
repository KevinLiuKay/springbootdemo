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
	public List<SysDict> checkUnique(SysDict sysDict);


	/**
	 * 保存排序
	 * @param dictId
	 * @return
	 */
	public int saveSort(String[] dictId);


	
	/**
	 * 保存排序
	 * @param dictTypeId  字典类型
	 * @param currRecordId  当前拖动的ID
	 * @param oldRecordId 拖动到该位置前 该位置原来的ID
	 * @return
	 */
	public int saveSort(String dictTypeId, String currRecordId, String oldRecordId);
	public int saveSort(String[] dictId, int firstSerialNum);


}
