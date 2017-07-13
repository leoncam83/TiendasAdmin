/**
 * 
 */
package com.leosoft.util.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * @author Leonardo Castro Mestra
 *
 */
public class BaseDaoJPA<T extends Serializable, ID>  implements BaseDao<T, ID> {

	Class<T> type;
	
	public BaseDaoJPA(Class<T> type) {
		this.type = type;
	}
	
	@Override
	public void persist(T transientInstance, EntityManager manager) throws Exception {
		manager.persist(transientInstance);		
	}

	@Override
	public void delete(T persistentInstance, EntityManager manager) throws Exception {		
		manager.remove(persistentInstance);
	}
	
	@Override
	public void delete(ID id, EntityManager manager) throws Exception {
		T t = manager.getReference(type, id);
		this.delete(t, manager);
	}
	
	@Override
	public void deleteAll(EntityManager manager) throws Exception {
		String sql  = "DELETE FROM "+ type.getSimpleName();
		
		manager.createQuery(sql).executeUpdate();		
	}
	
	@Override
	public int executeUpdate(String query, String[] parametros, Object[] value,EntityManager manager) throws Exception {		
		Query q = manager.createQuery(query);
		
		for(int i = 0; i < parametros.length; i++) {
			q.setParameter(parametros[i], value[i]);
		}
		
		int execute = q.executeUpdate();
		
		return execute; 
	}
	
	@Override
	public int executeUpdate(String query, EntityManager manager) throws Exception {		
		Query q = manager.createQuery(query);
						
		int execute = q.executeUpdate();
		
		return execute; 
	}
	
	@Override
	public List<T> findByQuery(String query, EntityManager manager) throws Exception {
		TypedQuery<T> q = manager.createQuery(query, type);

		return q.getResultList();
	}

	@Override
	public List<T> findByQuery(String query, String[] parametros, Object[] value, EntityManager manager)
			throws Exception {
		TypedQuery<T> q = manager.createQuery(query, type);

		for(int i = 0; i < parametros.length; i++) {
			q.setParameter(parametros[i], value[i]);
		}
		
		return q.getResultList();
	}
	
	@Override
	public T merge(T detachedInstance, EntityManager manager) throws Exception {
		return manager.merge(detachedInstance);
	}

	@Override
	public List<T> findAll(EntityManager manager) throws Exception {
		String sql  = "SELECT t FROM "+ type.getSimpleName() +" t ";

		TypedQuery<T> query = manager.createQuery(sql, type);

		return query.getResultList();
	}
	
	@Override
	public List<T> findAll(EntityManager manager, String[] parametros, Boolean[] ordens) throws Exception {
		String sql  = "SELECT t FROM "+ type.getSimpleName() +" t ";

		if(parametros != null && ordens != null && parametros.length == ordens.length) {
			sql += " ORDER BY";
			for(int i=0; i<parametros.length; i++) {
				sql += " t."+parametros[i] + " " + (ordens[i] ? "ASC" : "DESC") +",";
			}

			sql = sql.substring(0, sql.length()-1);

		}
		
		TypedQuery<T> query = manager.createQuery(sql, type);

		return query.getResultList();
	}

	@Override
	public T findById(ID id, EntityManager manager) throws Exception {
		return manager.find(type, id);
	}
		
	@Override
	public T findByFields(String[] fields, Object[] values, EntityManager manager) throws Exception {
		
		if((fields == null || fields.length <=0)) {
			throw new Exception("fields no debe ser null o estar vacio");
		}
		
		if((values == null || values.length <=0)) {
			throw new Exception("values no debe ser null o estar vacio");
		}
		
		String sql = String.format("SELECT t FROM %s t WHERE", type.getSimpleName());		
		
		for(int i = 0; i < fields.length; i++) {
			sql +=  String.format(" t.1%s = :_1%s ", fields[i]);
		}
		
		TypedQuery<T> q = manager.createQuery(sql, type);
		
		for(int i = 0; i < fields.length; i++) {
			q.setParameter(String.format("_1%s", fields[i]), values[i]);
		}
		
		return q.getSingleResult();
	}

}
