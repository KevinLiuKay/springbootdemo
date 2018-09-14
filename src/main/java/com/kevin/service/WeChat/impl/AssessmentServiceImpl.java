package com.kevin.service.WeChat.impl;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.mapper.AssessmentMapper;
import com.kevin.model.Assessment;
import com.kevin.model.AssessmentExample;
import com.kevin.service.WeChat.IAssessmentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lzk
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AssessmentServiceImpl implements IAssessmentService {
    private static Logger logger = LoggerFactory.getLogger(AssessmentServiceImpl.class);
    @Autowired
    private AssessmentMapper assessmentMapper;

    @Override
    public int save(Assessment record) {
        if (StringUtils.isBlank(record.getId())) {
            //新增
            record.setId(UUIDUtil.getUUID());
            GeneralMethod.setRecordInfo(record, true);
            return assessmentMapper.insertSelective(record);
        } else {
            //修改
            GeneralMethod.setRecordInfo(record, false);
            return assessmentMapper.updateByPrimaryKeySelective(record);
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
    public List<Assessment> queryList(Assessment assessment, String orderByClause) {
        AssessmentExample example = new AssessmentExample();
        AssessmentExample.Criteria criteria = example.createCriteria().andRecordStateEqualTo(GlobalConstant.Y);
        andCrieria(assessment, criteria);
        /* 排序字段 */
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        return assessmentMapper.selectByExample(example);
    }

    @Override
    public Assessment getById(String id) {
        return null;
    }

    private void andCrieria(Assessment assessment, AssessmentExample.Criteria criteria) {
        if (assessment.getType() != null) {
            criteria.andTypeEqualTo(assessment.getType());
        }
    }
}
