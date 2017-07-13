/**
 * 
 */
package com.leosoft.util.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

/**
 * @author Leonardo Castro Mestra
 *
 */
public interface BaseDao<T extends Serializable, ID> {

	public void persist(T transientInstance, EntityManager manager) throws Exception;
		
	public void delete(T persistentInstance, EntityManager manager) throws Exception;
	
	public void delete(ID id, EntityManager manager) throws Exception;
	
	public List<T> findByQuery(String query, EntityManager manager) throws Exception;
	
	public List<T> findByQuery(String query, String[] parametros, Object[] value,  EntityManager manager) throws Exception;
		
	public T merge(T detachedInstance, EntityManager manager) throws Exception;
	
	public List<T> findAll(EntityManager manager) throws Exception;
	
	public List<T> findAll(EntityManager manager,  String[] parametros, Boolean[] ordens) throws Exception;
	
	public T findById(ID id, EntityManager manager) throws Exception;
	
	int executeUpdate(String query, EntityManager manager) throws Exception;

	int executeUpdate(String query, String[] parametros, Object[] value, EntityManager manager) throws Exception;

	void deleteAll(EntityManager manager) throws Exception;

	T findByFields(String[] fields, Object[] values, EntityManager manager) throws Exception;

}
