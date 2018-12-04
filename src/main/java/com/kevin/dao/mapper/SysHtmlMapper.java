package com.kevin.dao.mapper;

import com.kevin.model.SysHtml;
import com.kevin.model.SysHtmlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysHtmlMapper {
    int countByExample(SysHtmlExample example);

    int deleteByExample(SysHtmlExample example);

    int deleteByPrimaryKey(String guid);

    int insert(SysHtml record);

    int insertSelective(SysHtml record);

    List<SysHtml> selectByExampleWithBLOBs(SysHtmlExample example);

    List<SysHtml> selectByExample(SysHtmlExample example);

    SysHtml selectByPrimaryKey(String guid);

    int updateByExampleSelective(@Param("record") SysHtml record, @Param("example") SysHtmlExample example);

    int updateByExampleWithBLOBs(@Param("record") SysHtml record, @Param("example") SysHtmlExample example);

    int updateByExample(@Param("record") SysHtml record, @Param("example") SysHtmlExample example);

    int updateByPrimaryKeySelective(SysHtml record);

    int updateByPrimaryKeyWithBLOBs(SysHtml record);

    int updateByPrimaryKey(SysHtml record);
}