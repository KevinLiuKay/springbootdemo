package com.kevin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysCfgExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysCfgExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andCfgCodeIsNull() {
            addCriterion("cfg_code is null");
            return (Criteria) this;
        }

        public Criteria andCfgCodeIsNotNull() {
            addCriterion("cfg_code is not null");
            return (Criteria) this;
        }

        public Criteria andCfgCodeEqualTo(String value) {
            addCriterion("cfg_code =", value, "cfgCode");
            return (Criteria) this;
        }

        public Criteria andCfgCodeNotEqualTo(String value) {
            addCriterion("cfg_code <>", value, "cfgCode");
            return (Criteria) this;
        }

        public Criteria andCfgCodeGreaterThan(String value) {
            addCriterion("cfg_code >", value, "cfgCode");
            return (Criteria) this;
        }

        public Criteria andCfgCodeGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_code >=", value, "cfgCode");
            return (Criteria) this;
        }

        public Criteria andCfgCodeLessThan(String value) {
            addCriterion("cfg_code <", value, "cfgCode");
            return (Criteria) this;
        }

        public Criteria andCfgCodeLessThanOrEqualTo(String value) {
            addCriterion("cfg_code <=", value, "cfgCode");
            return (Criteria) this;
        }

        public Criteria andCfgCodeLike(String value) {
            addCriterion("cfg_code like", value, "cfgCode");
            return (Criteria) this;
        }

        public Criteria andCfgCodeNotLike(String value) {
            addCriterion("cfg_code not like", value, "cfgCode");
            return (Criteria) this;
        }

        public Criteria andCfgCodeIn(List<String> values) {
            addCriterion("cfg_code in", values, "cfgCode");
            return (Criteria) this;
        }

        public Criteria andCfgCodeNotIn(List<String> values) {
            addCriterion("cfg_code not in", values, "cfgCode");
            return (Criteria) this;
        }

        public Criteria andCfgCodeBetween(String value1, String value2) {
            addCriterion("cfg_code between", value1, value2, "cfgCode");
            return (Criteria) this;
        }

        public Criteria andCfgCodeNotBetween(String value1, String value2) {
            addCriterion("cfg_code not between", value1, value2, "cfgCode");
            return (Criteria) this;
        }

        public Criteria andCfgValueIsNull() {
            addCriterion("cfg_value is null");
            return (Criteria) this;
        }

        public Criteria andCfgValueIsNotNull() {
            addCriterion("cfg_value is not null");
            return (Criteria) this;
        }

        public Criteria andCfgValueEqualTo(String value) {
            addCriterion("cfg_value =", value, "cfgValue");
            return (Criteria) this;
        }

        public Criteria andCfgValueNotEqualTo(String value) {
            addCriterion("cfg_value <>", value, "cfgValue");
            return (Criteria) this;
        }

        public Criteria andCfgValueGreaterThan(String value) {
            addCriterion("cfg_value >", value, "cfgValue");
            return (Criteria) this;
        }

        public Criteria andCfgValueGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_value >=", value, "cfgValue");
            return (Criteria) this;
        }

        public Criteria andCfgValueLessThan(String value) {
            addCriterion("cfg_value <", value, "cfgValue");
            return (Criteria) this;
        }

        public Criteria andCfgValueLessThanOrEqualTo(String value) {
            addCriterion("cfg_value <=", value, "cfgValue");
            return (Criteria) this;
        }

        public Criteria andCfgValueLike(String value) {
            addCriterion("cfg_value like", value, "cfgValue");
            return (Criteria) this;
        }

        public Criteria andCfgValueNotLike(String value) {
            addCriterion("cfg_value not like", value, "cfgValue");
            return (Criteria) this;
        }

        public Criteria andCfgValueIn(List<String> values) {
            addCriterion("cfg_value in", values, "cfgValue");
            return (Criteria) this;
        }

        public Criteria andCfgValueNotIn(List<String> values) {
            addCriterion("cfg_value not in", values, "cfgValue");
            return (Criteria) this;
        }

        public Criteria andCfgValueBetween(String value1, String value2) {
            addCriterion("cfg_value between", value1, value2, "cfgValue");
            return (Criteria) this;
        }

        public Criteria andCfgValueNotBetween(String value1, String value2) {
            addCriterion("cfg_value not between", value1, value2, "cfgValue");
            return (Criteria) this;
        }

        public Criteria andCfgDescIsNull() {
            addCriterion("cfg_desc is null");
            return (Criteria) this;
        }

        public Criteria andCfgDescIsNotNull() {
            addCriterion("cfg_desc is not null");
            return (Criteria) this;
        }

        public Criteria andCfgDescEqualTo(String value) {
            addCriterion("cfg_desc =", value, "cfgDesc");
            return (Criteria) this;
        }

        public Criteria andCfgDescNotEqualTo(String value) {
            addCriterion("cfg_desc <>", value, "cfgDesc");
            return (Criteria) this;
        }

        public Criteria andCfgDescGreaterThan(String value) {
            addCriterion("cfg_desc >", value, "cfgDesc");
            return (Criteria) this;
        }

        public Criteria andCfgDescGreaterThanOrEqualTo(String value) {
            addCriterion("cfg_desc >=", value, "cfgDesc");
            return (Criteria) this;
        }

        public Criteria andCfgDescLessThan(String value) {
            addCriterion("cfg_desc <", value, "cfgDesc");
            return (Criteria) this;
        }

        public Criteria andCfgDescLessThanOrEqualTo(String value) {
            addCriterion("cfg_desc <=", value, "cfgDesc");
            return (Criteria) this;
        }

        public Criteria andCfgDescLike(String value) {
            addCriterion("cfg_desc like", value, "cfgDesc");
            return (Criteria) this;
        }

        public Criteria andCfgDescNotLike(String value) {
            addCriterion("cfg_desc not like", value, "cfgDesc");
            return (Criteria) this;
        }

        public Criteria andCfgDescIn(List<String> values) {
            addCriterion("cfg_desc in", values, "cfgDesc");
            return (Criteria) this;
        }

        public Criteria andCfgDescNotIn(List<String> values) {
            addCriterion("cfg_desc not in", values, "cfgDesc");
            return (Criteria) this;
        }

        public Criteria andCfgDescBetween(String value1, String value2) {
            addCriterion("cfg_desc between", value1, value2, "cfgDesc");
            return (Criteria) this;
        }

        public Criteria andCfgDescNotBetween(String value1, String value2) {
            addCriterion("cfg_desc not between", value1, value2, "cfgDesc");
            return (Criteria) this;
        }

        public Criteria andWsIdIsNull() {
            addCriterion("ws_id is null");
            return (Criteria) this;
        }

        public Criteria andWsIdIsNotNull() {
            addCriterion("ws_id is not null");
            return (Criteria) this;
        }

        public Criteria andWsIdEqualTo(String value) {
            addCriterion("ws_id =", value, "wsId");
            return (Criteria) this;
        }

        public Criteria andWsIdNotEqualTo(String value) {
            addCriterion("ws_id <>", value, "wsId");
            return (Criteria) this;
        }

        public Criteria andWsIdGreaterThan(String value) {
            addCriterion("ws_id >", value, "wsId");
            return (Criteria) this;
        }

        public Criteria andWsIdGreaterThanOrEqualTo(String value) {
            addCriterion("ws_id >=", value, "wsId");
            return (Criteria) this;
        }

        public Criteria andWsIdLessThan(String value) {
            addCriterion("ws_id <", value, "wsId");
            return (Criteria) this;
        }

        public Criteria andWsIdLessThanOrEqualTo(String value) {
            addCriterion("ws_id <=", value, "wsId");
            return (Criteria) this;
        }

        public Criteria andWsIdLike(String value) {
            addCriterion("ws_id like", value, "wsId");
            return (Criteria) this;
        }

        public Criteria andWsIdNotLike(String value) {
            addCriterion("ws_id not like", value, "wsId");
            return (Criteria) this;
        }

        public Criteria andWsIdIn(List<String> values) {
            addCriterion("ws_id in", values, "wsId");
            return (Criteria) this;
        }

        public Criteria andWsIdNotIn(List<String> values) {
            addCriterion("ws_id not in", values, "wsId");
            return (Criteria) this;
        }

        public Criteria andWsIdBetween(String value1, String value2) {
            addCriterion("ws_id between", value1, value2, "wsId");
            return (Criteria) this;
        }

        public Criteria andWsIdNotBetween(String value1, String value2) {
            addCriterion("ws_id not between", value1, value2, "wsId");
            return (Criteria) this;
        }

        public Criteria andWsNameIsNull() {
            addCriterion("ws_name is null");
            return (Criteria) this;
        }

        public Criteria andWsNameIsNotNull() {
            addCriterion("ws_name is not null");
            return (Criteria) this;
        }

        public Criteria andWsNameEqualTo(String value) {
            addCriterion("ws_name =", value, "wsName");
            return (Criteria) this;
        }

        public Criteria andWsNameNotEqualTo(String value) {
            addCriterion("ws_name <>", value, "wsName");
            return (Criteria) this;
        }

        public Criteria andWsNameGreaterThan(String value) {
            addCriterion("ws_name >", value, "wsName");
            return (Criteria) this;
        }

        public Criteria andWsNameGreaterThanOrEqualTo(String value) {
            addCriterion("ws_name >=", value, "wsName");
            return (Criteria) this;
        }

        public Criteria andWsNameLessThan(String value) {
            addCriterion("ws_name <", value, "wsName");
            return (Criteria) this;
        }

        public Criteria andWsNameLessThanOrEqualTo(String value) {
            addCriterion("ws_name <=", value, "wsName");
            return (Criteria) this;
        }

        public Criteria andWsNameLike(String value) {
            addCriterion("ws_name like", value, "wsName");
            return (Criteria) this;
        }

        public Criteria andWsNameNotLike(String value) {
            addCriterion("ws_name not like", value, "wsName");
            return (Criteria) this;
        }

        public Criteria andWsNameIn(List<String> values) {
            addCriterion("ws_name in", values, "wsName");
            return (Criteria) this;
        }

        public Criteria andWsNameNotIn(List<String> values) {
            addCriterion("ws_name not in", values, "wsName");
            return (Criteria) this;
        }

        public Criteria andWsNameBetween(String value1, String value2) {
            addCriterion("ws_name between", value1, value2, "wsName");
            return (Criteria) this;
        }

        public Criteria andWsNameNotBetween(String value1, String value2) {
            addCriterion("ws_name not between", value1, value2, "wsName");
            return (Criteria) this;
        }

        public Criteria andRecordStateIsNull() {
            addCriterion("record_state is null");
            return (Criteria) this;
        }

        public Criteria andRecordStateIsNotNull() {
            addCriterion("record_state is not null");
            return (Criteria) this;
        }

        public Criteria andRecordStateEqualTo(String value) {
            addCriterion("record_state =", value, "recordState");
            return (Criteria) this;
        }

        public Criteria andRecordStateNotEqualTo(String value) {
            addCriterion("record_state <>", value, "recordState");
            return (Criteria) this;
        }

        public Criteria andRecordStateGreaterThan(String value) {
            addCriterion("record_state >", value, "recordState");
            return (Criteria) this;
        }

        public Criteria andRecordStateGreaterThanOrEqualTo(String value) {
            addCriterion("record_state >=", value, "recordState");
            return (Criteria) this;
        }

        public Criteria andRecordStateLessThan(String value) {
            addCriterion("record_state <", value, "recordState");
            return (Criteria) this;
        }

        public Criteria andRecordStateLessThanOrEqualTo(String value) {
            addCriterion("record_state <=", value, "recordState");
            return (Criteria) this;
        }

        public Criteria andRecordStateLike(String value) {
            addCriterion("record_state like", value, "recordState");
            return (Criteria) this;
        }

        public Criteria andRecordStateNotLike(String value) {
            addCriterion("record_state not like", value, "recordState");
            return (Criteria) this;
        }

        public Criteria andRecordStateIn(List<String> values) {
            addCriterion("record_state in", values, "recordState");
            return (Criteria) this;
        }

        public Criteria andRecordStateNotIn(List<String> values) {
            addCriterion("record_state not in", values, "recordState");
            return (Criteria) this;
        }

        public Criteria andRecordStateBetween(String value1, String value2) {
            addCriterion("record_state between", value1, value2, "recordState");
            return (Criteria) this;
        }

        public Criteria andRecordStateNotBetween(String value1, String value2) {
            addCriterion("record_state not between", value1, value2, "recordState");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNull() {
            addCriterion("create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(String value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(String value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(String value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(String value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(String value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLike(String value) {
            addCriterion("create_user_id like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotLike(String value) {
            addCriterion("create_user_id not like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<String> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<String> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(String value1, String value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(String value1, String value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIsNull() {
            addCriterion("update_user_id is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIsNotNull() {
            addCriterion("update_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdEqualTo(String value) {
            addCriterion("update_user_id =", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotEqualTo(String value) {
            addCriterion("update_user_id <>", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThan(String value) {
            addCriterion("update_user_id >", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("update_user_id >=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThan(String value) {
            addCriterion("update_user_id <", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThanOrEqualTo(String value) {
            addCriterion("update_user_id <=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLike(String value) {
            addCriterion("update_user_id like", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotLike(String value) {
            addCriterion("update_user_id not like", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIn(List<String> values) {
            addCriterion("update_user_id in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotIn(List<String> values) {
            addCriterion("update_user_id not in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdBetween(String value1, String value2) {
            addCriterion("update_user_id between", value1, value2, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotBetween(String value1, String value2) {
            addCriterion("update_user_id not between", value1, value2, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}