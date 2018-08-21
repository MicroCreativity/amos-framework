package com.amos.framework.exception;

public interface ErrorCode {

	//////================跟客户端请求有关的错误===============//////////
	/** 请求参数错误 */
	String PARAMETER_HAS_ERROR = "00";
	/** 参数{0}值不能为空  */
	String PARAMETER_HAS_EMPTY = "01";
	/** 请求参数不适合,如格式错误 */
	String PARAMETER_HAS_ILLEGALSTATE = "02";
	/** 请求参数数组及集合类越界 */
	String PARAMETER_HAS_INDEXOUTOFBOUNDS = "03";
	/** 请求参数存在空指针或空值  */
	String PARAMETER_HAS_NULLPOINTER = "04";
	/** 参数值不在范围内  */
	String PARAMETER_HAC_UNLIMITED_ERROR = "05";
	/**请求参数过长*/
	String PARAMETER_TOO_LONG="06";
	
	//////================跟系统有关的错误===============//////////
	/** 系统错误  */
	String SYSTEM_HAS_ERROR = "D0";
	/** 系统异常，参数为空  */
	String SYSTEM_HAS_EMPTY = "D1";
	/** 网络异常 */
	String SYSTEM_HAS_NETWORK_ERROR = "D2";
	
	//////================跟业务有关的错误===============//////////
	/** 未知错误 */
	String BIZ_HAS_UNKNOW_ERROR = "60";
	/** 操作失败  */
	String BIZ_OPERATE_FAILURE = "61";
	/** 空指针异常 */
	String BIZ_NULLPOINTER_ERROR = "62";
	/** 系统不支持的业务 */
	String BIZ_NOT_SUPPORT = "63";
	/** 登陆相关的错误  */
	String BIZ_LOGIN_ERROR = "64";
	/** 操作相关的错误  */
	String BIZ_OPERATE_ERROR = "65";
	/** 操作超时  */
	String BIZ_OPERATE_OUTTIME = "66";
}
