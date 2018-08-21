package com.amos.framework.generator;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ===============================
 * 作者：amos lam
 * 时间：2018年8月17日下午2:19:31
 * 内容：解析配置文件
 * ===============================
 */
public class ParseProperties {
	
	private static final Logger LOGGER = LogManager.getLogger(ParseProperties.class);

	private static Properties prop;
	
	/**
	 * 根据地址获取配置属性
	 * @param path
	 * @throws IOException
	 */
	public static void getProperties(String path) throws IOException{

		prop = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream(path));
		prop.load(in); 
		
	}
	
	/**
	 * 解析配置属性
	 * @param path
	 * @param key
	 * @return
	 */
	public static String parseProperties(String path,String key){
		
		try {
			
			if(null == prop){
				
				ParseProperties.getProperties(path);
			}
			
			Object value = prop.getProperty(key);
			
			if(null != value){
				
				return value.toString();
			}else{
				
				return "";
			}
		} catch (Exception e) {

			LOGGER.error("Parse properties happened exception {}",e);
			return "";
		}
	}
}
