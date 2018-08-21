package com.amos.framework.generator;

import org.apache.commons.lang3.StringUtils;

/**
 * ===============================
 * 作者：amos lam
 * 时间：2018年8月17日下午2:19:49
 * 内容：解析配置文件
 * ===============================
 */
public class PropertiesConstants {

	public static String getJdbcURL(String path) {
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.JDBC_URL_PROPERTIES);
		if(StringUtils.isEmpty(value)){
			
			return GeneratorConstants.JDBC_URL;
		}else{
			
			return value;
		}
	}

	public static String getJdbcUsername(String path) {
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.JDBC_USERNAME_PROPERTIES);
		if(StringUtils.isEmpty(value)){
			
			return GeneratorConstants.JDBC_USERNAME;
		}else{
			
			return value;
		}
	}

	public static String getJdbcPassword(String path) {
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.JDBC_PASSWORD_PROPERTIES);
		if(StringUtils.isEmpty(value)){
			
			return GeneratorConstants.JDBC_PASSWORD;
		}else{
			
			return value;
		}
	}

	public static String getJdbcDiverClassName(String path) {
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.JDBC_DRIVER_CLASS_NAME_PROPERTIES);
		if(StringUtils.isEmpty(value)){
			
			return GeneratorConstants.JDBC_DRIVER_CLASS_NAME;
		}else{
			
			return value;
		}
	}

	public static String getProjectPath(String path) {
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.PROJECT_PATH_PROPERTIES);
		if(StringUtils.isEmpty(value)){
			
			return GeneratorConstants.PROJECT_PATH;
		}else{
			
			return value;
		}
	}

	public static String getTemplateFilePath(String path) {
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.TEMPLATE_FILE_PATH_PROPERTIES);
		if(StringUtils.isEmpty(value)){
			
			return GeneratorConstants.TEMPLATE_FILE_PATH;
		}else{
			
			return value;
		}
	}

	public static String getJavaPath(String path) {
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.JAVA_PATH_PROPERTIES);
		if(StringUtils.isEmpty(value)){
			
			return GeneratorConstants.JAVA_PATH;
		}else{
			
			return value;
		}
	}

	public static String getResourcesPath(String path) {
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.RESOURCES_PATH_PROPERTIES);
		if(StringUtils.isEmpty(value)){
			
			return GeneratorConstants.RESOURCES_PATH;
		}else{
			
			return value;
		}
	}

	public static String getBasePackage(String path) {
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.BASE_PACKAGE_PROPERTIES);
		if(StringUtils.isEmpty(value)){
			
			return GeneratorConstants.BASE_PACKAGE;
		}else{
			
			return value;
		}
	}

	public static String getModelPackage(String path) {
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.MODEL_PACKAGE_PROPERTIES);
		if(StringUtils.isEmpty(value)){
			
			return getBasePackage(path) + GeneratorConstants.MODEL_PACKAGE;
		}else{
			
			return getBasePackage(path) + value;
		}
	}

	public static String getMapperPackage(String path) {
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.MAPPER_PACKAGE_PROPERTIES);
		if(StringUtils.isEmpty(value)){
			
			return getBasePackage(path) + GeneratorConstants.MAPPER_PACKAGE;
		}else{
			
			return getBasePackage(path) + value;
		}
	}

	public static String getServicePackage(String path) {
		
		return getBasePackage(path) + getServicePackageNotSplit(path);
	}
	
	public static String getServicePackageNotSplit(String path) {
		String value = ParseProperties.parseProperties(path, GeneratorConstants.SERVICE_PACKAGE_PROPERTIES);
		if(StringUtils.isEmpty(value)){
			
			return GeneratorConstants.SERVICE_PACKAGE;
		}else{
			
			return value;
		}
	}

	public static String getServiceImplPackage(String path) {
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.SERVICE_IMPL_PACKAGE_PROPERTIES);
		if(StringUtils.isEmpty(value)){
			
			return getBasePackage(path) + getServicePackageNotSplit(path) + GeneratorConstants.AUTO_CONSTANTS + GeneratorConstants.SERVICE_IMPL_PACKAGE;
		}else{
			
			return getBasePackage(path) + getServicePackageNotSplit(path) + value;
		}
	}

	public static String getControllerPackage(String path) {
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.CONTROLLER_PACKAGE_PROPERTIES);
		if(StringUtils.isEmpty(value)){
			
			return getBasePackage(path) + GeneratorConstants.CONTROLLER_PACKAGE;
		}else{
			
			return getBasePackage(path) + value;
		}
	}
	
	public static String getMapperInterfaceReferenceFramework() {
		
		return GeneratorConstants.BASIC_MAPPER_PUBLIC_PACKAGE;
	}

	public static String getMapperInterfaceReference(String path) {
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.MAPPER_INTERFACE_REFERENCE_PROPERTIES);
		if(StringUtils.isEmpty(value)){
			
			return getBasePackage(path) + GeneratorConstants.MAPPER_INTERFACE_REFERENCE;
		}else{
			
			return getBasePackage(path) + value;
		}
	}
	
	public static String getAuthor(String path){
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.PROPERTIES_AUTHOR);
		if(StringUtils.isEmpty(value)){
			
			return GeneratorConstants.AUTHOR;
		}else{
			
			return value;
		}
	}
	
	public static String getControllerInput(String path){
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.PROPERTIES_CONTROLLER_INPUT);
		if(StringUtils.isEmpty(value)){
			
			return getBasePackage(path) + "." + GeneratorConstants.CONTROLLER_INPUT_PACKAGE;
		}else{
			
			return getBasePackage(path) + "." + value;
		}
	}
	
	/**
	 * 获取配置文件不能为空校验key
	 * 如果配置文件没配，取缺省值
	 * @param path
	 * @return
	 */
	public static String getNotEmptyCue(String path){
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.PROPERTIES_NOT_EMPTY_CUE);
		if(StringUtils.isEmpty(value)){
			
			return GeneratorConstants.NOT_EMPTY_CUE;
		}else{
			
			return value;
		}
	}
	
	/**
	 * 获取配置文件最小值校验key
	 * 如果配置文件没配，取缺省值
	 * @param path
	 * @return
	 */
	public static String getMustBeMoreThanValue(String path){
		
		String value = ParseProperties.parseProperties(path, GeneratorConstants.PROPERTIES_MUST_BE_MORE_THAN_VALUE);
		if(StringUtils.isEmpty(value)){
			
			return GeneratorConstants.MUST_BE_MORE_THAN_VALUE;
		}else{
			
			return value;
		}
	}

}
