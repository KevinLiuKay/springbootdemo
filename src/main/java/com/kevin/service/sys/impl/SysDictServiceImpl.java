package com.kevin.service.sys.impl;

import java.util.*;
import javax.annotation.Resource;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
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
		//新增
		if(StringUtils.isBlank(sysDict.getDictId())){
			sysDict.setDictId(UUIDUtil.getUUID());
			GeneralMethod.setRecordInfo(sysDict, true);
			//最大值
			int maxVal = dictExtMapper.maxSortKey(sysDict);
			sysDict.setSortKey(maxVal+1);
			return dictMapper.insertSelective(sysDict);
		} else {
			//修改
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
		SysDictExample.Criteria criteria = example.createCriteria();
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

}
