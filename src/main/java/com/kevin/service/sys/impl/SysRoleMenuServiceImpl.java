package com.kevin.service.sys.impl;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.mapper.SysRoleMenuMapper;
import com.kevin.model.SysRoleMenu;
import com.kevin.model.SysRoleMenuExample;
import com.kevin.service.sys.ISysRoleMenuService;
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
public class SysRoleMenuServiceImpl implements ISysRoleMenuService {
    private static Logger logger = LoggerFactory.getLogger(SysRoleMenuServiceImpl.class);

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public int save(SysRoleMenu sysRoleMenu) {
        if (StringUtils.isBlank(sysRoleMenu.getRoleMenuId())) {
            //新增
            sysRoleMenu.setRoleMenuId(UUIDUtil.getUUID());
            GeneralMethod.setRecordInfo(sysRoleMenu, true);
            return sysRoleMenuMapper.insertSelective(sysRoleMenu);
        } else {
            //修改
            GeneralMethod.setRecordInfo(sysRoleMenu, false);
            return sysRoleMenuMapper.updateByPrimaryKeySelective(sysRoleMenu);
        }
    }

    @Override
    public int logicallyDeleteById(String id) {
        if (StringUtils.isNotBlank(id)) {
            //update
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            //set
            sysRoleMenu.setRecordState(GlobalConstant.N);
            //where
            sysRoleMenu.setRoleMenuId(id);
            return save(sysRoleMenu);
        }
        return GlobalConstant.ZERO;
    }

    @Override
    public int deleteById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return sysRoleMenuMapper.deleteByPrimaryKey(id);
        }
        return GlobalConstant.ZERO;
    }

    @Override
    public List<SysRoleMenu> queryList(SysRoleMenu sysRoleMenu, String orderByClause) {
        SysRoleMenuExample example = new SysRoleMenuExample();
        SysRoleMenuExample.Criteria criteria = example.createCriteria();
        andCrieria(sysRoleMenu, criteria);
        /* 排序字段 */
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        return sysRoleMenuMapper.selectByExample(example);
    }

    @Override
    public SysRoleMenu getById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return sysRoleMenuMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    private void andCrieria(SysRoleMenu sysRoleMenu, SysRoleMenuExample.Criteria criteria) {
        if (StringUtils.isNotBlank(sysRoleMenu.getRecordState())){
            criteria.andRecordStateEqualTo(sysRoleMenu.getRecordState());
        }
        if (StringUtils.isNotBlank(sysRoleMenu.getRoleId())){
            criteria.andRoleIdEqualTo(sysRoleMenu.getRoleId());
        }
        if (StringUtils.isNotBlank(sysRoleMenu.getMenuId())){
            criteria.andMenuIdEqualTo(sysRoleMenu.getMenuId());
        }
    }
}
