package com.kevin.model;

import java.util.Date;

public class AssessmentDetail {
    private String id;

    private String title;

    private String type;

    private Integer num;

    private Double complete;

    private Double targetvalue;

    private String target;

    private String company;

    private String checkdept;

    private Integer weight;

    private String chargedept;

    private String content;

    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;

    private String recordState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getComplete() {
        return complete;
    }

    public void setComplete(Double complete) {
        this.complete = complete;
    }

    public Double getTargetvalue() {
        return targetvalue;
    }

    public void setTargetvalue(Double targetvalue) {
        this.targetvalue = targetvalue;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCheckdept() {
        return checkdept;
    }

    public void setCheckdept(String checkdept) {
        this.checkdept = checkdept;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getChargedept() {
        return chargedept;
    }

    public void setChargedept(String chargedept) {
        this.chargedept = chargedept;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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