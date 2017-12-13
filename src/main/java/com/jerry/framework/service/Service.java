package com.jerry.framework.service;

import java.util.List;
import java.util.Map;

import com.jerry.framework.dao.support.Page;


/**
 * 让服务接口继承（extends）此基础服务（Service），
 * 这样所有的action通过任何服务接口都可以访问到Service中
 * 所定义的通用业务方法，从而避免在各个服务中重复定义常用功能的方法。<br>
 * 另外从BaseDispatchAction中可以直接使用getBaseService()获取Service，
 * 从而简化Action对此基础服务功能的获取。
 * @Class Name Service
 * @Author likang
 * @Create In Jul 22, 2010
 */
public interface Service {
	
	/**
	 * 保存持久化或游离状态的对象
	 * @param o
	 * @return Object
	 * @Methods Name save
	 * @Create In Aug 3, 2010 By likang
	 * @return
	 * @throws Exception Object
	 */
	public Object save(Object o)throws Exception;
	
	/**
	 * 更新实体对象
	 * @Methods Name update
	 * @Create In Aug 3, 2010 By likang
	 * @param o
	 * @throws Exception void
	 */
	public Integer update(Object o)throws Exception;

	/**
	 * 物理删除持久化状态
	 * @Methods Name remove
	 * @Create In Aug 3, 2010 By likang
	 * @param o
	 * @throws Exception void
	 */
	public void remove(Object o) throws Exception;

	/**
	 * 物理删除编号是id的持久化对象
	 * @Methods Name removeObject
	 * @Create In Aug 3, 2010 By likang
	 * @param clazz
	 * @param id
	 * @throws Exception Integer
	 */
	public Integer removeObject(Class clazz, Object id)throws Exception;
    
    /**
     * 物理批量删除
     * @Methods Name removeObjectByIds
     * @Create In Jul 22, 2010 By debby
     * @param clazz
     * @param value id的逗号分隔 如 1,2,3
     */
	public Integer removeObjectByIds(Class clazz, String values)throws Exception;

    /**
     * 逻辑删除持久化对象
     * 默认删除标记为deleteFlag 需要每个实体有该属性
     * @Methods Name removeObjectByFlag
     * @Create In Jul 22, 2010 By debby
     * @param clazz
     * @param id void
     */
	public Integer removeObjectByFlag(Class clazz, Object id)throws Exception;
    
    /**
     * 批量逻辑删除持久化对象
     * 默认删除标记为deleteFlag 需要每个实体有该属性
     * @Methods Name removeObjectsByFlag
     * @Create In Aug 3, 2010 By likang
     * @param clazz
     * @param values id的逗号分隔 如 1,2,3
     * @throws Exception void
     */
	public Integer removeObjectsByFlag(Class clazz, String values)throws Exception;

	/**
	 * 获取指定类的所有持久化对象
	 * @Methods Name getObjects
	 * @Create In Aug 3, 2010 By likang
	 * @param clazz
	 * @return
	 * @throws Exception List
	 */
	public List getObjects(Class clazz) throws Exception;
	
	
	/**
	 * 按条件不分页获取指定类的所有持久化对象
	 * @Methods Name getObjectsByParam
	 * @Create In Aug 3, 2010 By likang
	 * @param clazz
	 * @param map	  需要传入参数的map  例如 map.put("属性名",值);
	 * @param orderBy 排序字段
	 * @param isAsc   是否升序
	 * @return
	 * @throws Exception List
	 */
	public List getObjectsByParam(Class clazz, Map map, String orderBy, Boolean isAsc)throws Exception;
	
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
     * 分页查询，不指定查询条件
     * @Methods Name findAllByPageWithoutCondition
     * @Create In 2015-1-26 By likang
     * @param clazz
     * @param orderBy
     * @param isAsc
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception Page
     */
	@Deprecated
	public Page findAllByPageWithoutCondition(Class clazz, String orderBy, Boolean isAsc, int pageNo, int pageSize)throws Exception;
	
