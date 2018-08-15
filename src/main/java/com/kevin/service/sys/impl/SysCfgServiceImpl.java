package com.kevin.service.sys.impl;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.mapper.SysCfgMapper;
import com.kevin.model.SysCfg;
import com.kevin.model.SysCfgExample;
import com.kevin.model.SysCfgExample.Criteria;
import com.kevin.service.sys.ISysCfgService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional(rollbackFor=Exception.class)
public class SysCfgServiceImpl implements ISysCfgService {
	
	private static Logger logger = LoggerFactory.getLogger(SysCfgServiceImpl.class);
	
	@Resource
	private SysCfgMapper cfgMapper;


	@Override
	public int save(SysCfg sysCfg) {
		//新增
		if(StringUtils.isBlank(sysCfg.getCfgCode())){
			sysCfg.setCfgCode(UUIDUtil.getUUID());
			GeneralMethod.setRecordInfo(sysCfg, true);
			return cfgMapper.insertSelective(sysCfg);
		} else {
		//修改
			GeneralMethod.setRecordInfo(sysCfg, false);
			return cfgMapper.updateByPrimaryKeySelective(sysCfg);
		}
	}

	@Override
	public int logicallyDeleteById(String id) {
		return 0;
	}

	@Override
	public int deleteById(String id) {
		return 0;
	}

	@Override
	public List<SysCfg> queryList(SysCfg sysCfg, String orderByClause) {
		SysCfgExample example = new SysCfgExample();
		Criteria criteria = example.createCriteria();
		criteria.andRecordStateEqualTo(GlobalConstant.Y);
		/*排序字段*/
		if(StringUtils.isNotBlank(orderByClause)){
			example.setOrderByClause(orderByClause);
		}
		return cfgMapper.selectByExample(example);
	}

	@Override
	public SysCfg getById(String id) {
		if(StringUtils.isNotBlank(id)) {
			return cfgMapper.selectByPrimaryKey(id);
		}
		return null;
	}

	@Override
	public int saveSysCfg(List<SysCfg> sysCfgList) {
		int result = GlobalConstant.ZERO;
		for (SysCfg cfg : sysCfgList) {
			result += save(cfg);
		}
		return result;
	}
}
