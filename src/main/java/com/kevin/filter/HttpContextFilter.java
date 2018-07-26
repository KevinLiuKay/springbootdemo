package com.kevin.filter;
import java.io.IOException;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kevin.common.GlobalConstant.GlobalConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kevin.common.core.HttpServletContext;
import com.kevin.common.GlobalConstant.GlobalConstant;

public class HttpContextFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(HttpContextFilter.class);
	
	@Override
	public void destroy() { }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest =(HttpServletRequest) request;
		HttpServletResponse httpResponse =(HttpServletResponse) response;
		/**
	     * 对跨域提供支持
	     */
		httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
		httpResponse.setHeader("Access-Control-Allow-Methods", "*");
		httpResponse.setHeader("Access-Control-Max-Age", "3600");
		httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-width,Authorization,content-type");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		logger.debug("-----> sessionID:"+httpRequest.getSession().getId());
		
		String getPath = StringUtils.defaultString(httpRequest.getRequestURL().toString());
		logger.debug("-----> requestURL:"+ getPath);
		logger.debug("-----> queryString:"+ httpRequest.getQueryString());
		String servletPath = httpRequest.getServletPath();
		httpRequest.setAttribute(GlobalConstant.PAGE_SERVLET_PATH, servletPath);
		HttpServletContext.setRequest(httpRequest);
		HttpServletContext.setResponse(httpResponse);
		
		chain.doFilter(request, response);	
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException { }
}