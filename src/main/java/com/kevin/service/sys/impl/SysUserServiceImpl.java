package com.kevin.service.sys.impl;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.GeneralMethod;
import com.kevin.common.shiro.PasswordHelper;
import com.kevin.common.utils.UUIDUtil;
import com.kevin.dao.mapper.SysUserMapper;
import com.kevin.model.SysUser;
import com.kevin.model.SysUserExample;
import com.kevin.service.sys.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl implements ISysUserService {
    private static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper sysUserMapper;

//    @Autowired
//    private RedisUtil redisUtil;

    @Override
    @CachePut(value = "currUser",key = "'sysUser_'+#sysUser.getUserId()")
    public int save(SysUser sysUser) {
        if (StringUtils.isBlank(sysUser.getUserId())) {//新增
            sysUser.setUserId(UUIDUtil.getUUID());
            sysUser.setUserPwd(PasswordHelper.encryptPassword(sysUser.getUserId(), GlobalConstant.RESET_PASSWORD));
            GeneralMethod.setRecordInfo(sysUser, true);
            return sysUserMapper.insertSelective(sysUser);
        } else {//修改
            GeneralMethod.setRecordInfo(sysUser, false);
            return sysUserMapper.updateByPrimaryKeySelective(sysUser);
        }
    }

    @Override
    public int logicallyDeleteById(String id) {
        if (StringUtils.isNotBlank(id)) {
            //update
            SysUser user = new SysUser();
            //set
            user.setRecordState(GlobalConstant.N);
            //where
            user.setUserId(id);
            return save(user);
        }
        return GlobalConstant.ZERO;
    }

    @Override
    public int deleteById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return sysUserMapper.deleteByPrimaryKey(id);
        }
        return GlobalConstant.ZERO;
    }

    @Override
//    @Cacheable(value = "currUser",key="'sysUser_'+#sysUser.getUserId()")
    public List<SysUser> queryList(SysUser sysUser, String orderByClause) {
        System.err.println("没有走缓存！"+sysUser.getUserId());
//        SysUser setRedisCurrUser = (SysUser)redisUtil.get("currUser");
//        logger.debug("-------------redis缓存中获取当前用户信息:--------------------" + setRedisCurrUser.getUserName() + setRedisCurrUser.getUserAcc());
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria().andRecordStateEqualTo(GlobalConstant.Y);
        andCrieria(sysUser, criteria);
        /* 排序字段 */
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        return sysUserMapper.selectByExample(example);
    }

    @Override
    public SysUser getById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return sysUserMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    private void andCrieria(SysUser user, SysUserExample.Criteria criteria) {
        if (StringUtils.isNotBlank(user.getUserName())) {
            criteria.andUserNameLike(GlobalConstant.PERCENT + user.getUserName() + GlobalConstant.PERCENT);
        }
        if (StringUtils.isNotBlank(user.getUserPhone())) {
            criteria.andUserPhoneLike(GlobalConstant.PERCENT + user.getUserPhone() + GlobalConstant.PERCENT);
        }
    }

    @Override
    public SysUser insertRoot() {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(GlobalConstant.ROOT_USER_ID);
        sysUser.setUserAcc(GlobalConstant.ROOT_USER_ACC);
        sysUser.setUserName(GlobalConstant.ROOT_USER_NAME);
        String newPwd = PasswordHelper.encryptPassword(GlobalConstant.ROOT_USER_ID, GlobalConstant.RESET_PASSWORD);
        sysUser.setUserPwd(newPwd);
        //sysUser.setStatusId(UserStatusEnum.Activated.getId());
        sysUser.setRecordState(GlobalConstant.Y);
        sysUser.setCreateUserId(GlobalConstant.ROOT_USER_ID);
        sysUser.setCreateTime(Calendar.getInstance().getTime());
        sysUserMapper.insertSelective(sysUser);
        return sysUser;
    }

    @Override
    public SysUser getSysUserByUserAcc(String userAcc) {
        if(StringUtils.isNotBlank(userAcc)){
            SysUserExample example = new SysUserExample();
            example.createCriteria().andUserAccEqualTo(userAcc).andRecordStateEqualTo(GlobalConstant.Y);
            List<SysUser> userList = sysUserMapper.selectByExample(example);
            if (userList != null && !userList.isEmpty()) {
                return userList.get(0);
            }
        }
        return null;
    }

    @Override
    public List<SysUser> checkUnique(SysUser sysUser) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria().andRecordStateEqualTo(GlobalConstant.Y);
        if (StringUtils.isNotBlank(sysUser.getUserAcc())) {
            criteria.andUserAccEqualTo(sysUser.getUserAcc());
        }
        if (StringUtils.isNotBlank(sysUser.getUserPhone())) {
            criteria.andUserPhoneEqualTo(sysUser.getUserPhone());
        }
        //非自己！！
        if (sysUser.getUserId() != null) {
            criteria.andUserIdNotEqualTo(sysUser.getUserId());
        }
        List<SysUser> sysUserList = sysUserMapper.selectByExample(example);
        if (sysUserList != null && !sysUserList.isEmpty()) {
            return sysUserList;
        }
        return null;
    }

    @Override
    @CachePut(value = "currUser", key = "#sysUser.userId")
    public void cachePut(SysUser sysUser) {
        logger.debug("为id、key为:" + sysUser.getUserId() + "数据做了缓存");
    }

    @Override
    @CacheEvict(value = "currUser", key = "#userId")
    public void cacheEvict(String userId) {
        logger.debug("删除了id、key为" + userId + "的数据缓存");
    }

    @Override
    @Cacheable(value = "currUser", key = "#userId")
    public SysUser cacheable(String userId) {
        logger.debug("为id、key为:" + userId + "数据做了缓存");
        return getById(userId);
    }
}
