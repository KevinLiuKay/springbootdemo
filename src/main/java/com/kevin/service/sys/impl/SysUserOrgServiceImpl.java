package com.kevin.service.sys.impl;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.mapper.SysUserOrgMapper;
import com.kevin.model.SysUserOrg;
import com.kevin.model.SysUserOrgExample;
import com.kevin.model.SysUserOrgExample.Criteria;
import com.kevin.service.sys.ISysUserOrgService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;


/**
 * @author lzk
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserOrgServiceImpl implements ISysUserOrgService {
    private static Logger logger = LoggerFactory.getLogger(SysUserOrgServiceImpl.class);
    @Resource
    private SysUserOrgMapper sysUserOrgMapper;

    @Override
    public int save(SysUserOrg sysUserOrg) {
        if (StringUtils.isBlank(sysUserOrg.getUserOrgId())) {
            //新增
            sysUserOrg.setUserOrgId(UUIDUtil.getUUID());
            GeneralMethod.setRecordInfo(sysUserOrg, true);
            return sysUserOrgMapper.insertSelective(sysUserOrg);
        } else {
            //修改
            GeneralMethod.setRecordInfo(sysUserOrg, false);
            return sysUserOrgMapper.updateByPrimaryKeySelective(sysUserOrg);
        }
    }

    @Override
    public int logicallyDeleteById(String id) {
        if (StringUtils.isNotBlank(id)) {
            //update
            SysUserOrg sysUserOrg = new SysUserOrg();
            //set
            sysUserOrg.setRecordState(GlobalConstant.N);
            //where
            sysUserOrg.setUserOrgId(id);
            return save(sysUserOrg);
        }
        return GlobalConstant.ZERO;
    }

    @Override
    public int deleteById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return sysUserOrgMapper.deleteByPrimaryKey(id);
        }
        return GlobalConstant.ZERO;
    }

    @Override
    public List<SysUserOrg> queryList(SysUserOrg sysUserOrg, String orderByClause) {
        SysUserOrgExample example = new SysUserOrgExample();
        Criteria criteria = example.createCriteria();

        andCrieria(sysUserOrg, criteria);
        /* 排序字段 */
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        return sysUserOrgMapper.selectByExample(example);
    }

    @Override
    public SysUserOrg getById(String id) {
        return null;
    }

    private void andCrieria(SysUserOrg sysUserOrg, SysUserOrgExample.Criteria criteria) {
        if (StringUtils.isNotBlank(sysUserOrg.getRecordState())) {
            criteria.andRecordStateEqualTo(GlobalConstant.Y);
        }
        if (StringUtils.isNotBlank(sysUserOrg.getUserId())) {
            criteria.andUserIdEqualTo(sysUserOrg.getUserId());
        }
        if (StringUtils.isNotBlank(sysUserOrg.getOrgId())) {
            criteria.andOrgIdEqualTo(sysUserOrg.getOrgId());
        }
    }

}
