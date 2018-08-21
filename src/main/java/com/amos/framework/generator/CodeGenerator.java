package com.amos.framework.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.google.common.base.CaseFormat;

import freemarker.template.TemplateExceptionHandler;

/**
 * ================================
 * 作者:amos lam
 * 时间:2018年7月2日下午5:56:17
 * 内容:
 * 1、代码生成器。
 * 2、根据数据表名称生成对应的Model、Mapper、Service、Controller简化开发。
 * ================================
 */
public class CodeGenerator {

	/**
	 * JDBC链接
	 */
	private Connection getConnection = null;

	/**
	 * 列明
	 */
	private List<String> colnames = new ArrayList<String>();
	/**
	 * 列明类型
	 */
	private List<String> colTypes = new ArrayList<String>();
	/**
	 * 长度
	 */
	private List<Integer> colSizes = new ArrayList<Integer>();
	/**
	 * 备注
	 */
	private List<String> remarks = new ArrayList<String>();
	/**
	 * 是否为空
	 */
	private List<String> isEmpty = new ArrayList<String>();

	/**
	 * 时间类型是否显示
	 */
	private Boolean DATE_SHOW = false;

	/**
	 * SQL类型是否显示
	 */
	private Boolean SQL_SHOW = false;

	/**
	 * 是否需要判断不能为空
	 */
	private Boolean EMPTY_SHOW = false;

	/**
	 * 数值最小限制
	 */
	private Boolean MIN_SHOW = false;

	/**
	 * 大数字类型是否显示
	 */
	private Boolean BIGDECIMAL_SHOW = false;

