package com.kevin.ctrl.pub;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.kevin.common.shiro.PasswordHelper;
import com.kevin.model.SysLog;
import com.kevin.model.SysRole;
import com.kevin.model.SysUser;
import com.kevin.service.sys.ISysLogService;
import com.kevin.service.sys.ISysRoleService;
import com.kevin.service.sys.ISysUserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kevin.common.shiro.JWTUtil;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.utils.JsonResult;
import com.kevin.service.sys.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author lzk
 */
@RestController
@RequestMapping("/login")
@Api(value = "userLogin", tags = "userLogin")
public class LoginController extends CommonController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysUserRoleService userRoleService;
    @Autowired
    private ISysLogService logService;

///    @Autowired
//    private RedisUtil redisUtil;

    @RequestMapping(value = "/login", method = { RequestMethod.POST })
    @ApiOperation(value = "用户登录", notes = "用户登录", code = 200, produces = "application/json")
    public JsonResult login(HttpServletRequest request, HttpServletResponse response,
            @ApiParam(value = "userAcc", required = true) @RequestParam(value = "userAcc", required = true) String userAcc,
            @ApiParam(value = "userPwd", required = true) @RequestParam(value = "userPwd", required = true) String userPwd) {
        JsonResult jsonResult = new JsonResult();
        SysUser currUser = userService.getSysUserByUserAcc(userAcc);
        if (currUser == null) {
            if (!GlobalConstant.ROOT_USER_ACC.equals(userAcc)) {
                // 用户不存在
                jsonResult.setStatus(false);
                jsonResult.setMessage(GlobalConstant.NO_ACCOUNT);
                return jsonResult;
            } else {
                /**
                 * 超级管理员 首次创建！
                 */
                currUser = userService.insertRoot();
                jsonResult.setStatus(true);
                jsonResult.setModel(currUser);
                return jsonResult;
            }
        }
        userPwd = PasswordHelper.encryptPassword(currUser.getUserId(), userPwd);
        if (!userPwd.equals(currUser.getUserPwd())) {
            jsonResult.setStatus(false);
            jsonResult.setMessage(GlobalConstant.ACCOUNT_OR_PASSWORD_ERROR);
            return jsonResult;
        }
        /**
         * redis缓存
         */
///        logger.debug("-------------登录成功将当前用户信息存入redis缓存--------------------");
//        redisUtil.set("currUser", currUser);
//        SysUser setRedisCurrUser = (SysUser)redisUtil.get("currUser");
//        logger.debug("-------------redis缓存中获取当前用户信息:--------------------" + setRedisCurrUser.getUserName() + setRedisCurrUser.getUserAcc());
        userService.cacheable(currUser.getUserId());
        setSessionAttribute(GlobalConstant.CURR_USER, currUser);
        /**
         * 记录日志
         */
        SysLog sysLog = new SysLog();
        try {
            String ipAddr = getIpAddr(request);
            sysLog.setProxyClientIp(ipAddr);
            sysLog.setLocalHost(InetAddress.getLocalHost().toString());
            // 获得客户端的主机名
        } catch (UnknownHostException e) {
            logger.debug("----->  catch UnknownHostException:" + e.getMessage());
        }
        logService.save(sysLog);
        jsonResult.setStatus(true);
        jsonResult.setMessage(GlobalConstant.LOGIN_SUCCESS);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String token = JWTUtil.sign(currUser.getUserId(), currUser.getUserPwd());
        //认证
		resultMap.put("token", token);
        //当前登录用户
		resultMap.put("currUser", currUser);
        //当前用户所有权限
        List<SysRole> roleList = userRoleService.queryRoleListByUserId(currUser.getUserId());
		resultMap.put("roleList", roleList);
        jsonResult.setModel(resultMap);
        return jsonResult;
    }

    /**
     * 获得客户端的ip地址
     * 
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == GlobalConstant.ZERO || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == GlobalConstant.ZERO || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == GlobalConstant.ZERO || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/exit",method = {RequestMethod.POST})
    @ApiOperation(value = "退出登录", notes = "退出登录", code = 200, produces = "application/json")
    public JsonResult exit(HttpServletRequest request, HttpServletResponse response) {
    		JsonResult jsonResult = new JsonResult();
    		jsonResult.setStatus(true);
    		//注销用户，使session失效。
        request.getSession().invalidate();
        return jsonResult;
     }

	/**
	 * session过期
	 * 
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/timeout", method = { RequestMethod.POST })
    @ApiOperation(value = "session过期", notes = "session过期", code = 200, produces = "application/json")
	public JsonResult timeout(HttpServletRequest request) {
		logger.debug("-----> timeout !");
	    return new JsonResult(false, "请重新登录！", null);
	}
    
}
