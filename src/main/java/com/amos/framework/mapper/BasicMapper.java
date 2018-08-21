package com.amos.framework.mapper;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface BasicMapper<T>
		extends Mapper<T>, MySqlMapper<T>, BaseMapper<T>, ConditionMapper<T>, IdsMapper<T>, InsertListMapper<T> {

}
