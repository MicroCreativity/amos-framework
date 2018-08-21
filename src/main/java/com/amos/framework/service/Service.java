package com.amos.framework.service;
import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;
import java.util.List;
/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 */
public interface Service<T> {
	
	/**
	 * 保存
	 * @param model
	 */
    void save(T model);
    
    /**
     * 批量保存
     * @param models
     */
    void save(List<T> models);
    
    /**
     * 根据主键删除
     * @param id
     */
    void deleteById(Integer id);
    
    /**
     * 根据主键删除
     * @param id
     */
    void deleteById(Object id);
    
    /**
     * 根据主键批量删除,ids -> “1,2,3,4”
     * @param ids
     */
    void deleteByIds(String ids);
    
    /**
     * 修改数据
     * @param model
     */
    void update(T model);
    
    /**
     * 根据ID查找对象
     * @param id
     * @return
     */
    T findById(Integer id);
    
    /**
     * 根据ID查找对象
     * @param id
     * @return
     */
    T findById(Object id);
    
    /**
     * 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
     * @param fieldName
     * @param value
     * @return
     * @throws TooManyResultsException
     */
    T findBy(String fieldName, Object value) throws TooManyResultsException;
    
    /**
     * 根据多个ID查找集合对象
     * @param ids
     * @return
     */
    List<T> findByIds(String ids);
    
    /**
     * 根据条件查找
     * @param condition
     * @return
     */
    List<T> findByCondition(Condition condition);
    
    /**
     * 获取所有列表
     * @return
     */
    List<T> findAll();
}