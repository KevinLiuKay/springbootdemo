package com.kevin.dao.mapper;

import com.kevin.model.SysUserOrg;
import com.kevin.model.SysUserOrgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysUserOrgMapper {
    int countByExample(SysUserOrgExample example);

    int deleteByExample(SysUserOrgExample example);

    int deleteByPrimaryKey(String userOrgId);

    int insert(SysUserOrg record);

    int insertSelective(SysUserOrg record);

    List<SysUserOrg> selectByExample(SysUserOrgExample example);

    SysUserOrg selectByPrimaryKey(String userOrgId);

    int updateByExampleSelective(@Param("record") SysUserOrg record, @Param("example") SysUserOrgExample example);

    int updateByExample(@Param("record") SysUserOrg record, @Param("example") SysUserOrgExample example);

    int updateByPrimaryKeySelective(SysUserOrg record);

    int updateByPrimaryKey(SysUserOrg record);
}