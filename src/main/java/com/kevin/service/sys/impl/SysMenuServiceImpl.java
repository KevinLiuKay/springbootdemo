package com.kevin.service.sys.impl;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
import com.kevin.common.core.HttpServletContext;
import com.kevin.common.utils.JsonResult;
import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.extMapper.sys.SysMenuExtMapper;
import com.kevin.dao.extMapper.sys.SysRoleExtMapper;
import com.kevin.dao.mapper.SysMenuMapper;
import com.kevin.model.SysMenu;
import com.kevin.model.SysMenuExample;
import com.kevin.model.SysRoleMenu;
import com.kevin.model.SysUser;
import com.kevin.model.ext.sys.SysMenuExt;
import com.kevin.service.sys.ISysMenuService;
import com.kevin.service.sys.ISysRoleMenuService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author lzk
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysMenuServiceImpl implements ISysMenuService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(SysMenuServiceImpl.class);

    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysMenuExtMapper sysMenuExtMapper;
    @Resource
    private SysRoleExtMapper sysRoleExtMapper;
    @Resource
    private ISysRoleMenuService sysRoleMenuService;

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

    @Override
    public List<SysMenuExt> queryMenuTreeByRoleId(String roleId) {
        List<SysMenuExt> menuTree = null;
        if(StringUtils.isNotBlank(roleId)) {
            List<String> list = new ArrayList<>();
            list.add(roleId);
            List<SysMenuExt> menuList = sysMenuExtMapper.queryMenuByRoleId(list);
            if (null != menuList && !menuList.isEmpty()) {
                // 创建根节点
                SysMenuExt root = new SysMenuExt();
                // 组装Map数据
                Map<String, SysMenuExt> dataMap = new HashMap<String, SysMenuExt>();
                for (SysMenuExt sysMenuExt : menuList) {
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
        return null;
    }

    @Override
    public JsonResult saveRoleMenu(String roleId, String[] menuIds) {
        JsonResult jsonResult = new JsonResult();
        //通过roleId查询SysRoleMenu角色菜单表中存在的所有记录（recordState为Y和N）
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setRoleId(roleId);
        List<SysRoleMenu> roleMenuList = sysRoleMenuService.queryList(sysRoleMenu,"");
        //存放需要删除的
        Map<String, SysRoleMenu> deleteMap = new HashMap<String, SysRoleMenu>();
        //存放recordState状态为Y和N的数据
        List<SysRoleMenu> yList = new ArrayList<SysRoleMenu>();
        List<SysRoleMenu> nList = new ArrayList<SysRoleMenu>();
        if(roleMenuList !=null && !roleMenuList.isEmpty()) {
            for(SysRoleMenu roleMenu : roleMenuList) {
                if(GlobalConstant.Y.equals(roleMenu.getRecordState())) {
                    yList.add(roleMenu);
                    //默认全部删除
                    deleteMap.put(roleMenu.getRoleMenuId(),roleMenu);
                }else {
                    nList.add(roleMenu);
                }
            }
        }
        //对menuId []进行过滤
        if(menuIds != null && menuIds.length > 0) {
            for(String menuId : menuIds){
                boolean insert = true;
                //如果数据库中存在部分需要保存的角色菜单信息，继续使用，不删除
                if(yList != null && !yList.isEmpty()) {
                    for(SysRoleMenu yrm : yList) {
                        if(yrm.getMenuId().equals(menuId)){
                            insert = false;
                            if(!deleteMap.isEmpty()) {
                                deleteMap.remove(yrm.getRoleMenuId());
                            }
                            break;
                        }
                    }
                }
                //除去recordState为Y需要的角色菜单信息，其他的新增保存
                if(insert) {
                    SysRoleMenu roleMenu = new SysRoleMenu();
                    roleMenu.setRoleId(roleId);
                    roleMenu.setMenuId(menuId);
                    //如果已经删除的角色菜单存在需要绑定的角色菜单信息，修改状态
                    if(nList != null && !nList.isEmpty()){
                        for(SysRoleMenu nrm : nList) {
                            if(nrm.getMenuId().equals(menuId)) {
                                insert = false;
                                roleMenu.setRecordState(GlobalConstant.Y);
                                //从删除的角色菜单中找到需要绑定信息，修改recordState状态N改为Y
                                throwException(sysRoleMenuService.save(roleMenu),"保存角色菜单关联表失败!");
                                break;
                            }
                        }
                        //从删除的角色菜单中没有找到需要绑定信息，新增角色菜单信息
                        if(insert) {
                            throwException(sysRoleMenuService.save(roleMenu),"保存角色菜单关联表失败!");
                        }
                    }else {
                        //recordState为N的中不存在需要绑定的角色菜单信息，新增角色菜单信息
                        throwException(sysRoleMenuService.save(roleMenu),"保存角色菜单关联表失败!");
                    }
                }
            }
        }
        SysUser currUser = HttpServletContext.getCurrentUser();
        Date currentTime = Calendar.getInstance().getTime();
        if(currUser == null) {
            throwException(GlobalConstant.ZERO,GlobalConstant.SESSION_OUT_TIME);
        }
        //逻辑删除不需要的角色菜单信息
        if(!deleteMap.isEmpty()) {
            List<String> delRmList = new ArrayList<String>();
            for (SysRoleMenu value : deleteMap.values()) {
                delRmList.add(value.getMenuId());
            }
            if(delRmList != null && !delRmList.isEmpty()){
                Map<String, Object> delMap = new HashMap<String, Object>();
                delMap.put("currUserId",currUser.getUserId());
                delMap.put("updateTime",currentTime);
                delMap.put("roleId", roleId);
                delMap.put("list", delRmList);
                throwException(sysRoleExtMapper.deleteRoleMenuList(delMap),"保存角色菜单关联表失败!");
            }
        }
        jsonResult.setStatus(true);
        jsonResult.setMessage(GlobalConstant.SAVE_SUCCESSED);
        return jsonResult;
    }

    public void throwException(int result,String name){
        if (result == 0) {
            throw new RuntimeException(name);
        }
    }
}
