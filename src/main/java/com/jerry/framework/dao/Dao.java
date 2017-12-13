package com.jerry.framework.dao;

import java.util.List;
import java.util.Map;

/**
 * 针对单个Entity对象的操作定义.不依赖于具体ORM实现方案.
 * 提供Service的所有数据库操作接口
 * @Class Name Dao
 * @Author likang
 * @Create In Jul 22, 2010
 * @param <T>
 */
public interface Dao<T> {
	
	/**
	 * 保存实体对象
	 * @Methods Name save
	 * @Create In Jul 22, 2010 By debby
	 * @param o
	 * @return Object
	 */
	public Object save(Object o) throws Exception;
	
	/**
	 * 更新实体对象
	 * @Methods Name update
	 * @Create In Jul 22, 2010 By debby
	 * @param o Integer
	 */
	public Integer update(Object o)throws Exception;

    /**
	 * 提供对象id删除持久化对象
	 * @param id Integer
	 */
	public Integer removeObject(Class clazz, Object id)throws Exception;
    
    /**
     * 根据ids物理删除 例如 1,2,3
     * @Methods Name removeObjectByIds
     * @Create In Aug 3, 2010 By likang
     * @param clazz
     * @param value
     * @throws Exception void
     */ 
	public Integer removeObjectByIds(Class clazz, String value)throws Exception;
    
    /**
     * 逻辑删除持久化对象
     * 默认删除标记为deleteFlag
     * @Methods Name removeObjectByFlag
     * @Create In Jul 22, 2010 By debby
     * @param clazz
     * @param id void
     */
	public Integer removeObjectByFlag(Class clazz, Object id)throws Exception;

	
	/**
	 * 根据实体ID查找对象
	 * @Methods Name getObjectById
	 * @Create In Jul 22, 2010 By debby
	 * @param clazz
	 * @param id
	 * @return Object
	 */
	public Object getObjectById(Class clazz, Object id)throws Exception;
	
	/**
	 * 获取指定类的所有持久化对象
	 * @param clazz
	 * @return List
	 */
	public List getObjects(Class clazz)throws Exception;

	
	/**
	 * 带参数查询全部结果的方法，
	 * 所有参数根据配置文件的查询sql组成map对象
	 * querySql参数为查询sql的id
	 * 可以根据传入的参数进行查询结果的排序
	 * @Methods Name getObjectsByParam
	 * @Create In Jul 22, 2010 By debby
	 * @param clazz
	 * @param map
	 * @param querySql
	 * @return List
	 */
	public List getObjectsByParam(Class clazz, Map map, String querySql)throws Exception;
	
	/**
	 * 直接查询全部总数
	 * @Methods Name selectTotal
	 * @Create In Jul 23, 2010 By debby
	 * @param clazz
	 * @return int
	 */
	public Integer selectTotal(Class clazz);
	
	/**
	 * 根据参数查询全部总数
	 * @Methods Name selectTotalByParam
	 * @Create In Jul 23, 2010 By debby
	 * @param clazz
	 * @param map
	 * @return int
	 */
	public Integer selectTotalByParam(Class clazz, Map map);
	
	/**
     * 批量逻辑删除持久化对象
     * 默认删除标记为deleteFlag 需要每个实体有该属性
     * @Methods Name removeObjectsByFlag
     * @Create In Aug 3, 2010 By likang
     * @param clazz
     * @param values id的逗号分隔 如 1,2,3
     * @throws Exception void
     */
	public Integer removeObjectsByFlag(Class clazz, String values);
	
	/**
	 * 按条件不分页获取指定类的所有持久化对象
	 * @Methods Name getObjectsByParamNoPage
	 * @Create In Aug 3, 2010 By likang
	 * @param clazz
	 * @param map	  需要传入参数的map  例如 map.put("属性名",值);
	 * @return
	 * @throws Exception List
	 */
	public List getObjectsByParamNoPage(Class clazz, Map map);

	/**
	 * 根据查询语句名字，按条件获取指定类的所有持久化对象
	 * @Methods Name getObjectsByQueryName
	 * @Create In Jan 21, 2011 By likang
	 * @param clazz
	 * @param queryName
	 * @param map
	 * @return List
	 */
	public List getObjectsByQueryName(Class clazz, String queryName, Map map);
	
	/**
	 * 根据参数，通过查询语句的名字查询全部总数
	 * @Methods Name selectTotalByQueryName
	 * @Create In Jan 22, 2011 By likang
	 * @param clazz
	 * @param queryName
	 * @param map
	 * @return Integer
	 */
	public Integer selectTotalByQueryName(Class clazz, String queryName, Map map);

	public Object getObjectByParam(Class clazz, Map map);

	/**
	 * 根据查询语句名执行 返回Object
	 * @Methods Name removeBySqlId
	 * @Create In 2015-1-26 By likang
	 * @param sqlId
	 * @param o
	 * @return Integer
	 */
	public Integer removeBySqlId(String sqlId, Object o);
	
	public Integer updateBySqlId(String sqlId, Object o);

	public Integer batchInsert(Class clazz, List list);
	
	/**
	 * 根据queryName查询 返回多条map实体
	 * @Methods Name getObjectsByQueryName
	 * @Create In 2015-1-27 By likang
	 * @param queryName
	 * @param map
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryObjectsByQueryName(String queryName,
                                                             Map map);
	
}
