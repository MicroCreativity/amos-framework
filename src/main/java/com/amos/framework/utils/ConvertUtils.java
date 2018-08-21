package com.amos.framework.utils;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

/**
 * ===============================
 * 作者：amos lam
 * 时间：2018年7月23日下午7:17:32
 * 内容：类值转换映射帮助类
 * ===============================
 */
public class ConvertUtils {
	/**
	 * list列表类型转换类
	 * 
	 * @param sources 原始数据list列表
	 * @param targetClass 转换后的class
	 * @param filters 目的类型的实例中的不需要设置的字段名称
	 * @return 目的类型实例list列表
	 */
	public final static <S, T> List<T> convertList(List<S> sources, final Class<T> targetClass, final String... filters) {
		Optional<List<S>> absentList = Optional.of(sources);
		if (absentList.isPresent()) {
			Collection<T> transformColl = Collections2.transform(absentList.get(), new Function<S, T>() {
				public T apply(S input) {
					return convertObject(input, targetClass, filters);
				}
			});
			
			return Lists.newArrayList(transformColl);
		}
		
		return Lists.newArrayList();
	}

	/**
	 * 类型转换类
	 * 
	 * @param source 原始类型实例
	 * @param target 目的类型
	 * @param filters 目的类型的实例中的不需要设置的字段名称
	 * @return 目的类型实例
	 */
	public final static <S, T> T convertObject(S source, Class<T> target, String... filters) {
		T t = null;
		try {
			t = target.newInstance();
			BeanUtils.copyProperties(Optional.of(source).get(), t, filters);
		} catch (Exception e) {
			Throwables.propagateIfPossible(e);
		}

		return t;
	}
}
