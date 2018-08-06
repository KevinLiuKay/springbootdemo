package com.kevin.ctrl.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.utils.JsonResult;
import com.kevin.model.SysUser;
import com.kevin.model.ext.sys.SysUserExt;
import com.kevin.service.sys.ISysUserService;
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
@RequestMapping("/user")
@Api(value = "userManage", tags = "userManage")
public class SysUserController {
    private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private ISysUserService sysUserService;

    @ApiOperation(value = "查询用户列表（可按条件查询）", notes = "查询用户列表（可按条件查询）", code = 200, produces = "application/json")
    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    public JsonResult userList(SysUser sysUser,
                               @ApiParam(name = "pageNum",value = "当前页", required = false) @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @ApiParam(name = "pageSize",value = "每页的条数", required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                               @ApiParam(name = "pageHelper",value = "是否开启分页", required = false) @RequestParam(name = "pageHelper", required = false, defaultValue = "true") boolean pageHelper){
        JsonResult jsonResult = new JsonResult();
        try {
            if(pageHelper) {
                PageHelper.startPage(pageNum, pageSize);
            }
            List<SysUser> userList = sysUserService.queryList(sysUser,"create_time asc");
            PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(userList);
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

    @RequestMapping(value = "/queryUserExtList", method = RequestMethod.POST)
    @ApiOperation(value = "查询用户所有信息（组织，角色信息)", notes = "查询用户所有信息（组织，角色信息)", code = 200, produces = "application/json")
    public JsonResult queryUserExtList(SysUser sysUser,
                               @ApiParam(name = "pageNum", required = false) @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
                               @ApiParam(name = "pageSize", required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
        @ApiParam(name = "pageHelper",value = "是否开启分页", required = false) @RequestParam(name = "pageHelper", required = false, defaultValue = "true") boolean pageHelper) {
        JsonResult jsonResult = new JsonResult();
        try {
            if(pageHelper) {
                PageHelper.startPage(pageNum, pageSize);
            }
            PageHelper.startPage(pageNum, pageSize);
            List<SysUserExt> userExtList = sysUserService.queryUserExtList(sysUser);
            PageInfo<SysUserExt> pageInfo = new PageInfo<>(userExtList);
            jsonResult.setModel(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(false);
            jsonResult.setMessage(e.getClass().getName() + ":" + e.getMessage());
        }
        return jsonResult;
    }

    @ApiOperation(value = "保存用户", notes = "保存用户", code = 200, produces = "application/json")
    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public JsonResult saveUser(SysUser sysUser){
        JsonResult jsonResult = new JsonResult();
        try {
            int result = sysUserService.save(sysUser);
            if (GlobalConstant.ZERO !=  result) {
                jsonResult.setStatus(true);
                jsonResult.setMessage(GlobalConstant.OPERATE_SUCCESSED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(false);
            jsonResult.setMessage(e.getClass().getName() + ":" + e.getMessage());
        }
        return jsonResult;
    }

    @ApiOperation(value = "保存用户（包括部门，角色）", notes = "保存用户（包括部门，角色）", code = 200, produces = "application/json")
    @RequestMapping(value = "/saveUserExt", method = RequestMethod.POST)
    public JsonResult saveUserExt(SysUser sysUser,
                                  @ApiParam(name = "orgId", required = false) @RequestParam(name = "orgId", required = false) String orgId,
                                  @ApiParam(name = "roleIds", required = false) @RequestParam(name = "roleIds", required = false) String[] roleIds){
        List<String> roleIdList = new ArrayList<>();
        if(roleIds != null) {
            roleIdList = Arrays.asList(roleIds);
        }
        return sysUserService.saveUserExt(sysUser,orgId,roleIdList);
    }

    @RequestMapping(value = "/deleteUser" , method = RequestMethod.POST)
    @ApiOperation(value = "删除用户", notes = "删除用户", code = 200, produces = "application/json")
    public JsonResult deleteUser(@ApiParam(name = "userId", required = true) @RequestParam(name = "userId", required = true) String userId) {
        JsonResult jsonResult = new JsonResult();
        try {
            int result = sysUserService.logicallyDeleteById(userId);
            // 删除成功
            if (GlobalConstant.ZERO !=  result) {
                jsonResult.setStatus(true);
                jsonResult.setMessage(GlobalConstant.DELETE_SUCCESSED);
            }
        }catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(false);
            jsonResult.setMessage(e.getClass().getName() + ":" + e.getMessage());
        }
        return jsonResult;
    }

    @ApiOperation(value = "批量逻辑删除用户", notes = "批量逻辑删除用户", code = 200, produces = "application/json")
    @RequestMapping(value = "/batchDeleteUser", method = RequestMethod.POST)
    public JsonResult batchDeleteUser(@ApiParam(name = "userIds",value = "ids数组", required = true) @RequestParam(name = "userIds", required = true) String [] userIds){
        JsonResult jsonResult = new JsonResult();
        try {
            List<String> list = Arrays.asList(userIds);
            int result = sysUserService.batchLogicDelete(list);
            if (GlobalConstant.ZERO !=  result) {
                jsonResult.setStatus(true);
                jsonResult.setMessage(GlobalConstant.OPERATE_SUCCESSED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(false);
            jsonResult.setMessage(e.getClass().getName() + ":" + e.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "/checkUnique" , method = RequestMethod.POST)
    @ApiOperation(value = "验证手机号或账号的唯一性", notes = "验证手机号码或账号的唯一性", code = 200, produces = "application/json")
    public JsonResult checkUnique(SysUser sysUser) {
        JsonResult jsonResult = new JsonResult();
        try {
            long result = sysUserService.checkUnique(sysUser);
            if(GlobalConstant.ZERO ==  result) {
                jsonResult.setStatus(true);
                jsonResult.setMessage(GlobalConstant.PHONE_ACC_UNIQUE);
            } else {
                jsonResult.setStatus(false);
                jsonResult.setMessage(GlobalConstant.PHONE_ACC_EXIST);
            }
        }catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(false);
            jsonResult.setMessage(e.getClass().getName() + ":" + e.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "/updateCurrtUserPwd" , method = RequestMethod.POST)
    @ApiOperation(value = "修改当前登录用户的密码", notes = "修改当前登录用户的密码", code = 200, produces = "application/json")
    public JsonResult updateCurrUserPwd(
            @ApiParam(name = "oldUserPwd", required = true) @RequestParam(name = "oldUserPwd", required = true) String oldUserPwd,
            @ApiParam(name = "newUserPwd", required = true) @RequestParam(name = "newUserPwd", required = true) String newUserPwd) {
        return sysUserService.updateCurrtUserPwd(oldUserPwd, newUserPwd);
    }

    @RequestMapping(value = "/resetPwd" , method = RequestMethod.POST)
    @ApiOperation(value = "保存重置密码", notes = "保存重置密码", code = 200, produces = "application/json")
    public JsonResult resetPwd(
            @ApiParam(name = "userId", required = true) @RequestParam(name = "userId", required = true) String userId,
            @ApiParam(name = "userPwd", required = false) @RequestParam(name = "userPwd", required = false) String userPwd) {
        return sysUserService.resetPwd(userId, userPwd);
    }
}