    /**
     * 分页查询，指定查询条件 （Oracle）
     * @Methods Name findAllByPage
     * @Create In Jul 22, 2010 By likang
     * @param clazz    要查询的实体
     * @param map	   需要传入参数的map  例如 map.put("属性名",值);
     * @param orderBy  排序字段
     * @param isAsc    是否升序
     * @param pageNo   页码
     * @param pageSize 页大小
     * @return Page	   分页对象
     */
	public Page findAllByPage(Class clazz, Map map, String orderBy, Boolean isAsc, int pageNo, int pageSize)throws Exception;

	/**
	 * 分页查询，指定查询条件,返回结果不带总页数
	 * @Methods Name findPageListByParam
	 * @Create In Aug 18, 2010 By debby
	 * @param clazz
	 * @param map
	 * @param orderBy
	 * @param isAsc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception List
	 */
	public List findPageListByParam(Class clazz, Map map, String orderBy,
                                    Boolean isAsc, int pageNo, int pageSize)throws Exception;
	
	/**
	 * 根据查询语句名字，按条件不分页获取指定类的所有持久化对象
	 * @Methods Name getObjectsByQueryName
	 * @Create In Jan 21, 2011 By likang
	 * @param clazz
	 * @param queryName	sqlMap中查询语句名称
	 * @param map	  需要传入参数的map  例如 map.put("属性名",值);
	 * @param orderBy 排序字段
	 * @param isAsc   是否升序
	 * @return
	 * @throws Exception List
	 */
	public List getObjectsByQueryName(Class clazz, String queryName, Map map, String orderBy, Boolean isAsc)throws Exception;
	
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
	
	/**
	 * 分页查询辅助方法 封装成page对象(用于一个SqlMap中有同时有多个分页查询情况下自定义查询语句id)
	 * @Methods Name queryByPage
	 * @Create In Jan 22, 2011 By likang
	 * @param clazz	要查询的实体
	 * @param paramPageQueryName	条件分页查询在sqlMap中的id
	 * @param paramTotalQueryName	条件总数查询在sqlMap中的id
	 * @param queryMap				条件Map
	 * @param pageNo				当前页码
	 * @param pageSize				分页长度
	 * @return Page
	 */
	public Page pageQueryBySqlName(Class clazz, String paramPageQueryName,
                                   String paramTotalQueryName, Map queryMap, int pageNo, int pageSize);
	
	
	/**
	 * 带参数查询全部结果的方法
	 * @Methods Name getObjectByParam
	 * @Create In Mar 7, 2012 By Administrator
	 * @param clazz
	 * @param map
	 * @return Object
	 */
	public Object getObjectByParam(Class clazz, Map map);
	
	/**
	 * 根据查询语句名字查询返回List对象
	 * @Methods Name getListBySqlId
	 * @Create In Feb 3, 2012 By likang
	 * @param clazz
	 * @param sqlId
	 * @param map
	 * @return List
	 */
	public List getListBySqlId(Class clazz, String sqlId, Map map);
	
	
	/**
	 * 根据查询语句名执行 返回影响条数Integer
	 * @Methods Name removeBySqlId
	 * @Create In 2015-1-26 By likang
	 * @param sqlId
	 * @param o
	 * @return Integer
	 */
	public Integer removeBySqlId(String sqlId, Object o);
	
	
	/**
	 * 根据查询语句名执行 返回影响条数Integer
	 * @Methods Name updateBySqlId
	 * @Create In 2015-1-26 By likang
	 * @param sqlId
	 * @param o
	 * @return Integer
	 */
	public Integer updateBySqlId(String sqlId, Object o);
	
	/**
	 * 批量保存
	 * @Methods Name batchSave
	 * @Create In 2015-1-26 By likang
	 * @param clazz
	 * @param list
	 * @return Integer
	 */
	public Integer batchSave(Class clazz, List list);
	
	/**
	 * 根据queryName查询 返回多条map实体 需在xml中自定义sql
	 * @Methods Name getObjectsByQueryName
	 * @Create In 2015-1-27 By likang
	 * @param queryName
	 * @param map
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getObjectsByQueryName(String queryName, Map map);

}



