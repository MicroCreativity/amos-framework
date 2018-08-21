package com.amos.framework.generator;


/**
 * ===============================
 * 作者：amos lam
 * 时间：2018年8月17日下午2:14:20
 * 内容：
 * 1、代码生成器默认参数
 * 2、采用MVC模式生成代码
 * ===============================
 */
public interface GeneratorConstants {

	/** ===================== 默认值 start ===================== */
	/**
	 * JDBC配置URL
	 */
	String JDBC_URL = "jdbc:mysql://localhost:3306/test";
	
	/**
	 * JDBC用户名
	 */
	String JDBC_USERNAME = "root";
	
	/**
	 * JDBC密码
	 */
	String JDBC_PASSWORD = "123456";
	
	/**
	 * JDBC Driver
	 */
	String JDBC_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

	/**
	 * 项目在硬盘上的基础路径
	 */
	String PROJECT_PATH = System.getProperty("user.dir");
	
	/**
	 * 模板所在位置
	 */
	String TEMPLATE_FILE_PATH = "/generator/template";

	/**
	 * java代码文件路径
	 */
	String JAVA_PATH = "/src/main/java";
	
	/**
	 * 资源文件路径 xxxMapper.xml
	 */
	String RESOURCES_PATH = "/src/main/resources";

	/**
	 * 生成代码所在的基础包名称，可根据自己公司的项目修改
	 * （注意：这个配置修改之后需要手工修改src目录项目默认的包路径，使其保持一致，不然会找不到类）
	 */
	String BASE_PACKAGE = "com.amos.framework";
	
	/**
	 * 生成的Model所在包
	 */
	String MODEL_PACKAGE = ".model";
	
	/**
	 * 生成的Mapper所在包
	 */
	String MAPPER_PACKAGE = ".mapper";
	
	/**
	 * 生成的Service所在包
	 */
	String SERVICE_PACKAGE = ".service";
	
	/**
	 * 生成的ServiceImpl所在包
	 */
	String SERVICE_IMPL_PACKAGE = ".impl";
	
	/**
	 * 生成的Controller所在包
	 */
	String CONTROLLER_PACKAGE = ".controller";
	
	/**
	 * 生成的Controller所在包
	 */
	String CONTROLLER_INPUT_PACKAGE = "input";
	
	/**
	 * 保存前缀
	 */
	String CONTROLLER_SAVE = "Save";
	
	/**
	 * 修改前缀
	 */
	String CONTROLLER_UPDATE = "Update";
	
	/**
	 * 删除前缀
	 */
	String CONTROLLER_DELETE = "Delete";
	
	/**
	 * 根据ID获取详情信息前缀
	 */
	String CONTROLLER_GET_BY_ID = "GetById";
	
	/**
	 * 根据生成实体对象查询分页列表
	 */
	String CONTROLLER_FIND_BY_MODEL = "FindBy";
	
	/**
	 * Mapper插件基础接口的完全限定名
	 */
	String MAPPER_INTERFACE_REFERENCE = ".basic.BasicMapper";
	
	/**
	 * 生成到自动生成目录
	 */
	String AUTO_CONSTANTS = ".auto";
	
	/**
	 * 基础Mapper公共包
	 */
	String BASIC_MAPPER_PUBLIC_PACKAGE = "com.amos.framework.mapper.BasicMapper";
	
	/**
	 * 标记自动生成
	 */
	String SIGN_AUTO = "Auto";
	
	/**
	 * 默认作者
	 */
	String AUTHOR = "amos lam";
	
	/**
	 * 不允许为空
	 */
	String NOT_EMPTY = "0";
	
	/**
	 * 允许为空
	 */
	String EMPTY = "1";
	
	/**
	 * 字段不能为空key
	 */
	String EMPTY_KEY = "empty";
	
	/**
	 * 最小值key
	 */
	String MIN_KEY = "min";
	
	/**
	 * 参数不能为空
	 */
	String NOT_EMPTY_CUE = "{params.is.not.empty}";
	
