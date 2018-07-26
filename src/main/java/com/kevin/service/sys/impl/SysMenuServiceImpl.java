package com.kevin.service.sys.impl;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
import com.kevin.common.shiro.PasswordHelper;
import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.mapper.SysMenuMapper;
import com.kevin.model.SysMenu;
import com.kevin.model.SysMenuExample;
import com.kevin.model.SysUser;
import com.kevin.model.SysUserExample;
import com.kevin.service.sys.ISysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysMenuServiceImpl implements ISysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(SysMenuServiceImpl.class);
    @Override
    public int save(SysMenu sysMenu) {
        if (StringUtils.isBlank(sysMenu.getMenuId())) {//新增
            sysMenu.setMenuId(UUIDUtil.getUUID());
            GeneralMethod.setRecordInfo(sysMenu, true);
            return sysMenuMapper.insertSelective(sysMenu);
        } else {//修改
            GeneralMethod.setRecordInfo(sysMenu, false);
            return sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
        }
    }

    @Override
    public int logicallyDeleteById(String id) {
        if (StringUtils.isNotBlank(id)) {
            //update
           SysMenu sysMenu = new SysMenu();
            //set
            sysMenu.setRecordState(GlobalConstant.N);
            //where
            sysMenu.setMenuId(id);
            return save(sysMenu);
        }
        return GlobalConstant.ZERO;
    }

    @Override
    public int deleteById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return sysMenuMapper.deleteByPrimaryKey(id);
        }
        return GlobalConstant.ZERO;
    }

    @Override
    public List<SysMenu> queryList(SysMenu sysMenu, String orderByClause) {
        SysMenuExample example = new SysMenuExample();
        SysMenuExample.Criteria criteria = example.createCriteria().andRecordStateEqualTo(GlobalConstant.Y);
        andCrieria(sysMenu, criteria);
        /* 排序字段 */
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        return sysMenuMapper.selectByExample(example);
    }

    @Override
    public SysMenu getById(String id) {
        return null;
    }

    private void andCrieria(SysMenu sysMenu, SysMenuExample.Criteria criteria) {
        if (StringUtils.isNotBlank(sysMenu.getMenuName())) {
            criteria.andMenuNameLike(GlobalConstant.PERCENT + sysMenu.getMenuName() + GlobalConstant.PERCENT);
        }
    }
}
