package com.kevin.ctrl.pub;

import java.io.FileNotFoundException;
import java.net.BindException;
import java.net.ConnectException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.kevin.common.core.HttpServletContext;
import com.kevin.common.utils.AjaxUtil;
import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.utils.JsonResult;


public class CommonController {
	
	private static Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@ExceptionHandler(Throwable.class)
    public String exception(Throwable e, HttpServletRequest request) {
		logger.error(this.getClass().getCanonicalName()+" CommonController Exception",e);
		boolean isAjax = AjaxUtil.isAjaxRequest(request);
		if(isAjax){
			e.printStackTrace();
			return e.getMessage();
		}else{
	        e.printStackTrace();
	        request.setAttribute("exception", e);
	        return "error/500";			
		}
    }
	
	protected void setSessionAttribute(String key, Object obj){
		HttpServletContext.getSession().setAttribute(key, obj);
	}
	
	protected Object getSessionAttribute(String key){
		return HttpServletContext.getSession().getAttribute(key);
	}
	
	protected void removeSessionAttribute(String key){
		HttpServletContext.getSession().removeAttribute(key);
	}
	
	/**
	 * 基于异常处理机制
	 * 
	 * @param request
	 * @param exception
	 * @return
	 * @author renlinggao
	 */
	@ExceptionHandler
	@ResponseBody
	public JsonResult exp(HttpServletRequest request, Exception exception) {
		logger.error(exception.getMessage(),exception);
		String message = GlobalConstant.EMPTY;
//		 if (exception instanceof NoSuchRequestHandlingMethodException) {// 404
//			message = "路径请求错误！";
//		}
		if (exception instanceof NumberFormatException) {
			message = "参数类型错误！";
		} else if (exception instanceof MissingServletRequestParameterException
				|| exception instanceof TypeMismatchException || exception instanceof HttpMessageNotReadableException) { // 400
			message = "接口请求错误(参数类型不匹配或参数缺失)！";
		} else if (exception instanceof NoSuchAlgorithmException) {
			message = "短信网关异常！";
		} else if (exception instanceof BindException) {
			message = "参数绑定错误！";
		} else if (exception instanceof NullPointerException) {
			message = "参数不可为空！";
		} else if (exception instanceof FileNotFoundException) {
			message = "所选文件不存在！";
		} else if (exception instanceof RuntimeException) {
			message = "服务器处理异常";
		} else if (exception instanceof ConnectException) {
			message = "请求连接错误！";
		} else if (exception instanceof MaxUploadSizeExceededException) {
			Long size = (((MaxUploadSizeExceededException) exception).getMaxUploadSize()) / 1024;
			message = "上传文件大小应小于" + size + "KB（" + size / 1024 + "MB）";
		} else {
			message = "系统错误！";
		}
		return new JsonResult(false, "错误原因：" + message, null);
	}
	
}
