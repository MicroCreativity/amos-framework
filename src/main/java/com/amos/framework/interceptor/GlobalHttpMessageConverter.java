package com.amos.framework.interceptor;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.amos.framework.utils.JsonResultUtils;

/**
 * 返回统一json格式
 * @author linyantao
 *
 */
public class GlobalHttpMessageConverter extends FastJsonHttpMessageConverter {
	
	@Override
	@SuppressWarnings("deprecation")
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
			
		obj = JsonResultUtils.getJsonResult(obj, true, null, null);
		outputMessage.getHeaders();
		OutputStream out = outputMessage.getBody();
		String text = JSON.toJSONString(obj,getFeatures());
		byte[] bytes = text.getBytes(getCharset());
		
		out.write(bytes);
	}

	
}