	private String DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());// @date

	public static void main(String[] args) {

		CodeGenerator cg = new CodeGenerator();
		cg.genCode("src/main/resources/application-generate-code.properties", "user",null);
	}

	/**
	 * 通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。 如输入表名称 "t_user_detail" 将生成
	 * TUserDetail、TUserDetailMapper、TUserDetailService ...
	 * @param path 配置文件路径
	 * @param tableName 数据库表名
	 * @param aliasName 数据库表别名
	 */
	public void genCode(String path, String tableName,String aliasName) {

		genCodeByCustomModelName(path, tableName, aliasName);

		initGenerator();
	}

	/**
	 * 初始化生成器全局变量
	 */
	private void initGenerator() {
		/**
		 * 列明
		 */
		colnames = new ArrayList<String>();
		/**
		 * 列明类型
		 */
		colTypes = new ArrayList<String>();
		/**
		 * 长度
		 */
		colSizes = new ArrayList<Integer>();
		/**
		 * 备注
		 */
		remarks = new ArrayList<String>();
		/**
		 * 是否为空
		 */
		isEmpty = new ArrayList<String>();

		/**
		 * 时间类型是否显示
		 */
		DATE_SHOW = false;

		/**
		 * SQL类型是否显示
		 */
		SQL_SHOW = false;

		/**
		 * 是否需要判断不能为空
		 */
		EMPTY_SHOW = false;

		/**
		 * 数值最小限制
		 */
		MIN_SHOW = false;

		/**
		 * 大数字类型是否显示
		 */
		BIGDECIMAL_SHOW = false;
	}
	
	/**
	 * 初始化导入包
	 */
	private void initImport(){

		/**
		 * 时间类型是否显示
		 */
		DATE_SHOW = false;

		/**
		 * SQL类型是否显示
		 */
		SQL_SHOW = false;

		/**
		 * 是否需要判断不能为空
		 */
		EMPTY_SHOW = false;

		/**
		 * 数值最小限制
		 */
		MIN_SHOW = false;
		
		/**
		 * 大数字类型是否显示
		 */
		BIGDECIMAL_SHOW = false;
	}

	/**
	 * 通过数据表名称，和自定义的 Model 名称生成代码 如输入表名称 "t_user_detail" 和自定义的 Model 名称 "User"
	 * 将生成 User、UserMapper、UserService ...
	 * 
	 * @param tableName
	 *            数据表名称
	 * @param modelName
	 *            自定义的 Model 名称
	 */
	private void genCodeByCustomModelName(String path, String tableName, String modelName) {

		genModelAndMapper(path, tableName, modelName);

		genService(path, tableName, modelName);

		Map<String, String> input = genControllerInput(path, tableName, modelName);

		genController(path, tableName, modelName,input);
	}

	/**
	 * 生成实体对象跟映射mapper
	 * 
	 * @param path
	 *            配置文件路径
	 * @param tableName
	 *            表名
	 * @param modelName
	 *            实体名
	 */
	private void genModelAndMapper(String path, String tableName, String modelName) {
		Context context = new Context(ModelType.FLAT);
		context.setId("mysqlTables");
		context.setTargetRuntime("MyBatis3Simple");
		// context.setTargetRuntime("MyBatis3");

		// beginningDelimiter和endingDelimiter：指明数据库的用于标记数据库对象名的符号，比如ORACLE就是双引号，MYSQL默认是`反引号；
		context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
		context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");
		// 生成的Java文件的编码
		context.addProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING, "UTF-8");
		// 格式化java代码
		context.addProperty(PropertyRegistry.CONTEXT_JAVA_FORMATTER,
				"org.mybatis.generator.api.dom.DefaultJavaFormatter");
		// 格式化XML代码
		context.addProperty(PropertyRegistry.CONTEXT_XML_FORMATTER,
				"org.mybatis.generator.api.dom.DefaultXmlFormatter");

		JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
		jdbcConnectionConfiguration.setConnectionURL(PropertiesConstants.getJdbcURL(path));
		jdbcConnectionConfiguration.setUserId(PropertiesConstants.getJdbcUsername(path));
		jdbcConnectionConfiguration.setPassword(PropertiesConstants.getJdbcPassword(path));
		jdbcConnectionConfiguration.setDriverClass(PropertiesConstants.getJdbcDiverClassName(path));
		context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

		// 为了防止生成的代码中有很多注释，比较难看，加入下面的配置控制
		CommentGeneratorConfiguration commentGenerator = new CommentGeneratorConfiguration();
		commentGenerator.addProperty("suppressAllComments", "true");
		commentGenerator.addProperty("suppressDate", "true");
		context.setCommentGeneratorConfiguration(commentGenerator);

		PluginConfiguration pluginConfiguration = new PluginConfiguration();
		pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
		pluginConfiguration.addProperty("mappers", PropertiesConstants.getMapperInterfaceReferenceFramework());
		context.addPluginConfiguration(pluginConfiguration);

		PluginConfiguration serializable = new PluginConfiguration();
		// 生成的pojo，将implements Serializable,为生成的Java模型类添加序列化接口
		serializable.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
		context.addPluginConfiguration(serializable);

		PluginConfiguration toString = new PluginConfiguration();
		// 为生成的Java模型创建一个toString方法
		toString.setConfigurationType("org.mybatis.generator.plugins.ToStringPlugin");
		context.addPluginConfiguration(toString);

		PluginConfiguration rowBounds = new PluginConfiguration();
		// 这个插件可以生成一个新的selectByExample方法，这个方法可以接受一个RowBounds参数，
		// 主要用来实现分页（当然，我们后面会生成我们自己的分页查询函数），
		// 这个插件只针对MyBatis3/MyBatis3Simple有效
		// Mapper中会加入一个新的方法：
		// selectByExampleWithRowbounds(XxxExample example, RowBounds rowBounds)
		rowBounds.setConfigurationType("org.mybatis.generator.plugins.RowBoundsPlugin");
		context.addPluginConfiguration(rowBounds);

		String targetProject = PropertiesConstants.getProjectPath(path) + PropertiesConstants.getJavaPath(path);
		JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
		javaModelGeneratorConfiguration.setTargetProject(targetProject);
		javaModelGeneratorConfiguration
				.setTargetPackage(PropertiesConstants.getModelPackage(path) + GeneratorConstants.AUTO_CONSTANTS);
		context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

		SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
		sqlMapGeneratorConfiguration.setTargetProject(
				PropertiesConstants.getProjectPath(path) + PropertiesConstants.getResourcesPath(path));
		sqlMapGeneratorConfiguration.setTargetPackage("mapper");
		context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

		JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
		javaClientGeneratorConfiguration.setTargetProject(targetProject);
		javaClientGeneratorConfiguration
				.setTargetPackage(PropertiesConstants.getMapperPackage(path) + GeneratorConstants.AUTO_CONSTANTS);
		javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
		context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

		TableConfiguration tableConfiguration = new TableConfiguration(context);
		tableConfiguration.setTableName(tableName);
		if (StringUtils.isNotEmpty(modelName))
			tableConfiguration.setDomainObjectName(modelName);
		tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
		context.addTableConfiguration(tableConfiguration);

		List<String> warnings;
		MyBatisGenerator generator;
		try {
			Configuration config = new Configuration();
			config.addContext(context);
			config.validate();

			boolean overwrite = true;
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			warnings = new ArrayList<String>();
			generator = new MyBatisGenerator(config, callback, warnings);
			generator.generate(null);
		} catch (Exception e) {

			throw new RuntimeException("生成Model和Mapper失败", e);
		}

		if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {

			throw new RuntimeException("生成Model和Mapper失败：" + warnings);
		}
		if (StringUtils.isEmpty(modelName))
			modelName = tableNameConvertUpperCamel(tableName);
		System.out.println(modelName + ".java 生成成功");
		System.out.println(modelName + "Mapper.java 生成成功");
		System.out.println(modelName + "Mapper.xml 生成成功");
	}

	/**
	 * 生成业务逻辑接口及实现
	 * 
	 * @param path
	 *            配置文件路径
	 * @param tableName
	 *            表名
	 * @param modelName
	 *            实体名
	 */
	private void genService(String path, String tableName, String modelName) {
		try {

			freemarker.template.Configuration cfg = getConfiguration(path);

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("date", DATE);
			data.put("author", PropertiesConstants.getAuthor(path));
			String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName)
					: modelName;
			data.put("modelNameUpperCamel", modelNameUpperCamel);
			data.put("modelAutoNameUpperCamel", modelNameUpperCamel + GeneratorConstants.SIGN_AUTO);
			data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
			data.put("basePackage", PropertiesConstants.getBasePackage(path));

			String PACKAGE_PATH_SERVICE = packageConvertPath(
					PropertiesConstants.getServicePackage(path) + GeneratorConstants.AUTO_CONSTANTS);// 生成的Service存放路径
			String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(PropertiesConstants.getServiceImplPackage(path));// 生成的Service实现存放路径

			String filePath = PropertiesConstants.getProjectPath(path) + PropertiesConstants.getJavaPath(path);

			File file = new File(filePath + PACKAGE_PATH_SERVICE + modelNameUpperCamel + GeneratorConstants.SIGN_AUTO
					+ "Service.java");
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			cfg.getTemplate("service.ftl").process(data, new FileWriter(file));

			System.out.println(modelNameUpperCamel + "Service.java 生成成功");

			File file1 = new File(filePath + PACKAGE_PATH_SERVICE_IMPL + modelNameUpperCamel
					+ GeneratorConstants.SIGN_AUTO + "ServiceImpl.java");
			if (!file1.getParentFile().exists()) {
				file1.getParentFile().mkdirs();
			}
			cfg.getTemplate("service-impl.ftl").process(data, new FileWriter(file1));

			System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
		} catch (Exception e) {

			throw new RuntimeException("生成Service失败", e);
		}
	}

	/**
	 * 生成控制成controller
	 * 
	 * @param path
	 *            配置文件路径
	 * @param tableName
	 *            表名
	 * @param modelName
	 *            实体名
	 */
	private void genController(String path, String tableName, String modelName,Map<String, String> input) {

		try {

			freemarker.template.Configuration cfg = getConfiguration(path);

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("date", DATE);
			data.put("author", PropertiesConstants.getAuthor(path));
			String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName)
					: modelName;
			data.put("baseRequestMapping",
					modelNameConvertMappingPath(modelNameUpperCamel + GeneratorConstants.SIGN_AUTO));
			data.put("modelNameUpperCamel", modelNameUpperCamel);
			data.put("modelAutoNameUpperCamel", modelNameUpperCamel + GeneratorConstants.SIGN_AUTO);
			data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
			data.put("basePackage", PropertiesConstants.getBasePackage(path));
			
			data.put(GeneratorConstants.CONTROLLER_SAVE_INPUT, input.get(GeneratorConstants.CONTROLLER_SAVE_INPUT));
			data.put(GeneratorConstants.CONTROLLER_UPDATE_INPUT, input.get(GeneratorConstants.CONTROLLER_UPDATE_INPUT));
			data.put(GeneratorConstants.CONTROLLER_DELETE_INPUT, input.get(GeneratorConstants.CONTROLLER_DELETE_INPUT));
			data.put(GeneratorConstants.CONTROLLER_GET_BY_ID_INPUT, input.get(GeneratorConstants.CONTROLLER_GET_BY_ID_INPUT));
			data.put(GeneratorConstants.CONTROLLER_FIND_BY_MODEL_INPUT, input.get(GeneratorConstants.CONTROLLER_FIND_BY_MODEL_INPUT));
			
			String INPUT_CLASS = "Class";
			
			data.put(GeneratorConstants.CONTROLLER_SAVE_INPUT + INPUT_CLASS, this.subStringClassName(input.get(GeneratorConstants.CONTROLLER_SAVE_INPUT)));
			data.put(GeneratorConstants.CONTROLLER_UPDATE_INPUT + INPUT_CLASS, this.subStringClassName(input.get(GeneratorConstants.CONTROLLER_UPDATE_INPUT)));
			data.put(GeneratorConstants.CONTROLLER_DELETE_INPUT + INPUT_CLASS, this.subStringClassName(input.get(GeneratorConstants.CONTROLLER_DELETE_INPUT)));
			data.put(GeneratorConstants.CONTROLLER_GET_BY_ID_INPUT + INPUT_CLASS, this.subStringClassName(input.get(GeneratorConstants.CONTROLLER_GET_BY_ID_INPUT)));
			data.put(GeneratorConstants.CONTROLLER_FIND_BY_MODEL_INPUT + INPUT_CLASS, this.subStringClassName(input.get(GeneratorConstants.CONTROLLER_FIND_BY_MODEL_INPUT)));

			// 生成的Controller存放路径
			String PACKAGE_PATH_CONTROLLER = packageConvertPath(
					PropertiesConstants.getControllerPackage(path) + GeneratorConstants.AUTO_CONSTANTS);
			String filePath = PropertiesConstants.getProjectPath(path) + PropertiesConstants.getJavaPath(path);

			File file = new File(filePath + PACKAGE_PATH_CONTROLLER + modelNameUpperCamel + GeneratorConstants.SIGN_AUTO
					+ "Controller.java");
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			// cfg.getTemplate("controller-restful.ftl").process(data, new
			// FileWriter(file));
			cfg.getTemplate("controller.ftl").process(data, new FileWriter(file));

			System.out.println(modelNameUpperCamel + "Controller.java 生成成功");
		} catch (Exception e) {

			throw new RuntimeException("生成Controller失败", e);
		}

	}
	
	/**
	 * 截取类名
	 * @param classPath 类路径
	 * @return
	 */
	private String subStringClassName (String classPath){
		
		int firstPoint = classPath.lastIndexOf(".");
		classPath = classPath.substring((firstPoint + 1),classPath.length());
		
		return classPath;
	}
	
	/**
	 * 解析表中内容
	 * 
	 * @param path
	 *            配置文件路径
	 * @param tableName
	 *            表名
	 */
	private void parseTableAttrs(String path, String tableName) {

		try {

			getConnections(path);

			DatabaseMetaData dbmd = getConnection.getMetaData();

			ResultSet rset = dbmd.getColumns(null, "%", tableName, "%");

			while (rset.next()) {

				String columnName = rset.getString("COLUMN_NAME").toLowerCase();
				colnames.add(camelName(columnName));
				colTypes.add(rset.getString("TYPE_NAME"));
				colSizes.add(Integer.valueOf(rset.getString("COLUMN_SIZE")));
				remarks.add(rset.getString("REMARKS"));
				isEmpty.add(rset.getString("NULLABLE"));

			}
		} catch (SQLException e) {

			throw new RuntimeException("SQLException异常", e);
		}
	}

	/**
	 * 排除字段
	 */
	private List<String> excludeField;

	/**
	 * 持有字段（以持有为主，排除为辅）
	 */
	private List<String> holdField;

	/**
	 * 排除注解
	 */
	private List<String> excludeAnnotation;

	/**
	 * 持有注解（以持有为主，排除为辅）
	 */
	private List<String> holdAnnotation;

	/**
	 * save接口需要排除的字段
	 * 
	 * @param excludeField
	 * @return
	 */
	protected List<String> saveExcludeField(List<String> saveExcludeField) {

		saveExcludeField.add("id");
		saveExcludeField.add("isDisable");
		saveExcludeField.add("isDeleted");
		saveExcludeField.add("createdTime");
		saveExcludeField.add("modifedTime");
		return saveExcludeField;
	}
	
	/**
	 * save接口需要持有的字段
	 * 
	 * @param excludeField
	 * @return
	 */
	protected List<String> saveHoldField(List<String> saveHoldField) {

		return saveHoldField;
	}
	
	/**
	 * save接口需要排除的字段注解
	 * 
	 * @param excludeField
	 * @return
	 */
	protected List<String> saveExcludeAnnotation(List<String> saveExcludeAnnotation) {

		return saveExcludeAnnotation;
	}

	/**
	 * save接口需要持有的字段注解
	 * 
	 * @param excludeField
	 * @return
	 */
	protected List<String> saveHoldAnnotation(List<String> saveHoldAnnotation) {

		return saveHoldAnnotation;
	}

	/**
	 * update接口需要排除的字段
	 * 
	 * @return
	 */
	protected List<String> updateExcludeField(List<String> updateExcludeField) {

		updateExcludeField.add("createdTime");
		updateExcludeField.add("modifedTime");
		return updateExcludeField;
	}
	
	/**
	 * update接口需要排除的字段
	 * 
	 * @return
	 */
	protected List<String> updateHoldField(List<String> updateHoldField) {

		return updateHoldField;
	}

	/**
	 * update接口需要持有的字段注解
	 * 
	 * @return
	 */
	protected List<String> updateHoldAnnotation(List<String> updateHoldAnnotation) {

		updateHoldAnnotation.add("id");
		return updateHoldAnnotation;
	}
	
	/**
	 * update接口需要排除的字段注解
	 * 
	 * @return
	 */
	protected List<String> updateExcludeAnnotation(List<String> updateExcludeAnnotation) {

		return updateExcludeAnnotation;
	}

	/**
	 * delete接口需要排除的字段
	 * 
	 * @return
	 */
	protected List<String> deleteExcludeField(List<String> deleteExcludeField) {

		return deleteExcludeField;
	}
	
	/**
	 * delete接口需要排除的字段
	 * 
	 * @return
	 */
	protected List<String> deleteHoldField(List<String> deleteHoldField) {

		deleteHoldField.add("id");
		return deleteHoldField;
	}

	/**
	 * delete接口需要持有的字段注解
	 * 
	 * @return
	 */
	protected List<String> deleteHoldAnnotation(List<String> deleteHoldAnnotation) {

		deleteHoldAnnotation.add("id");
		return deleteHoldAnnotation;
	}
	
	/**
	 * delete接口需要排除的字段注解
	 * 
	 * @return
	 */
	protected List<String> deleteExcludeAnnotation(List<String> deleteExcludeAnnotation) {

		return deleteExcludeAnnotation;
	}

	/**
	 * getById接口需要排除的字段
	 * 
	 * @return
	 */
	protected List<String> getByIdExcludeField(List<String> getByIdExcludeField) {

		return getByIdExcludeField;
	}
	
	/**
	 * getById接口需要排除的字段
	 * 
	 * @return
	 */
	protected List<String> getByIdHoldField(List<String> getByIdHoldField) {

		return getByIdHoldField;
	}

	/**
	 * getById接口需要持有的字段注解
	 * 
	 * @return
	 */
	protected List<String> getByIdHoldAnnotation(List<String> getByIdHoldAnnotation) {

		return getByIdHoldAnnotation;
	}
	
	/**
	 * getById接口需要排除的字段注解
	 * 
	 * @return
	 */
	protected List<String> getByIdExcludeAnnotation(List<String> getByIdExcludeAnnotation) {

		return getByIdExcludeAnnotation;
	}

	/**
	 * getById接口需要排除的字段
	 * 
	 * @return
	 */
	protected List<String> findByModelExcludeField(List<String> findByModelExcludeField) {

		return findByModelExcludeField;
	}
	
	/**
	 * getById接口需要排除的字段
	 * 
	 * @return
	 */
	protected List<String> findByModelHoldField(List<String> findByModelHoldField) {

		return findByModelHoldField;
	}

	/**
	 * getById接口需要持有的字段注解
	 * 
	 * @return
	 */
	protected List<String> findByModelHoldAnnotation(List<String> findByModelHoldAnnotation) {

		return findByModelHoldAnnotation;
	}
	
	/**
	 * getById接口需要排除的字段注解
	 * 
	 * @return
	 */
	protected List<String> findByModelExcludeAnnotation(List<String> findByModelExcludeAnnotation) {

		return findByModelExcludeAnnotation;
	}
	
	private Map<String, String> genControllerInput(String path, String tableName, String modelName) {

		Map<String, String> packagePath = new HashMap<String, String>();
		
		String upperInputPackage = tableNameConvertUpperCamel(GeneratorConstants.CONTROLLER_INPUT_PACKAGE);

		String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;

		parseTableAttrs(path, tableName);

		/**
		 * 字段内容
		 */
		String content = "";

		// 保存接口入参
		String save = GeneratorConstants.CONTROLLER_SAVE + modelNameUpperCamel + upperInputPackage;

		excludeField = new ArrayList<String>();
		excludeField = saveExcludeField(excludeField);
		
		holdField = new ArrayList<String>();
		holdField = saveHoldField(holdField);
		
		excludeAnnotation = new ArrayList<String>();
		excludeAnnotation = saveExcludeAnnotation(excludeAnnotation);
		
		holdAnnotation = new ArrayList<String>();
		holdAnnotation = saveHoldAnnotation(holdAnnotation);
		
		content = genBody(path, tableName, save,modelName);
		String saveInput = outPutControllerInput(path, tableName, save, content,modelName);
		packagePath.put(GeneratorConstants.CONTROLLER_SAVE_INPUT, saveInput);
		initImport();

		// 修改接口入参
		String update = GeneratorConstants.CONTROLLER_UPDATE + modelNameUpperCamel + upperInputPackage;

		excludeField = new ArrayList<String>();
		excludeField = updateExcludeField(excludeField);
		
		holdField = new ArrayList<String>();
		holdField = updateHoldField(holdField);
		
		excludeAnnotation = new ArrayList<String>();
		excludeAnnotation = updateExcludeAnnotation(excludeAnnotation);
		
		holdAnnotation = new ArrayList<String>();
		holdAnnotation = updateHoldAnnotation(holdAnnotation);
		
		content = genBody(path, tableName, update,modelName);
		String updateInput = outPutControllerInput(path, tableName, update, content,modelName);
		packagePath.put(GeneratorConstants.CONTROLLER_UPDATE_INPUT, updateInput);
		initImport();
		
		// 删除接口入参
		String delete = GeneratorConstants.CONTROLLER_DELETE + modelNameUpperCamel + upperInputPackage;

		excludeField = new ArrayList<String>();
		excludeField = deleteExcludeField(excludeField);
		
		holdField = new ArrayList<String>();
		holdField = deleteHoldField(holdField);
		
		excludeAnnotation = new ArrayList<String>();
		excludeAnnotation = deleteExcludeAnnotation(excludeAnnotation);
		
		holdAnnotation = new ArrayList<String>();
		holdAnnotation = deleteHoldAnnotation(holdAnnotation);
		
		content = genBody(path, tableName, delete,modelName);
		String deleteInput = outPutControllerInput(path, tableName, delete, content,modelName);
		packagePath.put(GeneratorConstants.CONTROLLER_DELETE_INPUT, deleteInput);
		initImport();
		
		// 根据ID查询接口入参
		String getById = GeneratorConstants.CONTROLLER_GET_BY_ID + modelNameUpperCamel + upperInputPackage;

		excludeField = new ArrayList<String>();
		excludeField = getByIdExcludeField(excludeField);
		
		holdField = new ArrayList<String>();
		holdField = getByIdHoldField(holdField);
		
		excludeAnnotation = new ArrayList<String>();
		excludeAnnotation = getByIdExcludeAnnotation(excludeAnnotation);
		
		holdAnnotation = new ArrayList<String>();
		holdAnnotation = getByIdHoldAnnotation(holdAnnotation);
		
		content = genBody(path, tableName, getById,modelName);
		String getByIdInput = outPutControllerInput(path, tableName, getById, content,modelName);
		packagePath.put(GeneratorConstants.CONTROLLER_GET_BY_ID_INPUT, getByIdInput);
		initImport();
		
		// 根据对象查询接口入参
		String findByModel = GeneratorConstants.CONTROLLER_FIND_BY_MODEL + modelNameUpperCamel + upperInputPackage;

		excludeField = new ArrayList<String>();
		excludeField = findByModelExcludeField(excludeField);
		
		holdField = new ArrayList<String>();
		holdField = findByModelHoldField(holdField);
		
		excludeAnnotation = new ArrayList<String>();
		excludeAnnotation = findByModelExcludeAnnotation(excludeAnnotation);
		
		holdAnnotation = new ArrayList<String>();
		holdAnnotation = findByModelHoldAnnotation(holdAnnotation);
		
		content = genBody(path, tableName, findByModel,modelName);
		String findByModelInput = outPutControllerInput(path, tableName, findByModel, content,modelName);
		packagePath.put(GeneratorConstants.CONTROLLER_FIND_BY_MODEL_INPUT, findByModelInput);
		initImport();
		
		return packagePath;
	}

	/**
	 * * 生成控制成controller 入参对象
	 * 
	 * @param path
	 *            配置文件路径
	 * @param tableName
	 *            表名
	 * @param type
	 *            类型
	 */
	private String outPutControllerInput(String path, String tableName, String type, String content,String modelName) {

		String packagePath = "";
		String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertLowerCamel(tableName) : modelName;
		try {

			String PACKAGE_PATH_CONTROLLER_INPUT = packageConvertPath(PropertiesConstants.getControllerInput(path) + "." + modelNameUpperCamel + ".");
			String filePath = PropertiesConstants.getProjectPath(path) + PropertiesConstants.getJavaPath(path);

			packagePath = PACKAGE_PATH_CONTROLLER_INPUT + type;
			
			String outputPath = filePath + packagePath + ".java";

			File file = new File(outputPath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			FileWriter fw = new FileWriter(outputPath);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(content);
			pw.flush();
			pw.close();
			
			System.out.println(type + ".java 生成成功");
		} catch (IOException e) {

			throw new RuntimeException("输出Controller 校验类异常", e);
		}
		
		return (PropertiesConstants.getControllerInput(path) + "." + modelNameUpperCamel + "." + type);
	}

	/**
	 * 生成实体类主体代码
	 * 
	 * @return
	 */
	private String genBody(String path, String tableName, String className,String modelName) {

		StringBuffer body = new StringBuffer();

		/**
		 * 生成所有属性
		 */
		processAllAttrs(body, path);

		/**
		 * 生成所有get set
		 */
		processAllMethod(body, path);

		StringBuffer sb = new StringBuffer();

		String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertLowerCamel(tableName) : modelName;
		String packageName = PropertiesConstants.getControllerInput(path) + "." + modelNameUpperCamel;

		sb.append("package " + packageName + ";").append("\r\n");
		sb.append("\r\n");
		sb.append("import java.io.Serializable;").append("\r\n");

		// 判断是否导入工具包
		if (DATE_SHOW) {
			sb.append("import java.util.Date;\r\n");
		}
		if (SQL_SHOW) {
			sb.append("import java.sql.*;\r\n");
		}
		if(BIGDECIMAL_SHOW){
			sb.append("import java.math.BigDecimal;").append("\r\n");
		}
		if (MIN_SHOW) {
			sb.append("import javax.validation.constraints.Min;").append("\r\n");
		}
		if (EMPTY_SHOW) {
			sb.append("import org.hibernate.validator.constraints.NotEmpty;").append("\r\n");
		}
		sb.append("\r\n");

		// 注释部分
		sb.append("/**").append("\r\n");
		sb.append(" * ").append("===============================").append("\r\n");
		sb.append(" * ").append("作者：" + PropertiesConstants.getAuthor(path)).append("\r\n");
		sb.append(" * ").append("时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\r\n");
		sb.append(" * ").append("内容：" + tableNameConvertUpperCamel(tableName) + " Object").append("\r\n");
		sb.append(" * ").append("===============================").append("\r\n");

		sb.append("*/").append("\r\n");
		// 实体部分
		sb.append("public class " + className + " implements Serializable{").append("\r\n");

		sb.append("\r\n");
		sb.append("\t").append("private static final long serialVersionUID = 1L;").append("\r\n");
		sb.append("\r\n");

		sb.append(body.toString());

		sb.append("}").append("\r\n");

		return sb.toString();
	}

	/**
	 * 功能：生成所有属性
	 * 
	 * @param sb
	 */
	private void processAllAttrs(StringBuffer sb, String path) {

		for (int i = 0; i < colnames.size(); i++) {

			if (holdField.isEmpty()) {

				if (excludeField.contains(colnames.get(i))) {
					// 如果持有字段为空，并且该字段属于排除字段，则排除掉
					continue;
				}
			} else {

				if (!holdField.contains(colnames.get(i))) {
					// 如果持有字段不为空，并且该字段不存在于持有字段，则排除
					continue;
				}
			}

			sb.append("\t").append("/**").append("\r\n");
			sb.append("\t").append(" *").append(" " + remarks.get(i)).append("\r\n");
			sb.append("\t").append(" */").append("\r\n");

			if (colTypes.get(i).equalsIgnoreCase("datetime") || colTypes.get(i).equalsIgnoreCase("timestamp") 
					|| colTypes.get(i).equalsIgnoreCase("date")) {
				DATE_SHOW = true;
			}
			if (colTypes.get(i).equalsIgnoreCase("image") || colTypes.get(i).equalsIgnoreCase("text")) {
				SQL_SHOW = true;
			}

			if (holdAnnotation.isEmpty()) {

				if (!excludeAnnotation.contains(colnames.get(i))) {

					this.appendAnnotation(path, sb, i);
				}
			} else {

				if (holdAnnotation.contains(colnames.get(i))) {

					this.appendAnnotation(path, sb, i);
				}
			}
			String dataType = "";
			if(GeneratorConstants.JDBC_DRIVER_CLASS_NAME.equals(PropertiesConstants.getJdbcDiverClassName(path))){
				
				dataType = mysqlType2JavaType(colTypes.get(i));
			}else{
				
				dataType = oracleType2JavaType(colTypes.get(i), colSizes.get(i).toString());
			}
			sb.append("\t").append("private " + dataType + " " + colnames.get(i) + ";")
					.append("\r\n");
			sb.append("\r\n");
		}
	}

	/**
	 * 给字段追加注解
	 * 
	 * @param path
	 *            配置文件路径
	 * @param sb
	 *            拼接字符串
	 * @param i
	 *            当前字段下标
	 */
	private void appendAnnotation(String path, StringBuffer sb, int i) {

		Map<String, Boolean> maps = new HashMap<String, Boolean>();
		if (isEmpty.get(i).equals(GeneratorConstants.NOT_EMPTY)) {

			EMPTY_SHOW = true;
			MIN_SHOW = true;
			maps.put(GeneratorConstants.EMPTY_KEY, true);
			maps.put(GeneratorConstants.MIN_KEY, true);
		}

		if (null != maps.get(GeneratorConstants.MIN_KEY) && maps.get(GeneratorConstants.MIN_KEY)) {

			sb.append("\t").append("@Min(value = " + colSizes.get(i) + ",message = \""
					+ PropertiesConstants.getMustBeMoreThanValue(path) + "\")").append("\r\n");
		}
		if (null != maps.get(GeneratorConstants.EMPTY_KEY) && maps.get(GeneratorConstants.EMPTY_KEY)) {

			sb.append("\t").append("@NotEmpty(message = \"" + PropertiesConstants.getNotEmptyCue(path) + "\")")
					.append("\r\n");
		}
	}

	/**
	 * 生成所有Get Set方法
	 * 
	 * @param sb
	 */
	private void processAllMethod(StringBuffer sb,String path) {

		for (int i = 0; i < colnames.size(); i++) {

			if(holdField.isEmpty()){

				if (excludeField.contains(colnames.get(i))) {

					// 如果持有字段为空，并且该字段属于排除字段，则排除掉
					continue;
				}
			}else{
				
				if (!holdField.contains(colnames.get(i))) {

					// 如果持有字段不为空，并且该字段不存在于持有字段，则排除
					continue;
				}
			}
			
			String dataType = "";
			if(GeneratorConstants.JDBC_DRIVER_CLASS_NAME.equals(PropertiesConstants.getJdbcDiverClassName(path))){
				
				dataType = mysqlType2JavaType(colTypes.get(i));
			}else{
				
				dataType = oracleType2JavaType(colTypes.get(i), colSizes.get(i).toString());
			}
			sb.append("\t").append("public void set" + tableNameConvertUpperCamel(colnames.get(i)) + "").append("(")
			.append(dataType).append(" " + colnames.get(i)).append(")").append("{")
			.append("\r\n");
			sb.append("\t").append("\t").append("this.").append("" + colnames.get(i) + " = " + colnames.get(i) + ";")
					.append("\r\n");
			sb.append("\t").append("}").append("\r\n");
			sb.append("\r\n");
		
			sb.append("\t").append("public " + dataType).append(" get")
					.append(tableNameConvertUpperCamel(colnames.get(i))).append("()").append("{").append("\r\n");
			sb.append("\t").append("\t").append("return" + " ").append(colnames.get(i) + ";").append("\r\n");
			sb.append("\t").append("}").append("\r\n");
			sb.append("\r\n");
		}
	}
	
	/**
	 * 获得列的数据类型
	 * 
	 * @param sqlType
	 * @return
	 */
	private String oracleType2JavaType(String sqlType,String... attrs) {

		if (sqlType.equalsIgnoreCase("NUMBER")) {
			Integer length = Integer.valueOf(attrs[0]);
			if(length >= 18){

				BIGDECIMAL_SHOW = true;
				return "BigDecimal";
			}else if(10 <= length && length < 18){
				
				return "Long";
			}else {
				
				return "Integer";
			}
		} else if (sqlType.equalsIgnoreCase("RAW") || sqlType.equalsIgnoreCase("VARCHAR2") 
				|| sqlType.equalsIgnoreCase("BLOB") || sqlType.equalsIgnoreCase("CHAR")
				|| sqlType.equalsIgnoreCase("CLOB")) {
			
			return "String";
		} else if (sqlType.equalsIgnoreCase("DATE")) {
			
			return "Date";
		} else if (sqlType.equalsIgnoreCase("FLOAT")) {
			
			Integer length = Integer.valueOf(attrs[0]);
			if(length >= 24){
				
				return "BigDecimal";
			}else if(length > 0){

				return "Double";
			}else{
				
				return "FLOAT";
			}
		}

		return null;
	}

	/**
	 * 获得列的数据类型
	 * 
	 * @param sqlType
	 * @return
	 */
	private String mysqlType2JavaType(String sqlType) {

		if (sqlType.equalsIgnoreCase("bit")) {
			return "Boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "Byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "short";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "int";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "Long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "float";
		} else if (sqlType.equalsIgnoreCase("real")) {
			return "double";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("money") || sqlType.equalsIgnoreCase("smallmoney")) {
			return "BigDecimal";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("text")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("timestamp")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("image")) {
			return "Blod";
		}

		return null;
	}

	/**
	 * 获取数据库连接
	 * 
	 * @param path
	 *            配置文件路径
	 * @return
	 */
	private Connection getConnections(String path) {
		try {

			if (null == getConnection) {

				String DRIVER = PropertiesConstants.getJdbcDiverClassName(path);
				String URL = PropertiesConstants.getJdbcURL(path);
				String NAME = PropertiesConstants.getJdbcUsername(path);
				String PASS = PropertiesConstants.getJdbcPassword(path);

				Properties props = new Properties();
				props.put("remarksReporting", "true");
				props.put("user", NAME);
				props.put("password", PASS);
				
				Class.forName(DRIVER);
				getConnection = DriverManager.getConnection(URL, NAME, PASS);
			}
		} catch (Exception e) {

			throw new RuntimeException("获取jdbc出现异常", e);
		}
		return getConnection;
	}

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HELLO_WORLD->HelloWorld
	 * 
	 * @param name
	 *            转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	private String camelName(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将首字母小写
			return name.substring(0, 1).toLowerCase() + name.substring(1);
		}
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 处理真正的驼峰片段
			if (result.length() == 0) {
				// 第一个驼峰片段，全部字母都小写
				result.append(camel.toLowerCase());
			} else {
				// 其他的驼峰片段，首字母大写
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

	private freemarker.template.Configuration getConfiguration(String path) throws IOException {
		freemarker.template.Configuration cfg = new freemarker.template.Configuration(
				freemarker.template.Configuration.VERSION_2_3_23);
		String url = CodeGenerator.class.getResource(PropertiesConstants.getTemplateFilePath(path)).getPath();
		File file = new File(url);
		cfg.setDirectoryForTemplateLoading(file);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		return cfg;
	}

	private String tableNameConvertLowerCamel(String tableName) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
	}

	private String tableNameConvertUpperCamel(String tableName) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());

	}

	private String tableNameConvertMappingPath(String tableName) {
		tableName = tableName.toLowerCase();// 兼容使用大写的表名
		return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
	}

	private String modelNameConvertMappingPath(String modelName) {
		String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
		return tableNameConvertMappingPath(tableName);
	}

	private String packageConvertPath(String packageName) {
		return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
	}

}
