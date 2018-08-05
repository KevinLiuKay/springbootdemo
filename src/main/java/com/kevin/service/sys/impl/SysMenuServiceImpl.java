package com.kevin.service.sys.impl;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.mapper.SysMenuMapper;
import com.kevin.model.SysMenu;
import com.kevin.model.SysMenuExample;
import com.kevin.model.ext.sys.SysMenuExt;
import com.kevin.service.sys.ISysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lzk
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysMenuServiceImpl implements ISysMenuService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(SysMenuServiceImpl.class);

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public int save(SysMenu sysMenu) {
        if (StringUtils.isBlank(sysMenu.getMenuId())) {
            //新增
            sysMenu.setMenuId(UUIDUtil.getUUID());
            GeneralMethod.setRecordInfo(sysMenu, true);
            return sysMenuMapper.insertSelective(sysMenu);
        } else {
            //修改
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

    @Override
    public List<SysMenuExt> queryMenuTree(SysMenu sysMenu) {
        List<SysMenuExt> menuTree = null;
        List<SysMenu> menuList = queryList(sysMenu, null);
        if (null != menuList && !menuList.isEmpty()) {
            // 创建根节点
            SysMenuExt root = new SysMenuExt();
            // 组装Map数据
            Map<String, SysMenuExt> dataMap = new HashMap<String, SysMenuExt>();
            for (SysMenu menu : menuList) {
                SysMenuExt sysMenuExt = new SysMenuExt();
                BeanUtils.copyProperties(menu,sysMenuExt);
                dataMap.put(sysMenuExt.getMenuId(), sysMenuExt);
            }
            // 组装树形结构
            Set<Map.Entry<String, SysMenuExt>> entrySet = dataMap.entrySet();
            for (Map.Entry<String, SysMenuExt> entry : entrySet) {
                SysMenuExt menu = entry.getValue();
                if (null == menu.getMenuParentId() || ("0").equals(menu.getMenuParentId())) {
                    root.getChildren().add(menu);
                } else {
                    dataMap.get(menu.getMenuParentId()).getChildren().add(menu);
                }
            }
            // 对树形结构进行二叉树排序
            root.sortChildren();
            menuTree = root.getChildren();
        }
        return menuTree;
    }
}
