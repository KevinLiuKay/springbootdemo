package com.kevin.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class SysUser {
    private String userId;

    private String userName;

    private String userAcc;
    /*
     * serialize:是否需要序列化属性.
     */
    @JSONField(serialize=false)
    private String userPwd;

    private String userPhone;

    private Integer userGender;

    private String headortraitpath;

    private String userAddr;

    private String createUserId;
    @JSONField(format="yyyy-MM-dd HH:mm")
    private Date createTime;

    private String updateUserId;
    @JSONField(format="yyyy-MM-dd HH:mm")
    private Date updateTime;

    private String recordState;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAcc() {
        return userAcc;
    }

    public void setUserAcc(String userAcc) {
        this.userAcc = userAcc;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getUserGender() {
        return userGender;
    }

    public void setUserGender(Integer userGender) {
        this.userGender = userGender;
    }

    public String getHeadortraitpath() {
        return headortraitpath;
    }

    public void setHeadortraitpath(String headortraitpath) {
        this.headortraitpath = headortraitpath;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRecordState() {
        return recordState;
    }

    public void setRecordState(String recordState) {
        this.recordState = recordState;
    }
}