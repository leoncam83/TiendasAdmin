/**
 * 
 */
package com.leosoft.util.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.leosoft.dao.TiendaDao;
import com.leosoft.dao.TiendaDaoImpl;
import com.leosoft.entities.Tienda;

/**
 * @author leonc
 *
 */
public class IniciarBaseDeDatos {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IniciarBaseDeDatos baseDeDatos = new IniciarBaseDeDatos();
	}

	public IniciarBaseDeDatos() {
		EntityManager manager = JPAUtil.getManager();  
		EntityTransaction transaction = manager.getTransaction();
		
		TiendaDao tiendaDao = new TiendaDaoImpl();
		
		transaction.begin();
		try {

			

			for(int i = 1; i < 21; i++) {
				Tienda tienda = new Tienda();
				tienda.setNombre("Tienda # " + i);
				tiendaDao.persist(tienda, manager);				
			}
			
			transaction.commit();
			System.out.println("Proseso terminado exitosamente");

		} catch (Exception e) {

			if(manager.isOpen()) {
				transaction.rollback();				
			}
			System.err.println(e.getMessage());			
			e.getStackTrace();

		} finally {
			if(manager.isOpen()) {
				manager.close();
			}		
		}

	}

}
