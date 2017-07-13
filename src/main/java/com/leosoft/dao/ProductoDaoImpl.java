/**
 * 
 */
package com.leosoft.dao;

import com.leosoft.entities.Producto;
import com.leosoft.util.dao.BaseDaoJPA;

/**
 * @author leonc
 *
 */
public class ProductoDaoImpl extends BaseDaoJPA<Producto, Long> implements ProductoDao {

	public ProductoDaoImpl() {
		super(Producto.class);
	}

}