	/**
	 * 参数不能小于指定值
	 */
	String MUST_BE_MORE_THAN_VALUE = "{params.must.be.more.than.value}";
	
	/**
	 * controller 保存方法输入参数key
	 */
	String CONTROLLER_SAVE_INPUT = "saveInput";
	
	/**
	 * controller 修改方法输入参数key
	 */
	String CONTROLLER_UPDATE_INPUT = "updateInput";
	
	/**
	 * controller 删除方法输入参数key
	 */
	String CONTROLLER_DELETE_INPUT = "deleteInput";
	
	/**
	 * controller 根据ID查询对象方法输入参数key
	 */
	String CONTROLLER_GET_BY_ID_INPUT = "getByIdInput";
	
	/**
	 * controller 根据对象分页查询所有方法输入参数key
	 */
	String CONTROLLER_FIND_BY_MODEL_INPUT = "findByModelInput";
	
	/** ===================== 默认值 end ===================== */
	
	
	
	/** ===================== 配置文件key start ===================== */
	/**
	 * JDBC地址
	 */
	String JDBC_URL_PROPERTIES = "generate.jdbc.url";
	
	/**
	 * JDBC用户名
	 */
	String JDBC_USERNAME_PROPERTIES = "generate.jdbc.username";
	
	/**
	 * JDBC密码
	 */
	String JDBC_PASSWORD_PROPERTIES = "generate.jdbc.password";
	
	/**
	 * JDBC Driver
	 */
	String JDBC_DRIVER_CLASS_NAME_PROPERTIES = "generate.jdbc.driver";

	/**
	 * 项目在硬盘上的基础路径
	 */
	String PROJECT_PATH_PROPERTIES = "generate.project.path";
	
	/**
	 * 模板位置
	 */
	String TEMPLATE_FILE_PATH_PROPERTIES = "generate.template.file.path";

	/**
	 * java文件路径
	 */
	String JAVA_PATH_PROPERTIES = "generate.java.path";
	
	/**
	 * 资源文件路径
	 */
	String RESOURCES_PATH_PROPERTIES = "generate.resources.path";

	/**
	 * 生成代码所在的基础包名称，可根据自己公司的项目修改（注意：这个配置修改之后需要手工修改src目录项目默认的包路径，使其保持一致，不然会找不到类）
	 */
	String BASE_PACKAGE_PROPERTIES = "generate.base.package";

	/**
	 * 生成的Model所在包
	 */
	String MODEL_PACKAGE_PROPERTIES = "generate.model.package";

	/**
	 * 生成的Mapper所在包
	 */
	String MAPPER_PACKAGE_PROPERTIES = "generate.mapper.package";

	/**
	 * 生成的Service所在包
	 */
	String SERVICE_PACKAGE_PROPERTIES = "generate.service.package";

	/**
	 * 生成的ServiceImpl所在包
	 */
	String SERVICE_IMPL_PACKAGE_PROPERTIES = "generate.service.impl.package";

	/**
	 * 生成的Controller所在包
	 */
	String CONTROLLER_PACKAGE_PROPERTIES = "generate.controller.package";

	/**
	 * Mapper插件基础接口的完全限定名
	 */
	String MAPPER_INTERFACE_REFERENCE_PROPERTIES = "generate.mapper.interface.reference";
	
	/**
	 * 默认作者
	 */
	String PROPERTIES_AUTHOR = "generate.author";
	
	/**
	 * 默认作者
	 */
	String PROPERTIES_CONTROLLER_INPUT = "generate.controller.input";
	
	/**
	 * 参数不能为空
	 */
	String PROPERTIES_NOT_EMPTY_CUE = "generate.params.is.not.empty";
	
	/**
	 * 参数不能小于指定值
	 */
	String PROPERTIES_MUST_BE_MORE_THAN_VALUE = "generate.params.must.be.more.than.value";
	/** ===================== 配置文件key end ===================== */
}
