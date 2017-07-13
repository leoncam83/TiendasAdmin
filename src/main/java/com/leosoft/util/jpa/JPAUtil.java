/**
 * 
 */
package com.leosoft.util.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * @author leonc
 *
 */
public class JPAUtil {

	private final static String JPA_PERSITENCE = "TiendasAdmin";

	private static EntityManagerFactory factory =  Persistence.createEntityManagerFactory(JPA_PERSITENCE);

	public static EntityManager getManager() {

		if ( factory == null || !factory.isOpen()){
			factory = Persistence.createEntityManagerFactory(JPA_PERSITENCE);
		}

		return factory.createEntityManager();
	}

	public static void close(){

		if (!factory.isOpen()){
			factory.close();
		}		

	}

	public static void clear() {
		factory.getCache().evictAll();
	}

	public static void clear(Class<?> clase, Object arg) {
		factory.getCache().evict(clase, arg);
	}

	public static void clear(Class<?> clase) {
		factory.getCache().evict(clase);
	}

	/******/
	
}
