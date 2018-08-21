package com.amos.framework.exception;

public class BusinessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorCode;
	
	private String errormessage;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	public BusinessException(String errorCode, String errormessage) {
		super();
		this.errorCode = errorCode;
		this.errormessage = errormessage;
	}

	public BusinessException(String errormessage) {
		super();
		this.errormessage = errormessage;
	}

	public BusinessException() {
		super();
	}
	
}
