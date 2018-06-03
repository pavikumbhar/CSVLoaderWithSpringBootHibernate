package com.pravinkumbhar.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author pavikumbhar
 *
 */
@Repository
public class PersistanceDaoImp implements PersistanceDao{

	 @PersistenceContext
	 private EntityManager entityManager;

  /**
   * 
   * @param t
   * @return
   */
   public <T> T save(T t) {
       this.entityManager.persist(t);
       return t;
   }

  /**
   * 
   * @param t
   * @param id
   * @return
   */
   public <T, PK> T read(Class<T> t,PK id) {
       return this.entityManager.find(t, id);
   }

  /**
   * 
   * @param t
   * @return
   */
   public <T> T update(T t) {
       return this.entityManager.merge(t);
   }

  /**
   * 
   * @param t
   */
   public <T> void delete(T t) {
       t = this.entityManager.merge(t);
       this.entityManager.remove(t);
   }
   
   /**
    * @param queryString
    * @return	
    */
   public   List<Object[]>  getNativeQueryResultList(String queryString, Object... bindVariables){
		String queryFormated = String.format(queryString, bindVariables);
		  Query query = this.entityManager.createNativeQuery(queryFormated);
		  List<Object[]> records = query.getResultList();
		return records;
				
	}
   
   /**
    * 
    * @param queryString
    * @param typeKey
    * @return
    */
   public  <T> List<T> getEntityList(String queryString,Class<T> typeKey, Object... bindVariables) {
	   String queryFormated = String.format(queryString, bindVariables);
	     List<T> entityList  =  new ArrayList<T>();
	    TypedQuery<T> query = this.entityManager.createQuery(queryFormated,typeKey);
	    entityList=query.getResultList();
	    return entityList;
   }
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public  <T> T createElement(Class<T> clazz) {
		T t = null;
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			t = constructor.newInstance();
			return t;
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {

			e.printStackTrace();
		}
		return t;

	} 
	

	/**
	 * @param queryString
	 * @param queryParamValue
	 * @return
	 */
	public  <T> List<T> getEntityList(String queryString, HashMap<String, Object>  queryParamValue){
		  List<T> entityList  =  new ArrayList<T>();
		 Query query = this.entityManager.createNativeQuery(queryString);
		 for (Map.Entry<String, Object> pair : queryParamValue.entrySet()) {
			    query.setParameter(pair.getKey(), pair.getValue());
			}
		 entityList=query.getResultList();
		return entityList;
	}
	
	
	/**
	 * This method queries DB based on name  and Cast the result Class typekey
	 * provided in input.
	 * Returns object of Class Type  
	 * @param name  based on name fire query. 
	 * @param typeKey      class type in which you want result
	 * @return <T> T   Returns object with results of the query. 
	 * <pre>
	 * eg.
	 * {@code
	 *    String name="PHYSICALSTRUCTURE";
	 *	  Entity entity= persistanceDelegator.getEntityByName(Entity.class, name);
	 * }
	 * </pre>
	 */
	 public <T> T getEntityByName(Class<T> typeKey,  String name) {
	 	List<T> entityList  =  new ArrayList<T>();
	    TypedQuery<T> query = this.entityManager.createQuery("from " + typeKey.getName() + " where name = :name",typeKey);
	     query.setParameter("name", name);
	    entityList=query.getResultList();
	    return  (T) entityList.stream().findFirst().orElse(null);

	    
	}
	 
	 
 /**
     * This method queries get Single column value  and Cast the result Class typekey
     * @param queryString
     * @param typeKey  class type in which you want result
     * @param bindVariables  queryString Bind variables to be replaced in template query. 
     * @return  <T> T  Returns object with results of the query. 
     * <pre>
     * eg.
	 * {@code
	 *    String queryString ="Select name from Entity  where id='%s";
	 *    Long id=100; 
	 *	  Entity entity= persistanceDelegator.getNativeSingleColumnValue(queryString, String.class, id);
	 * }
	 * </pre>
     */
    @SuppressWarnings("unchecked")
	public <T> T getNativeSingleColumnValue(String queryString,Class<T> typeKey, Object... bindVariables) {
	   String queryFormated = String.format(queryString, bindVariables);
	     Query query = this.entityManager.createNativeQuery(queryFormated);
	    T t=(T)query.getResultList().stream().findFirst().orElse(null);
	    return  t;
	}
    
	    
    /**
     * This method queries get Single column value  and Cast the result Class typekey
     * @param queryString
     * @param typeKey  class type in which you want result
     * @param bindVariables  queryString Bind variables to be replaced in template query. 
     * @return  <T> T  Returns object with results of the query. 
     * <pre>
     * eg.
	 * {@code
	 *    String queryString ="Select name from Entity  where id='%s";
	 *    Long id=100; 
	 *	  Entity entity= persistanceDelegator.getSingleColumnValue(queryString, String.class, id);
	 * }
	 * </pre>
     */
	    @SuppressWarnings("unchecked")
  public <T> T getSingleColumnValue(String queryString, Class<T> typeKey, Object... bindVariables) {
		   String queryFormated = String.format(queryString, bindVariables);
		     Query query = this.entityManager.createQuery(queryFormated);
		    T t=(T)query.getResultList().stream().findFirst().orElse(null);
		    return  t;
		}
	

	
	/**
	 * 
	 * @param recordList
	 */
	 public <T> void saveRecordList(List<T> recordList){
			
		  try {   int batchSize = 20;

	      	for ( int i=0; i<recordList.size(); i++ ) {
	      	
	      		T t=recordList.get(i);
	      		 entityManager.persist(t);
	      	
	      	    if ( i % batchSize == 0 ) { 
	      	    	entityManager.flush();
	      	    	entityManager.clear();
	      	    }
	      	}
	    
	      } catch (Exception e) {
	        e.printStackTrace();
	      }

	 }
	 
	/***
	 *  
	 */
	public void deleteFromTable(Class<?> clazz) {
		System.err.println("deleteFromTable: {}   "+clazz.getSimpleName());
		entityManager.createQuery("DELETE FROM " + clazz.getSimpleName()).executeUpdate();

	}

}
