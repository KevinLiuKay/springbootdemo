package com.kevin.service.sys.impl;

import java.util.*;
import javax.annotation.Resource;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
import com.kevin.common.core.HttpServletContext;
import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.extMapper.sys.SysDictExtMapper;
import com.kevin.dao.mapper.SysDictMapper;
import com.kevin.model.SysDict;
import com.kevin.model.SysDictExample;
import com.kevin.model.SysDictExample.Criteria;
import com.kevin.service.sys.ISysDictService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional(rollbackFor=Exception.class)
public class SysDictServiceImpl implements ISysDictService {
	private static Logger logger = LoggerFactory.getLogger(SysDictServiceImpl.class);

	@Resource
	private SysDictMapper dictMapper;
	@Resource
	private SysDictExtMapper dictExtMapper;

	@Override
	public int save(SysDict sysDict) {
		if(StringUtils.isBlank(sysDict.getDictId())){//新增
			sysDict.setDictId(UUIDUtil.getUUID());
			GeneralMethod.setRecordInfo(sysDict, true);
			/*if(StringUtils.isNotBlank(sysDict.getDictTypeId())){
				sysDict.setDictTypeName(DictTypeEnum.getNameById(sysDict.getDictTypeId()));
			}*/

			//最大值
			int maxVal = dictExtMapper.maxSortKey(sysDict);
			sysDict.setSortKey(maxVal+1);
			return dictMapper.insertSelective(sysDict);
		} else {//修改
			GeneralMethod.setRecordInfo(sysDict, false);
			return dictMapper.updateByPrimaryKeySelective(sysDict);
		}
	}

	@Override
	public int logicallyDeleteById(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<SysDict> queryList(SysDict sysDict, String orderByClause) {
		SysDictExample example = new SysDictExample();
		Criteria criteria = example.createCriteria();
		andCriteria(sysDict, criteria);

		if(StringUtils.isNotBlank(sysDict.getDictValue())){
			criteria.andDictValueLike(GlobalConstant.PERCENT + sysDict.getDictValue() + GlobalConstant.PERCENT);
		}
		/*排序字段*/
		if(StringUtils.isNotBlank(orderByClause)){
			example.setOrderByClause(orderByClause);
		}
		return dictMapper.selectByExample(example);
	}

	/**
	 * 公用查询条件
	 * @param sysDict
	 * @param criteria
	 */
	private void andCriteria(SysDict sysDict, Criteria criteria) {
		if(StringUtils.isNotBlank(sysDict.getDictTypeId())){
			criteria.andDictTypeIdEqualTo(sysDict.getDictTypeId());
		}
		if(sysDict.getDictKey() != null){
			criteria.andDictKeyEqualTo(sysDict.getDictKey());
		}
		if(sysDict.getRecordState() != null){
			criteria.andRecordStateEqualTo(sysDict.getRecordState());
		}
	}

	@Override
	public SysDict getById(String dictId) {
		return dictMapper.selectByPrimaryKey(dictId);
	}

	//**********************华丽的分割线 *******************************

	@Override
	public List<SysDict> checkUnique(SysDict sysDict) {
		SysDictExample example = new SysDictExample();
		Criteria criteria = example.createCriteria();
		andCriteria(sysDict, criteria);
		if(StringUtils.isNotBlank(sysDict.getDictValue())){
			criteria.andDictValueEqualTo(sysDict.getDictValue());
		}
		//非自己的--NotEqualTo
		if(StringUtils.isNotBlank(sysDict.getDictId())){
			criteria.andDictIdNotEqualTo(sysDict.getDictId());
		}
		return dictMapper.selectByExample(example);
	}

	@Override
	public int saveSort(String[] dictId) {
		int result = GlobalConstant.ZERO;
		if(dictId == null) {
			return result;
		}
		int i = GlobalConstant.ONE;
		for(String id : dictId){
			SysDict sysDict = new SysDict();
			sysDict.setDictId(id);
			sysDict.setSortKey((i++));
			result += save(sysDict);
		}
		return result;
	}

	@Override
	public int saveSort(String dictTypeId, String currRecordId, String oldRecordId) {
		int result = GlobalConstant.ZERO;
		SysDict oldDict = getById(oldRecordId);
		SysDict currSysDict = getById(currRecordId);
		if(oldDict != null && currSysDict != null){
			int oldSortKey = oldDict.getSortKey();
			int currSortKey = currSysDict.getSortKey();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String currUserId = HttpServletContext.getCurrentUser().getUserId();
			//long currTime = System.currentTimeMillis();
			paramMap.put("oldSortKey", oldSortKey);
			paramMap.put("currSortKey", currSortKey);
			paramMap.put("currUserId", currUserId);
			paramMap.put("dictTypeId", dictTypeId);
			paramMap.put("updateTime", Calendar.getInstance().getTime());
			//==判断大小
			if(currSortKey > oldSortKey){//5-->1 : 1到4  批量+1
				result += dictExtMapper.setSortKeyAddOne(paramMap);
			}else if(currSortKey < oldSortKey){//1-->5:  2到5  批量-1
				result += dictExtMapper.setSortKeySubOne(paramMap);
			}
			SysDict updateCurr = new SysDict();
			updateCurr.setDictId(currRecordId);
			updateCurr.setSortKey(oldSortKey);
			result += save(updateCurr);
		}
		return result;
	}

	@Override
	public int saveSort(String[] dictId, int firstSerialNum) {
		String currUserId = HttpServletContext.getCurrentUser().getUserId();
		//long currTime = System.currentTimeMillis();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("currUserId", currUserId);
		paramMap.put("dictId", dictId);
		paramMap.put("firstSerialNum", firstSerialNum);
		return dictExtMapper.saveSort(paramMap);
	}



}
