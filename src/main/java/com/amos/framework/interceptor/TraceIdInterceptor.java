package com.amos.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.amos.framework.constants.Constants;

/**
 * ================================
 * 作者:linyantao
 * 时间:2018年7月12日上午11:20:13
 * 内容:链路追踪拦截器，必须在接收请求第一时间执行，给当前线程上下文追加TraceId
 * ================================
 */
public class TraceIdInterceptor implements HandlerInterceptor{
	
	public Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		
		LOGGER.info("<----------------------------【{}】【END】---------------------------->",request.getRequestURI());
		//清除当前线程所有上下文信息
		ThreadContext.clearAll();
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		
		//给当前线程上下文添加追踪ID
		if(StringUtils.isBlank(ThreadContext.get(Constants.TRACE_ID_KEY))){
			
    		String traceId = System.currentTimeMillis() + "_" + String.format("%05d", (int)((Math.random()*9+1)*10000)) ;
        	ThreadContext.put(Constants.TRACE_ID_KEY, traceId);
    	}
		LOGGER.info("<----------------------------【{}】【START】---------------------------->",request.getRequestURI());
		return true;
	}
	
}
