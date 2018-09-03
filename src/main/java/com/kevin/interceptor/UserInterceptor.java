package com.kevin.interceptor;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.core.HttpServletContext;
import com.kevin.model.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lzk
 */
public class UserInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory.getLogger(UserInterceptor.class);

	/**
	 * rootUrl
	 */
	private static Map<String, Boolean> EXCLUDE_MAPPING;

	static {
		EXCLUDE_MAPPING = new HashMap<String, Boolean>();
		EXCLUDE_MAPPING.put("sysCfg", true);
		EXCLUDE_MAPPING.put("sysLog", true);
	}

	/**
	 * 在请求处理之前进行调用（Controller方法调用之前）
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		String getPath = request.getRequestURL().toString();
		String requestUrl = request.getRequestURI();
        String contextPath = request.getContextPath();  
        String url = requestUrl.substring(contextPath.length());
        
        /**
         * 截取请求前缀： inspect
         * 判断用户是否具有请求该URL前缀的权限！
         * 
         */
        String[] urlArray = url.split("/");
		//localhost:8080/springbootdemo
        if(GlobalConstant.ONE > urlArray.length) {
        	return true;
        }
        String urlPrefix = urlArray[1];  
        logger.debug("-----> urlPrefix:"+ urlPrefix);
		SysUser currUser = HttpServletContext.getCurrentUser();
		if (currUser == null) {
			request.getRequestDispatcher("/login/timeout").forward(request, response);
			return false;
		}else{//==》权限拦截，控制非法访问！
			if(EXCLUDE_MAPPING.get(urlPrefix) != null) {
				return true;
			}
		}
		return true;
	}

	/**
	 * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
	 */
	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response, Object obj, ModelAndView mav)
			throws Exception {
	}

	/**
	 * 在整个请求结束之后被调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response, Object obj, Exception err)
			throws Exception {
	}
}
