package com.pravinkumbhar.service;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pravinkumbhar.dao.PersistanceDao;
import com.pravinkumbhar.service.PersistanceService;

/**
 * 
 * @author pavikumbhar
 *
 */
@Service
@Transactional
public class PersistanceServiceImp implements PersistanceService {

	@Autowired
	private PersistanceDao persistanceDao;

	@Transactional
	@Override
	public <T> T save(T t) {
		return persistanceDao.save(t);
	}

	@Transactional(readOnly=true)
	@Override
	public <T, PK> T read(Class<T> t, PK id) {
		return persistanceDao.read(t, id);
	}
    
	@Transactional
	@Override
	public <T> T update(T t) {
		return persistanceDao.update(t);
	}

	@Transactional
	@Override
	public <T> void delete(T t) {
		persistanceDao.delete(t);

	}

	@Transactional(readOnly=true)
	@Override
	public List<Object[]> getNativeQueryResultList(String queryString, Object... bindVariables) {
		return  persistanceDao.getNativeQueryResultList(queryString,bindVariables);
	}

	@Transactional(readOnly=true)
	@Override
	public <T> List<T> getEntityList(String queryString, Class<T> typeKey, Object... bindVariables) {
		return persistanceDao.getEntityList(queryString, typeKey,bindVariables);
	}

	@Override
	public <T> T createElement(Class<T> clazz) {
		return persistanceDao.createElement(clazz);
	}

	@Transactional(readOnly=true)
	@Override
	public <T> List<T> getEntityList(String queryString, HashMap<String, Object> queryParamValue) {
	
		return persistanceDao.getEntityList(queryString,queryParamValue);
	}

	@Transactional(readOnly=true)
	@Override
	public <T> T getEntityByName(Class<T> typeKey, String name) {
		
		return persistanceDao.getEntityByName(typeKey, name);
	}

	@Transactional(readOnly=true)
	@Override
	public <T> T getNativeSingleColumnValue(String queryString, Class<T> typeKey, Object... bindVariables) {
		return persistanceDao.getNativeSingleColumnValue(queryString,typeKey, bindVariables);
	}
	@Transactional(readOnly=true)
	@Override
	public <T> T getSingleColumnValue(String queryString, Class<T> typeKey,Object... bindVariables) {
		return persistanceDao.getSingleColumnValue(queryString,typeKey, bindVariables);
	}

	@Override
	public <T> void saveRecordList(List<T> recordList) {
		persistanceDao.saveRecordList(recordList);
		
	}

	@Override
	public void deleteFromTable(Class<?> clazz) {
		persistanceDao.deleteFromTable(clazz);
		
	}

}
