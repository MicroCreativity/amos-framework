package com.amos.framework.i18n;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
/**
 * ================================
 * 作者:linyantao
 * 时间:2018年7月12日下午3:24:44
 * 内容:获取国际化信息
 * ================================
 */
@Component
public class MessageManager {

	@Autowired
	private MessageSource messageSource;
	
	public String getMsg(String key) {
		
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(key, null, locale);
	}

	public String getMsg(String key, String... arg) {
		
		Locale locale = LocaleContextHolder.getLocale();
		Object[] args = new Object[arg.length];
		for (int i = 0; i < arg.length; i++) {
			args[i] = arg[i];
		}
		return messageSource.getMessage(key, args, locale);
	}

}
