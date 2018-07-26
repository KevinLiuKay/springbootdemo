package com.kevin.service.sys.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.extMapper.sys.SysLogExtMapper;
import com.kevin.dao.mapper.SysLogMapper;
import com.kevin.model.SysLog;
import com.kevin.model.SysLogExample;
import com.kevin.model.SysLogExample.Criteria;
import com.kevin.model.ext.sys.SysLogExt;
import com.kevin.service.sys.ISysLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional(rollbackFor=Exception.class)
public class SysLogServiceImpl implements ISysLogService {
	@Resource
	private SysLogMapper logMapper;
	@Resource
	private SysLogExtMapper logExtMapper;

	@Override
	public int save(SysLog sysLog) {
		if(StringUtils.isBlank(sysLog.getLogId())){//新增
			sysLog.setLogId(UUIDUtil.getUUID());
			GeneralMethod.setRecordInfo(sysLog, true);
			return logMapper.insertSelective(sysLog);
		} else {//修改
			GeneralMethod.setRecordInfo(sysLog, false);
			return logMapper.updateByPrimaryKeySelective(sysLog);
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
	public List<SysLog> queryList(SysLog sysLog, String orderByClause) {
		SysLogExample example = new SysLogExample();
		Criteria criteria = example.createCriteria().andRecordStateEqualTo(GlobalConstant.Y);
		if(sysLog.getLogTypeId() != null){
			criteria.andLogTypeIdEqualTo(sysLog.getLogTypeId());
		}
		/*排序字段*/
		if(StringUtils.isNotBlank(orderByClause)){
			example.setOrderByClause(orderByClause);
		}
		return logMapper.selectByExample(example);
	}

	@Override
	public SysLog getById(String id) {
		if(StringUtils.isBlank(id)){
			return null;
		}
		return logMapper.selectByPrimaryKey(id);
	}

	//**********************华丽的分割线 *******************************

	@Override
	public List<SysLogExt> querySysLogExtList(Map<String, Object> paramMap) {
		return logExtMapper.querySysLogExtList(paramMap);
	}
}
