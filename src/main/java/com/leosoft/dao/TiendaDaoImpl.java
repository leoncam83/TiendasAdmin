/**
 * 
 */
package com.leosoft.dao;

import com.leosoft.entities.Tienda;
import com.leosoft.util.dao.BaseDaoJPA;

/**
 * @author leonc
 *
 */
public class TiendaDaoImpl extends BaseDaoJPA<Tienda, Long> implements TiendaDao {

	public TiendaDaoImpl() {
		super(Tienda.class);
	}

}
