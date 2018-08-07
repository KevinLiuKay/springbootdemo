package com.kevin.service.sys.impl;


import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
import com.kevin.common.core.HttpServletContext;
import com.kevin.common.utils.JsonResult;
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
    public JsonResult saveRoleUserList(List<String> userIdList, String roleId) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(false);
        SysUser currUser = HttpServletContext.getCurrentUser();
        if(currUser == null) {
            throwException(GlobalConstant.ZERO,GlobalConstant.SESSION_OUT_TIME);
        }
        if(StringUtils.isNotBlank(roleId)) {
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(roleId);
            //查询userRole关联表中，当前roleId绑定的所有用户
            List<SysUserRole> roleUserList = queryList(userRole,"");
            /**
             * userIds不为空分3种情况
             * 1.roleUser存在部分需要配置的用户角色信息（复用那部分存在的用户角色信息，其他新增）
             * 2.roleUser存在部分不需要的用户角色信息（删除那部分存在的不需要的用户角色信息，新增全部）
             * 3.roleUser不存在需要配置的用户角色信息 （新增全部）
             */
            if(userIdList != null && !userIdList.isEmpty()){
                //数据库userRole关联表中存在记录
                int result = GlobalConstant.ZERO;
                SysUserRole sysUserRole = new SysUserRole();
                if(roleUserList != null && !roleUserList.isEmpty()) {
                    //将userRole关联表中，当前roleId下的所有用户逻辑删除
                    userRole.setRoleId(roleId);
                    userRole.setUpdateUserId(currUser.getUserId());
                    userRole.setUpdateTime(Calendar.getInstance().getTime());
                    throwException(sysRoleExtMapper.deleteRoleUser(userRole),"删除用户角色关联表失败");

                    if (userIdList != null && !userIdList.isEmpty()) {
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
                                    sysUserRole.setRoleId(roleId);
                                    sysUserRole.setUserId(userId);
                                    result += save(sysUserRole);
                                } else {
                                    // 修改
                                    sysUserRole.setRecordState(GlobalConstant.Y);
                                    sysUserRole.setUpdateUserId(currUser.getUserId());
                                    sysUserRole.setUpdateTime(Calendar.getInstance().getTime());
                                    result += save(sysUserRole);
                                }
                            } else {
                                // 存在部分不需要的用户角色，删除后全部新增
                                sysUserRole = new SysUserRole();
                                sysUserRole.setRoleId(roleId);
                                sysUserRole.setUserId(userId);
                                result += save(sysUserRole);
                            }
                        }
                        throwException(result,"保存用户角色关联表失败");
                    }
                }else {
                    // 数据库空
                    for (String userId : userIdList) {
                        sysUserRole = new SysUserRole();
                        sysUserRole.setRoleId(roleId);
                        sysUserRole.setUserId(userId);
                        result += save(sysUserRole);
                    }
                    throwException(result,"保存用户角色关联表失败");
                }
                jsonResult.setStatus(true);
                jsonResult.setMessage(GlobalConstant.SAVE_SUCCESSED);
            }else {
                /**
                 * userIds为空分两种情况
                 * 1.roleId之前有绑定用户信息，默认删除roleId下面所有用户信息
                 * 2.roleId之前未绑定任何用户信息，直接返回保存成功
                 */
                if (roleUserList != null && !roleUserList.isEmpty()) {
                    //将userRole关联表中，当前roleId下的所有用户逻辑删除
                    userRole.setRoleId(roleId);
                    userRole.setUpdateUserId(currUser.getUserId());
                    userRole.setUpdateTime(Calendar.getInstance().getTime());
                    throwException(sysRoleExtMapper.deleteRoleUser(userRole),"删除用户角色关联表失败");
                }
                jsonResult.setStatus(true);
                jsonResult.setMessage(GlobalConstant.SAVE_SUCCESSED);
            }
            return jsonResult;
        }else {
            jsonResult.setMessage(GlobalConstant.PARAM_IS_EMPTY);
            return jsonResult;
        }
    }

    @Override
    public JsonResult deleteRoleUser(String userId, String roleId) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(false);
        if (StringUtils.isBlank(roleId)) {
            jsonResult.setMessage(GlobalConstant.PARAM_IS_EMPTY);
            return jsonResult;
        }
        SysUser currUser = HttpServletContext.getCurrentUser();
        if(currUser == null) {
            throwException(GlobalConstant.ZERO,GlobalConstant.SESSION_OUT_TIME);
        }
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(roleId);
        long roleIdExistNum = count(sysUserRole);
        if(roleIdExistNum != GlobalConstant.ZERO) {
            sysUserRole.setUserId(userId);
            sysUserRole.setUpdateUserId(currUser.getUserId());
            sysUserRole.setUpdateTime(Calendar.getInstance().getTime());
            throwException(sysRoleExtMapper.deleteRoleUser(sysUserRole),"删除用户角色关联表失败");

        }
        jsonResult.setStatus(true);
        jsonResult.setMessage(GlobalConstant.DELETE_SUCCESSED);
        return jsonResult;
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
    public void throwException(int result,String name){
        if (result == 0) {
            throw new RuntimeException(name);
        }
    }
}
