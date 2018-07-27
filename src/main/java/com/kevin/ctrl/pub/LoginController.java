package com.kevin.ctrl.pub;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.kevin.common.shiro.PasswordHelper;
import com.kevin.model.SysUser;
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

@RestController
@RequestMapping("/login")
@Api(value = "userLogin", tags = "userLogin")
public class LoginController extends CommonController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ISysUserService userService;

//    @Autowired
//    private RedisUtil redisUtil;

    /**
     * 首页
     * 
     * @return
     */
    /*
     * @RequestMapping(value = "/home", method = { RequestMethod.POST,
     * RequestMethod.GET }) public JsonResult home(HttpServletRequest request,
     * HttpServletResponse response, Model model) { JsonResult result = new
     * JsonResult(); result.setStatus(true);
     * result.setModel("weChat/main/home"); return result; }
     */
    @RequestMapping(value = "/login", method = { RequestMethod.POST })
    @ApiOperation(value = "用户登录", notes = "用户登录", code = 200, produces = "application/json")
    public JsonResult login(HttpServletRequest request, HttpServletResponse response,
            @ApiParam(value = "userAcc", required = true) @RequestParam(value = "userAcc", required = true) String userAcc,
            @ApiParam(value = "userPwd", required = true) @RequestParam(value = "userPwd", required = true) String userPwd) {
        JsonResult jsonResult = new JsonResult();
        SysUser currUser = userService.getSysUserByUserAcc(userAcc);
//        if (currUser == null) {
//            currUser = userService.getByUserMobile(userAcc);
//        }
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
//        logger.debug("-------------登录成功将当前用户信息存入redis缓存--------------------");
//        redisUtil.set("currUser", currUser);
//        SysUser setRedisCurrUser = (SysUser)redisUtil.get("currUser");
//        logger.debug("-------------redis缓存中获取当前用户信息:--------------------" + setRedisCurrUser.getUserName() + setRedisCurrUser.getUserAcc());
        userService.cacheable(currUser.getUserId());
        setSessionAttribute(GlobalConstant.CURR_USER, currUser);
        /**
         * 记录日志
         */
//        SysLog sysLog = new SysLog();
//        sysLog.setLogTypeId(1);
//        try {
//            String ipAddr = getIpAddr(request);
//            sysLog.setProxyClientIp(ipAddr);
//            sysLog.setLocalHost(InetAddress.getLocalHost().toString());
//            // 获得客户端的主机名
//        } catch (UnknownHostException e) {
//            logger.debug("----->  catch UnknownHostException:" + e.getMessage());
//        }
//        logService.save(sysLog);
        // 加载用户所有菜单
        //loadRoleListAndMenuList();
        jsonResult.setStatus(true);
        jsonResult.setMessage(GlobalConstant.LOGIN_SUCCESS);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String token = JWTUtil.sign(currUser.getUserId(), currUser.getUserPwd());
//        List<SysRole> roleList = roleService.queryRoleListByUserId(currUser.getUserId());
		logger.debug("-----> token:" + token);
		resultMap.put("token", token);//认证
		resultMap.put("currUser", currUser);//当前登录用户
//		resultMap.put("roleList", roleList);//当前用户所有权限
        jsonResult.setModel(resultMap);
        return jsonResult;
    }


	/**
	 * 加载载用户角色和权限
	 */
