package com.amos.framework.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amos.framework.exception.BusinessException;
import com.amos.framework.exception.ErrorCode;
import com.amos.framework.exception.ParamsException;
import com.amos.framework.i18n.MessageManager;
import com.amos.framework.output.ResultOutput;
import com.amos.framework.utils.JsonResultUtils;

/**
 * ================================
 * 作者:linyantao
 * 时间:2018年7月12日上午11:07:12
 * 内容:全局异常拦截器
 * ================================
 */
@ControllerAdvice
public class GlobalExceptionAspect {
	
	public Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MessageManager messageManager;

	@ExceptionHandler(Exception.class)  
    @ResponseBody
    public ResultOutput<?> processUnauthenticatedException(Exception ex,HttpServletRequest request) throws Exception {
        
		String errorCode = ErrorCode.SYSTEM_HAS_ERROR;;
		String errorMssage = "";
        if(ex instanceof BusinessException){
        	
        	BusinessException be = (BusinessException)ex;
        	errorCode = be.getErrorCode();
        	errorMssage = messageManager.getMsg(be.getErrormessage());
        }else if(ex instanceof ParamsException){
        	
        	ParamsException pe = (ParamsException)ex;
        	errorCode = pe.getErrorCode();
        	errorMssage = messageManager.getMsg(pe.getErrormessage());
        }
        
        LOGGER.error("{} thows exception is {}",request.getRequestURI(),ex);
        ResultOutput<?> result = JsonResultUtils.getJsonResult(null, false, errorCode, errorMssage);
        return result;
    }

}
