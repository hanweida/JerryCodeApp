package com.jerry.framework.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;


/**
 * 实现Dao接口 为基础数据提供数据库访问方法 mybatis版本
 * @Class Name BaseDao
 * @Author likang
 * @Create In Jul 19, 2010
 */
public class BaseDao<T> extends SqlSessionDaoSupport implements Dao<T>{

	/**
	 * 根据实体class查找全部对象
	 * @Methods Name removeObject
	 * @Create In Jul 22, 2010 By likang
	 * @param clazz 查询实体的class
	 * @return void
	 */
	public List getObjects(Class clazz) throws Exception{
		return this.getSqlSession().selectList(clazz.getName()+".selectAll");
	}

	/**
	 * 根据实体的id删除
	 * @Methods Name removeObject
	 * @Create In Jul 22, 2010 By likang
	 * @param clazz 删除实体的class
	 * @param id 实体Id
	 * @return Integer
	 */
	public Integer removeObject(Class clazz, Object id) throws Exception{
		return this.getSqlSession().delete(clazz.getName()+".delete",id);
	}
	
	/**
	 * 实体对象的通用保存方法
	 * 通过sqlmapconfig中的insert来保存实体
	 * @Methods Name save
	 * @Create In Jul 22, 2010 By likang
	 * @param o 实体对象
	 * @return Object
	 */
	public Object save(Object o) throws Exception{
		this.getSqlSession().insert(o.getClass().getName()+".insert",o);
		return o;
	}
	
	/**
	 * 根据实体的id逻辑删除
	 * @Methods Name removeObjectByFlag
	 * @Create In Jul 22, 2010 By likang
	 * @param clazz 删除实体的class
	 * @param id 实体Id
	 * @return void
	 */
	public Integer removeObjectByFlag(Class clazz, Object id) throws Exception{
		return this.getSqlSession().update(clazz.getName()+".deleteByLogic",id);
	}

	/**
	 * 带参数查询全部结果的方法，
	 * 所有参数根据配置文件的查询sql组成map对象
	 * querySql参数为查询sql的id
	 * 可以根据传入的参数进行查询结果的排序
	 * @Methods Name getObjectsByParam
	 * @Create In Jul 22, 2010 By likang
	 * @param clazz
	 * @param map
	 * @param querySqlId
	 * @return List
	 */
	public List getObjectsByParam(Class clazz, Map map, String querySqlId) throws Exception{
		return this.getSqlSession().selectList(clazz.getName()+"."+querySqlId,map);
	}

	/**
	 * 根据实体ID查找对象
	 * @Methods Name getObjectById
	 * @Create In Jul 22, 2010 By likang
	 * @param clazz
	 * @param id
	 * @return Object
	 */
	public Object getObjectById(Class clazz, Object id) throws Exception{
		return this.getSqlSession().selectOne(clazz.getName()+".selectById",id);
	}

	/**
     * 根据ids物理删除 例如 1,2,3
     * @Methods Name removeObjectByIds
     * @Create In Aug 3, 2010 By likang
     * @param clazz
     * @param value
     * @throws Exception void
     */
	public Integer removeObjectByIds(Class clazz, String value) throws Exception{
		return this.getSqlSession().delete(clazz.getName()+".deleteByIds",value.split(","));
	}

	/**
	 * 更新实体对象
	 * @Methods Name update
	 * @Create In Jul 22, 2010 By likang
	 * @param o void
	 */
	public Integer update(Object o) throws Exception{
		return this.getSqlSession().update(o.getClass().getName()+".update",o);
	}

	/**
	 * 直接查询全部总数
	 * @Methods Name selectTotal
	 * @Create In Jul 23, 2010 By likang
	 * @param clazz
	 * @return int
	 */
	public Integer selectTotal(Class clazz) {
		return (Integer) this.getSqlSession().selectOne(clazz.getName()+".selectTotal");
	}

	/**
	 * 根据参数查询全部总数
	 * @Methods Name selectTotalByParam
	 * @Create In Jul 23, 2010 By likang
	 * @param clazz
	 * @param map
	 * @return int
	 */
	public Integer selectTotalByParam(Class clazz, Map map) {
		return (Integer) this.getSqlSession().selectOne(clazz.getName()+".selectTotalByParam",map);
	}
	
	/**
	 * 按条件不分页获取指定类的所有持久化对象
	 * @Methods Name getObjectsByParamNoPage
	 * @Create In Aug 3, 2010 By likang
	 * @param clazz
	 * @param map	  需要传入参数的map  例如 map.put("属性名",值);
	 * @return
	 * @throws Exception List
	 */
	public List getObjectsByParamNoPage(Class clazz, Map map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(clazz.getName()+".selectObjectsByParamNoPage",map);
	}
	
	/**
     * 批量逻辑删除持久化对象
     * 默认删除标记为deleteFlag 需要每个实体有该属性
     * @Methods Name removeObjectsByFlag
     * @Create In Aug 3, 2010 By likang
     * @param clazz
     * @param values id的逗号分隔 如 1,2,3
     * @throws Exception void
     */
	public Integer removeObjectsByFlag(Class clazz, String values) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(clazz.getName()+".deleteObjectsByLogic",values.split(","));
	}
	
	/**
	 * 根据查询语句名字，按条件不分页获取指定类的所有持久化对象
	 * @Methods Name getObjectsByQueryName
	 * @Create In Jan 21, 2011 By likang
	 * @param clazz
	 * @param queryName
	 * @param map
	 * @return List
	 */
	public List getObjectsByQueryName(Class clazz, String queryName, Map map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(clazz.getName()+"." + queryName,map);
	}
	
	/**
	 * 根据参数，通过查询语句的名字查询全部总数
	 * @Methods Name selectTotalByQueryName
	 * @Create In Jan 22, 2011 By likang
	 * @param clazz
	 * @param queryName
	 * @param map
	 * @return Integer
	 */
	public Integer selectTotalByQueryName(Class clazz, String queryName, Map map) {
		// TODO Auto-generated method stub
		return (Integer) this.getSqlSession().selectOne(clazz.getName()+"."+queryName,map);
	}
	
	public Object getObjectByParam(Class clazz, Map map) {
		return this.getSqlSession().selectOne(clazz.getName() + ".selectObjectByParam", map);
	}

	@Override
	public Integer removeBySqlId(String sqlId, Object o) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete(sqlId, o);
	}

	@Override
	public Integer updateBySqlId(String sqlId, Object o) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(sqlId, o);
	}

	@Override
	public Integer batchInsert(Class clazz, List list) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(clazz.getName() + ".batchInsert", list);
	}

	@Override
	public List<Map<String, Object>> queryObjectsByQueryName(String queryName,
			Map map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(queryName,map);
	}
	
}
