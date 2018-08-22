package com.kevin.ctrl.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.utils.JsonResult;
import com.kevin.model.SysRole;
import com.kevin.model.SysUser;
import com.kevin.model.SysUserRole;
import com.kevin.model.ext.sys.SysUserExt;
import com.kevin.service.sys.ISysRoleService;
import com.kevin.service.sys.ISysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lzk
 */
@RestController
@RequestMapping("/sysRole")
@Api(value = "roleManage", tags = "roleManage")
public class SysRoleController {
    private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @ApiOperation(value = "查询角色列表（可按条件查询）", notes = "查询角色列表（可按条件查询）", code = 200, produces = "application/json")
    @RequestMapping(value = "/roleList", method = RequestMethod.POST)
    public JsonResult roleList(SysRole sysRole,
                               @ApiParam(name = "pageNum",value = "当前页", required = false) @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @ApiParam(name = "pageSize",value = "每页的条数", required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                               @ApiParam(name = "pageHelper",value = "是否开启分页", required = false) @RequestParam(name = "pageHelper", required = false, defaultValue = "true") boolean pageHelper){
        JsonResult jsonResult = new JsonResult();
        try {
            if(pageHelper) {
                PageHelper.startPage(pageNum, pageSize);
            }
            List<SysRole> roleList = sysRoleService.queryList(sysRole,"create_time asc");
            PageInfo<SysRole> pageInfo = new PageInfo<SysRole>(roleList);
            jsonResult.setStatus(true);
            jsonResult.setMessage(GlobalConstant.OPERATE_SUCCESSED);
            jsonResult.setModel(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(false);
            jsonResult.setMessage(e.getClass().getName() + ":" + e.getMessage());
        }
        return jsonResult;
    }

    @ApiOperation(value = "保存角色", notes = "保存角色", code = 200, produces = "application/json")
    @RequestMapping(value = "/saveRole", method = RequestMethod.POST)
    public JsonResult saveRole(SysRole sysRole){
        JsonResult jsonResult = new JsonResult();
        try {
            int result = sysRoleService.save(sysRole);
            if (GlobalConstant.ZERO !=  result) {
                jsonResult.setStatus(true);
                jsonResult.setMessage(GlobalConstant.SAVE_SUCCESSED);
            }else {
                jsonResult.setStatus(false);
                jsonResult.setMessage(GlobalConstant.SAVE_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(false);
            jsonResult.setMessage(e.getClass().getName() + ":" + e.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "/deleteRole" , method = RequestMethod.POST)
    @ApiOperation(value = "删除角色", notes = "删除角色", code = 200, produces = "application/json")
    public JsonResult deleteRole(@ApiParam(name = "roleId", required = true) @RequestParam(name = "roleId", required = true) String roleId) {
        JsonResult jsonResult = new JsonResult();
        try {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setRecordState(GlobalConstant.Y);
            //查询当前角色下存在的用户数量
            long roleExistUserNum = sysUserRoleService.count(sysUserRole);
            if (GlobalConstant.ZERO < roleExistUserNum) {
                jsonResult.setStatus(false);
                jsonResult.setMessage("该角色已关联多个用户,无法删除！");
                return jsonResult;
            }
            int result = sysRoleService.logicallyDeleteById(roleId);
            // 删除成功
            if (GlobalConstant.ZERO !=  result) {
                jsonResult.setStatus(true);
                jsonResult.setMessage(GlobalConstant.DELETE_SUCCESSED);
            }else {
                jsonResult.setStatus(false);
                jsonResult.setMessage(GlobalConstant.DELETE_FAIL);
            }
        }catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(false);
            jsonResult.setMessage(e.getClass().getName() + ":" + e.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "/saveRoleUserList", method = { RequestMethod.POST})
    @ApiOperation(value = "角色配置用户", notes = "角色配置用户", code = 200, produces = "application/json")
    public JsonResult saveRoleUserList(
            @ApiParam(name = "userIds",value = "ids数组", required = false) @RequestParam(name = "userIds", required = false) String [] userIds,
            @ApiParam(name = "roleId", required = true) @RequestParam(name = "roleId", required = true) String roleId) {
        List<String> userIdList = new ArrayList<String>();
        if(userIds != null) {
            userIdList = Arrays.asList(userIds);
        }
        return sysUserRoleService.saveRoleUserList(userIdList, roleId);
    }

    @RequestMapping(value = "/queryUserListByRoleId", method = { RequestMethod.POST})
    @ApiOperation(value = "通过roleId查询user信息", notes = "通过roleId查询user信息", code = 200, produces = "application/json")
    public JsonResult queryUserListByRoleId(SysUser sysUser,
                                            @ApiParam(name = "roleId", required = true) @RequestParam(name = "roleId", required = true) String roleId,
                                            @ApiParam(name = "pageNum", required = false) @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
                                            @ApiParam(name = "pageSize", required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUserExt> sysUserList = sysUserRoleService.queryUserExtListByRoleId(roleId, sysUser);
        PageInfo<SysUserExt> pageInfo = new PageInfo<SysUserExt>(sysUserList);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(true);
        jsonResult.setModel(pageInfo);
        return jsonResult;
    }

    @RequestMapping(value = "/deleteUserRole", method = RequestMethod.POST)
    @ApiOperation(value = "删除用户角色关联", notes = "删除用户角色关联", code = 200, produces = "application/json")
    public JsonResult deleteUserRole(
            @ApiParam(name = "userId", required = false) @RequestParam(name = "userId", required = false) String userId,
            @ApiParam(name = "roleId", required = true) @RequestParam(name = "roleId", required = true) String roleId) {
        return sysUserRoleService.deleteRoleUser(userId, roleId);
    }
}
