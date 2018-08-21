package com.amos.framework.utils;

import org.apache.logging.log4j.ThreadContext;

import com.amos.framework.constants.Constants;
import com.amos.framework.output.ResultOutput;

public final class JsonResultUtils {

	public static <T> ResultOutput<T> getJsonResult(T data,Boolean success,String errorCode,String errorMessage){
		
		ResultOutput<T> reslt = new ResultOutput<T>();
		
		//获取追踪ID，该ID由TraceIdInterceptor.java生成
		String traceId = ThreadContext.get(Constants.TRACE_ID_KEY);
		
		reslt.setTraceId(traceId);
		reslt.setSuccess(success);
		reslt.setData(data);
		reslt.setCode(errorCode);
		reslt.setMsg(errorMessage);
		reslt.setTimestamp(System.currentTimeMillis() + "");
		return reslt;
	}
}
