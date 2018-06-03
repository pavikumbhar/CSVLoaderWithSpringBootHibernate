package com.pravinkumbhar.dao;

import java.util.HashMap;
import java.util.List;

public interface PersistanceDao {

	/**
	 * 
	 * @param t
	 * @return
	 */
	public <T> T save(T t);

	/**
	 * 
	 * @param t
	 * @param id
	 * @return
	 */
	public <T, PK> T read(Class<T> t, PK id);

	/**
	 * 
	 * @param t
	 * @return
	 */
	public <T> T update(T t);

	/**
	 * 
	 * @param t
	 */
	public <T> void delete(T t);

	/**
	 * @param queryString
	 * @return
	 */
	public List<Object[]> getNativeQueryResultList(String queryString, Object... bindVariables);

	/**
	 * 
	 * @param queryString
	 * @param typeKey
	 * @return
	 */
	public <T> List<T> getEntityList(String queryString, Class<T> typeKey, Object... bindVariables);
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> T createElement(Class<T> clazz);
	
	
	/**
	 * 
	 * @param queryString
	 * @param queryParamValue
	 * @return
	 */
	public  <T> List<T> getEntityList(String queryString, HashMap<String, Object>  queryParamValue);
	
	
	/**
	 * 
	 * @param typeKey
	 * @param name
	 * @return
	 */
	 public <T> T getEntityByName(Class<T> typeKey,  String name);
	 
	 
	 /**
	  * 
	  * @param queryString
	  * @param typeKey
	  * @param bindVariables
	  * @return
	  */
	 public <T> T getNativeSingleColumnValue(String queryString,Class<T> typeKey, Object... bindVariables);
	 
	 /**
	  * 
	  * @param queryString
	  * @param typeKey
	  * @param bindVariables
	  * @return
	  */
	 public <T> T getSingleColumnValue(String queryString,Class<T> typeKey, Object... bindVariables);
	 
	 /***
	  * 
	  * @param recordList
	  */
	 public <T> void saveRecordList(List<T> recordList);
	 
	 /**
	  * 
	  * @param clazz
	  */
	 public void deleteFromTable(Class<?> clazz);
}
