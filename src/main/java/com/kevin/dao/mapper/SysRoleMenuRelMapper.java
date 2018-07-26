package com.kevin.dao.mapper;

import com.kevin.model.SysRoleMenuRel;
import com.kevin.model.SysRoleMenuRelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysRoleMenuRelMapper {
    int countByExample(SysRoleMenuRelExample example);

    int deleteByExample(SysRoleMenuRelExample example);

    int deleteByPrimaryKey(String roleMenuId);

    int insert(SysRoleMenuRel record);

    int insertSelective(SysRoleMenuRel record);

    List<SysRoleMenuRel> selectByExample(SysRoleMenuRelExample example);

    SysRoleMenuRel selectByPrimaryKey(String roleMenuId);

    int updateByExampleSelective(@Param("record") SysRoleMenuRel record, @Param("example") SysRoleMenuRelExample example);

    int updateByExample(@Param("record") SysRoleMenuRel record, @Param("example") SysRoleMenuRelExample example);

    int updateByPrimaryKeySelective(SysRoleMenuRel record);

    int updateByPrimaryKey(SysRoleMenuRel record);
}