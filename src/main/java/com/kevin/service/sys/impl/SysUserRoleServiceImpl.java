package com.kevin.service.sys.impl;


import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
import com.kevin.common.core.HttpServletContext;
import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.extMapper.sys.SysRoleExtMapper;
import com.kevin.dao.mapper.SysUserRoleMapper;
import com.kevin.exception.CommonException;
import com.kevin.model.SysUser;
import com.kevin.model.SysUserRole;
import com.kevin.model.SysUserRoleExample;
import com.kevin.model.SysUserRoleExample.Criteria;
import com.kevin.model.ext.sys.SysUserExt;
import com.kevin.service.sys.ISysUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author lzk
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserRoleServiceImpl implements ISysUserRoleService {
    private static Logger logger = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private SysRoleExtMapper sysRoleExtMapper;

    @Override
    public int save(SysUserRole sysUserRole) {
        if (StringUtils.isBlank(sysUserRole.getUserRoleId())) {
            //新增
            sysUserRole.setUserRoleId(UUIDUtil.getUUID());
            GeneralMethod.setRecordInfo(sysUserRole, true);
            return sysUserRoleMapper.insertSelective(sysUserRole);
        } else {
            //修改
            GeneralMethod.setRecordInfo(sysUserRole, false);
            return sysUserRoleMapper.updateByPrimaryKeySelective(sysUserRole);
        }
    }

    @Override
    public int logicallyDeleteById(String id) {
        if (StringUtils.isNotBlank(id)) {
            //update
            SysUserRole sysUserRole = new SysUserRole();
            //set
            sysUserRole.setRecordState(GlobalConstant.N);
            //where
            sysUserRole.setRoleId(id);
            return save(sysUserRole);
        }
        return GlobalConstant.ZERO;
    }

    @Override
    public int deleteById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return sysUserRoleMapper.deleteByPrimaryKey(id);
        }
        return GlobalConstant.ZERO;
    }

    @Override
    public List<SysUserRole> queryList(SysUserRole sysUserRole, String orderByClause) {
        SysUserRoleExample example = new SysUserRoleExample();
        SysUserRoleExample.Criteria criteria = example.createCriteria();
        andCrieria(sysUserRole, criteria);
        /* 排序字段 */
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        return sysUserRoleMapper.selectByExample(example);
    }

    @Override
    public SysUserRole getById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return sysUserRoleMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    private void andCrieria(SysUserRole sysUserRole, SysUserRoleExample.Criteria criteria) {
        if (StringUtils.isNotBlank(sysUserRole.getRoleId())){
            criteria.andRoleIdEqualTo(sysUserRole.getRoleId());
        }
    }

    @Override
    public long count(SysUserRole sysUserRole) {
        SysUserRoleExample example = new SysUserRoleExample();
        Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(sysUserRole.getRecordState())) {
            criteria.andRecordStateEqualTo(sysUserRole.getRecordState());
        }
        if (StringUtils.isNotBlank(sysUserRole.getRoleId())) {
            criteria.andRoleIdEqualTo(sysUserRole.getRoleId());
        }
        return sysUserRoleMapper.countByExample(example);
    }

    @Override
    public int saveRoleUserList(List<String> userIdList, String roleId) throws Exception {
        if(StringUtils.isNotBlank(roleId) && userIdList != null && !userIdList.isEmpty()) {
            SysUser currUser = HttpServletContext.getCurrentUser();
            if(currUser == null) {
                throw new CommonException(GlobalConstant.SESSION_OUT_TIME);
            }
            int result = GlobalConstant.ZERO;
            SysUserRole sysUserRole = null;
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("userIdList", userIdList);
            paramMap.put("roleId", roleId);
            //查询userRole关联表中，当前roleId绑定的所有用户
            List<SysUserRole> roleUserList = sysRoleExtMapper.queryRoleUserList(paramMap);
            if(roleUserList != null && !roleUserList.isEmpty()){
                //将userRole关联表中，当前roleId下的所有用户逻辑删除
                SysUserRole userRole = new SysUserRole();
                userRole.setRoleId(roleId);
                userRole.setUpdateUserId(currUser.getUserId());
                userRole.setUpdateTime(Calendar.getInstance().getTime());
                sysRoleExtMapper.deleteRoleUser(userRole);
            }
            List<String> userRoleIdList = null;
            if (userIdList != null && !userIdList.isEmpty()) {
                userRoleIdList = new ArrayList<String>();
                for (String userId : userIdList) {
                    // 是否新增 false：不新增 true:新增
                    boolean insert = true;
                    // 数据库不为空
                    if (roleUserList != null && !roleUserList.isEmpty()) {
                        for (SysUserRole existUR : roleUserList) {
                            if (existUR.getUserId().equals(userId)) {
                                sysUserRole = existUR;
                                insert = false;
                                break;
                            }
                        }
                        // 新增
                        if (insert) {
                            sysUserRole = new SysUserRole();
                            sysUserRole.setRoleId(roleId);
                            sysUserRole.setUserId(userId);
                            result += save(sysUserRole);
                        } else {
                            // 需要修改N为Y的userRoleId集合
                            String userRoleId = sysUserRole.getUserRoleId();
                            userRoleIdList.add(userRoleId);
                        }
                    } else {// 数据库空
                        sysUserRole = new SysUserRole();
                        sysUserRole.setRoleId(roleId);
                        sysUserRole.setUserId(userId);
                        result += save(sysUserRole);
                    }
                }
                //将userRole关联表中，当前roleId下需要绑定的用户的如果已存在数据库的N状态改为Y（复用）
                if (userRoleIdList != null && !userRoleIdList.isEmpty()) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("currUserId", currUser.getUserId());
                    map.put("userRoleIdList", userRoleIdList);
                    result += sysRoleExtMapper.updateRecordStateN2Y(map);
                }
            }
            return result;
        } else if(StringUtils.isNotBlank(roleId) && (userIdList == null || userIdList.isEmpty())){
            /**
             * roleId 绑定角色为空分两种情况
             * 1.roleId之前有绑定用户信息，默认删除roleId下面所有用户信息
             * 2.roleId之前未绑定任何用户信息，直接返回保存失败
             */
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            long roleIdExistNum = count(sysUserRole);
            if(roleIdExistNum != GlobalConstant.ZERO) {
                return deleteRoleUser("",roleId);
            } else {
            return GlobalConstant.ZERO;
            }
        } else {
            return GlobalConstant.ZERO;
        }
    }

    @Override
    public int deleteRoleUser(String userId, String roleId) throws Exception{
        if (StringUtils.isBlank(roleId)) {
            return GlobalConstant.ZERO;
        }
        SysUser currUser = HttpServletContext.getCurrentUser();
        if(currUser == null) {
            throw new CommonException(GlobalConstant.SESSION_OUT_TIME);
        }
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(roleId);
        long roleIdExistNum = count(sysUserRole);
        if(roleIdExistNum != GlobalConstant.ZERO) {
            sysUserRole.setUserId(userId);
            sysUserRole.setUpdateUserId(currUser.getUserId());
            sysUserRole.setUpdateTime(Calendar.getInstance().getTime());
            return sysRoleExtMapper.deleteRoleUser(sysUserRole);
        } else {
            throw new CommonException("删除数据不存在");
        }

    }

    @Override
    public List<SysUserExt> queryUserExtListByRoleId(String roleId, SysUser sysUser) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(roleId)) {
            paramMap.put("roleId", roleId);
            paramMap.put("sysUser", sysUser);
            return sysRoleExtMapper.queryUserExtListByRoleId(paramMap);
        }
        return null;
    }
}
