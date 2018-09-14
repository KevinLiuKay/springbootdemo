package com.kevin.dao.mapper;

import com.kevin.model.Assessment;
import com.kevin.model.AssessmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AssessmentMapper {
    int countByExample(AssessmentExample example);

    int deleteByExample(AssessmentExample example);

    int deleteByPrimaryKey(String id);

    int insert(Assessment record);

    int insertSelective(Assessment record);

    List<Assessment> selectByExample(AssessmentExample example);

    Assessment selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Assessment record, @Param("example") AssessmentExample example);

    int updateByExample(@Param("record") Assessment record, @Param("example") AssessmentExample example);

    int updateByPrimaryKeySelective(Assessment record);

    int updateByPrimaryKey(Assessment record);
}