package com.kevin.service.sys.impl;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;

import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.mapper.SysRoleMapper;
import com.kevin.model.SysRole;
import com.kevin.model.SysRoleExample;
import com.kevin.service.sys.ISysRoleService;
import com.kevin.service.sys.ISysUserRoleService;
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
public class SysRoleServiceImpl implements ISysRoleService {
    private static Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private ISysUserRoleService sysUserRoleService;

    @Override
    public int save(SysRole sysRole) {
        if (StringUtils.isBlank(sysRole.getRoleId())) {
            //新增
            sysRole.setRoleId(UUIDUtil.getUUID());
            GeneralMethod.setRecordInfo(sysRole, true);
            return sysRoleMapper.insertSelective(sysRole);
        } else {
            //修改
            GeneralMethod.setRecordInfo(sysRole, false);
            return sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        }
    }

    @Override
    public int logicallyDeleteById(String id) {
        if (StringUtils.isNotBlank(id)) {
            //update
            SysRole role = new SysRole();
            //set
            role.setRecordState(GlobalConstant.N);
            //where
            role.setRoleId(id);
            return save(role);
        }
        return GlobalConstant.ZERO;
    }

    @Override
    public int deleteById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return sysRoleMapper.deleteByPrimaryKey(id);
        }
        return GlobalConstant.ZERO;
    }

    @Override
    public List<SysRole> queryList(SysRole sysRole, String orderByClause) {
        SysRoleExample example = new SysRoleExample();
        SysRoleExample.Criteria criteria = example.createCriteria();
        andCrieria(sysRole, criteria);
        /* 排序字段 */
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        return sysRoleMapper.selectByExample(example);
    }

    @Override
    public SysRole getById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return sysRoleMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    private void andCrieria(SysRole sysRole, SysRoleExample.Criteria criteria) {
        if (StringUtils.isNotBlank(sysRole.getRoleName())){
            criteria.andRoleNameLike(GlobalConstant.PERCENT + sysRole.getRoleName() + GlobalConstant.PERCENT);
        }
    }


}
