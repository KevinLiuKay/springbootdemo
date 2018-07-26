package com.kevin.dao.mapper;

import com.kevin.model.PubFile;
import com.kevin.model.PubFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PubFileMapper {
    int countByExample(PubFileExample example);

    int deleteByExample(PubFileExample example);

    int deleteByPrimaryKey(String fileId);

    int insert(PubFile record);

    int insertSelective(PubFile record);

    List<PubFile> selectByExample(PubFileExample example);

    PubFile selectByPrimaryKey(String fileId);

    int updateByExampleSelective(@Param("record") PubFile record, @Param("example") PubFileExample example);

    int updateByExample(@Param("record") PubFile record, @Param("example") PubFileExample example);

    int updateByPrimaryKeySelective(PubFile record);

    int updateByPrimaryKey(PubFile record);
}