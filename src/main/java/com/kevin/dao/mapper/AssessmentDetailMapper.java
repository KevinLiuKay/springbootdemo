package com.kevin.dao.mapper;

import com.kevin.model.AssessmentDetail;
import com.kevin.model.AssessmentDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AssessmentDetailMapper {
    int countByExample(AssessmentDetailExample example);

    int deleteByExample(AssessmentDetailExample example);

    int deleteByPrimaryKey(String id);

    int insert(AssessmentDetail record);

    int insertSelective(AssessmentDetail record);

    List<AssessmentDetail> selectByExample(AssessmentDetailExample example);

    AssessmentDetail selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AssessmentDetail record, @Param("example") AssessmentDetailExample example);

    int updateByExample(@Param("record") AssessmentDetail record, @Param("example") AssessmentDetailExample example);

    int updateByPrimaryKeySelective(AssessmentDetail record);

    int updateByPrimaryKey(AssessmentDetail record);
}