//	public void loadRoleListAndMenuList(){
//		SysUser currUser = HttpServletContext.getCurrentUser();
//		String userId = currUser.getUserId();
//		String userAcc = currUser.getUserAcc();
//		// 获取该用户的所有角色
//		List<SysRole> currUserRoleList = null;
//		// 该用户所有菜单
//		List<SysMenu> menuList = null;
//        if (!GlobalConstant.ROOT_USER_ACC.equals(userAcc)) {
//            currUserRoleList = roleService.queryRoleListByUserId(userId);
//            if (currUserRoleList != null && !currUserRoleList.isEmpty()) {
//            		Map<String, SysMenu> sysMenuMap = new HashMap<String, SysMenu>();
//            		for (SysRole role : currUserRoleList) {
//            			List<SysMenu> tempList = ServletContextListenerImpl.getSysMenuListByRoleId(role.getRoleId());
//            			if(tempList != null && !tempList.isEmpty()){
//            				for (SysMenu sysMenu : tempList) {
//            					SysMenu tempMenu = sysMenuMap.get(sysMenu.getMenuId());
//            					if(tempMenu == null) {
//            						sysMenuMap.put(sysMenu.getMenuId(), tempMenu);
//            					}
//						}
//            			}
//				}
//            		menuList = new ArrayList<>();
//            		//遍历map中的值
//            		for (SysMenu sysMenu : sysMenuMap.values()) {
//            			menuList.add(sysMenu);
//            		}
//            }else {//默认角色菜单
//            		//TODO
//            }
//        }else{
//			//--超级管理员
//			menuList = ServletContextListenerImpl.getSysMenuListByRoleId("0");
//		}
//        List<SysMenu> topMenuList = null;//一级菜单
//        List<String> currUserUrlList = null;//当前用户所能访问的URL
//        Map<String, List<SysMenu>> childMenuMap = null;
//		if(menuList != null && !menuList.isEmpty()) {
//			topMenuList = new ArrayList<SysMenu>();
//	        currUserUrlList = new ArrayList<String>();
//	        childMenuMap = new HashMap<String, List<SysMenu>>();
//			/**
//			 *  根据Pid组织Map:
//			 *  key: pid; value: 记录的pid是该pid的List
//			 */
//			for (SysMenu sysMenu : menuList) {
//				//排除不需要显示的菜单
//				if(sysMenu == null || sysMenu.getShowStatus() == null || GlobalConstant.ZERO == sysMenu.getShowStatus()) {
//					continue;
//				}
//				String menuParentId = sysMenu.getMenuParentId();// 获取Pid
//				List<SysMenu> tempList = childMenuMap.get(menuParentId);// 根据Pid去查找上级菜单
//				if (tempList == null) {
//					tempList = new ArrayList<SysMenu>();
//				}
//				tempList.add(sysMenu);
//				childMenuMap.put(menuParentId, tempList);
//				// TODO 获取所有访问地址，并放入Session==>后期扩展另加方法！！！！！
//				String url = sysMenu.getMenuUrl();
//				if (StringUtils.isNotBlank(url)) {
//					currUserUrlList.add(url);
//				}
//			}
//			topMenuList = childMenuMap.get(0L);// 获得一级菜单
//		}
//		/*setSessionAttribute("topMenuList", topMenuList);
//		setSessionAttribute("childMenuMap", childMenuMap);
//		setSessionAttribute(GlobalConstant.CURR_USER_URL_LIST, currUserUrlList);*/
//	}

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
	
	/**
	 * 无权限访问！
	 * @return
	 */
    @RequestMapping(value = "/illegalAccess",method = {RequestMethod.POST})
	public JsonResult illegalAccess(HttpServletRequest request) {
		logger.debug("-----> illegalAccess !");
		return new JsonResult(false, "无权限访问！", null);
	}
	
    /**
     * 动态登陆
     * @param request
     * @param userMobile
     * @param verifyCode
     * @return
     */
//    @RequestMapping(value = "/dynamicLoginByMobile",method = {RequestMethod.POST})
//    @ResponseBody
//    @ApiOperation(value = "动态登陆",notes = "动态登陆",code = 200,produces = "application/json")
//    public JsonResult dynamicLoginByMobile(HttpServletRequest request,
//            @ApiParam(name = "userMobile",value = "手机号",required = true) @RequestParam(name = "userMobile",required = true)String userMobile,
//            @ApiParam(name = "verifyCode",value = "动态验证码",required = true) @RequestParam(name = "verifyCode",required = true)int verifyCode){
//        JsonResult result = new JsonResult();
//        logger.debug("sessionId:"+request.getSession().getId());
//        SysUser currUser = userService.getByUserMobile(userMobile);
//        if (currUser == null) {
//            if (!GlobalConstant.ROOT_USER_ACC.equals(userMobile)) {
//                // 用户不存在
//                result.setStatus(false);
//                result.setMessage("该用户未注册，请先注册！");
//                return result;
//            }
//        }
//        Integer code = (Integer) request.getSession().getAttribute("pwdByverifyCode");
//        if (verifyCode != code) {
//            result.setStatus(false);
//            result.setMessage(GlobalConstant.VALI_CODE_ERROR);
//            return result;
//        }
//        // 设置当前用户
////        setSessionAttribute(GlobalConstant.CURR_USER, currUser);
//        result.setStatus(true);
//        result.setMessage(GlobalConstant.LOGIN_SUCCESS);
//        result.setModel(currUser);
//        return result;
//    }
    
}
