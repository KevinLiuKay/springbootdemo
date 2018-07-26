package com.kevin.ctrl.pub;

import com.kevin.common.shiro.PasswordHelper;
import com.kevin.common.utils.JsonResult;
import com.kevin.model.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kevin.common.shiro.JWTUtil;
import com.kevin.service.sys.ISysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "token", tags = "token")
public class WebController {

	private static Logger logger = LoggerFactory.getLogger(WebController.class);

	@Autowired
    private ISysUserService userService;
    public void setService(ISysUserService userService) {
        this.userService = userService;
    }


    /**
     * 登入
     * @param userAcc
     * @param userPwd
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录", code = 200, produces = "application/json")
    public JsonResult login(
    		@ApiParam(value = "userAcc", required = true) @RequestParam("userAcc") String userAcc,
    		@ApiParam(value = "userPwd", required = true) @RequestParam("userPwd") String userPwd) {
        SysUser sysUser = userService.getSysUserByUserAcc(userAcc);
        String pwd = PasswordHelper.encryptPassword(sysUser.getUserId(), userPwd);
        if (sysUser.getUserPwd().equals(pwd)) {
            String token = JWTUtil.sign(userAcc, sysUser.getUserPwd());
            logger.debug("-----> token:" + token);
            return new JsonResult(false, "Login success", token);
        } else {
            throw new UnauthorizedException();
        }
    }

    /**
     * 所有人都可以访问，但是用户与游客看到的内容不同
     * @param token
     * @return
     */
    @GetMapping("/article")
    @ApiOperation(value = "article", notes = "article", code = 200, produces = "application/json")
    public JsonResult article( @RequestHeader("token") String token) {
        logger.debug("-----> token:" + token);
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new JsonResult(false, "You are already logged in", null);
        } else {
            return new JsonResult(false, "You are guest", null);
        }
    }

    /**
     * 登入的用户才可以进行访问
     * @return
     */
    @GetMapping("/require_auth")
    @RequiresAuthentication
    @ApiOperation(value = "require_auth", notes = "require_auth", code = 200, produces = "application/json")
    public JsonResult requireAuth() {
        return new JsonResult(false, "You are authenticated", null);
    }

    /**
     * admin的角色用户才可以登入
     * @return
     */
    @GetMapping("/require_role")
    @RequiresRoles("admin")
    @ApiOperation(value = "require_role", notes = "require_role", code = 200, produces = "application/json")
    public JsonResult requireRole() {
        return new JsonResult(false, "You are visiting require_role", null);
    }

    /**
     * 拥有view和edit权限的用户才可以访问
     * @return
     */
    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    @ApiOperation(value = "require_permission", notes = "require_permission", code = 200, produces = "application/json")
    public JsonResult requirePermission() {
        return new JsonResult(false, "You are visiting permission require edit,view", null);
    }

    @RequestMapping(value = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public JsonResult unauthorized() {
        return new JsonResult(false, "Unauthorized", null);
    }
}