package com.kevin.common.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.shiro.JWTUtil;
import com.kevin.model.SysUser;
import com.kevin.service.sys.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class HttpServletContext {
	private final static Logger logger = LoggerFactory.getLogger(HttpServletContext.class);

	private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();

	private static ISysUserService userService;
	@Autowired
	public void setService(ISysUserService userService) {
		this.userService = userService;
	}


	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) requestLocal.get();
	}

	public static void setRequest(HttpServletRequest request) {
		requestLocal.set(request);
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) responseLocal.get();
	}

	public static void setResponse(HttpServletResponse response) {
		responseLocal.set(response);
	}

	public static HttpSession getSession() {
		if (null == getRequest()) {
			return null;
		}
		return (HttpSession) getRequest().getSession();
	}

	public static SysUser getCurrentUser() {
		if (null == getSession()) {
			return null;
		}
		//获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.getPrincipal() == null) {
			return null;
		}
		String token = (String) currentUser.getPrincipal();
		logger.debug("-----> token:" + token);
		String userId = JWTUtil.getUsername(token);
		logger.debug("-----> userId:"+userId);
		return userService.cacheable(userId);
	}

//	public static void setSessionAttribute(String key, Object obj) {
//		getSession().setAttribute(key, obj);
//	}
//
//	public static Object getSessionAttribute(String key) {
//		return getSession().getAttribute(key);
//	}

}
