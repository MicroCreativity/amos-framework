package com.amos.framework.output;

import java.io.Serializable;

/**
 * ================================
 * 作者:linyantao
 * 时间:2018年7月4日上午11:12:07
 * 内容:统一返回对象
 * ================================
 */
public class ResultOutput<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 876351445102550969L;

	/**
	 * 请求是否成功
	 */
	private Boolean success;
	
	/**
	 * 响应编码
	 */
	private String code;
	
	/**
	 * 响应信息
	 */
	private String msg;
	
	/**
	 * 追踪ID
	 */
	private String traceId;
	
	/**
	 * 时间戳
	 */
	private String timestamp;
	
	/**
	 * 响应数据
	 */
	private T data;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
}
