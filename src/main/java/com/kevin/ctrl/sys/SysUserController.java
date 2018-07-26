package com.kevin.ctrl.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.utils.JsonResult;
import com.kevin.model.SysUser;
import com.kevin.service.sys.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation(value = "保存用户", notes = "保存用户", code = 200, produces = "application/json")
    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public JsonResult userList(SysUser sysUser){
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

}
