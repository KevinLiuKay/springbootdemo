package com.kevin.dao.mapper;

import com.kevin.model.SysUserRoleRel;
import com.kevin.model.SysUserRoleRelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysUserRoleRelMapper {
    int countByExample(SysUserRoleRelExample example);

    int deleteByExample(SysUserRoleRelExample example);

    int deleteByPrimaryKey(String userRoleId);

    int insert(SysUserRoleRel record);

    int insertSelective(SysUserRoleRel record);

    List<SysUserRoleRel> selectByExample(SysUserRoleRelExample example);

    SysUserRoleRel selectByPrimaryKey(String userRoleId);

    int updateByExampleSelective(@Param("record") SysUserRoleRel record, @Param("example") SysUserRoleRelExample example);

    int updateByExample(@Param("record") SysUserRoleRel record, @Param("example") SysUserRoleRelExample example);

    int updateByPrimaryKeySelective(SysUserRoleRel record);

    int updateByPrimaryKey(SysUserRoleRel record);
}