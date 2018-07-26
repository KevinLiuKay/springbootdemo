package com.kevin.ctrl.pub;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.json.JsonReadContext;
import com.kevin.common.shiro.ShiroConfig;
import com.kevin.common.utils.JsonResult;


/**
 * @author lzk
 * 全局处理Spring Boot的抛出异常。利用@RestControllerAdvice能很好的实现
 */
@RestControllerAdvice
public class ExceptionController {
	
	private static Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public JsonResult handle401(ShiroException e) {
        logger.debug("-----> e.getMessage():"+e.getMessage());
        return new JsonResult(false, e.getMessage(), null);
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public JsonResult handle401() {
        logger.debug("-----> Unauthorized");
        return new JsonResult(false, "Unauthorized", null);
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult globalException(HttpServletRequest request, Throwable ex) {
        logger.debug("-----> e.getMessage():"+ex.getMessage());
        return new JsonResult(false, ex.getMessage(), null);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        logger.debug("-----> statusCode:"+statusCode);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
