package com.kevin.dao.mapper;

import com.kevin.model.SysCfg;
import com.kevin.model.SysCfgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysCfgMapper {
    int countByExample(SysCfgExample example);

    int deleteByExample(SysCfgExample example);

    int deleteByPrimaryKey(String cfgCode);

    int insert(SysCfg record);

    int insertSelective(SysCfg record);

    List<SysCfg> selectByExample(SysCfgExample example);

    SysCfg selectByPrimaryKey(String cfgCode);

    int updateByExampleSelective(@Param("record") SysCfg record, @Param("example") SysCfgExample example);

    int updateByExample(@Param("record") SysCfg record, @Param("example") SysCfgExample example);

    int updateByPrimaryKeySelective(SysCfg record);

    int updateByPrimaryKey(SysCfg record);
}