package com.leosoft.ws;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leosoft.dao.ProductoDao;
import com.leosoft.dao.ProductoDaoImpl;
import com.leosoft.entities.Producto;
import com.leosoft.util.jpa.JPAUtil;

/**
 * 
 * @author leonc
 *
 */
@Path("producto")
public class ProductoRest {

	ProductoDao productoDao = new ProductoDaoImpl();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("findbytienda/{id}")
	public Response findByTienda(@PathParam(value="id") Long id) {

		EntityManager manager = JPAUtil.getManager();

		List<Producto> productos = null;

		try {
			productos = productoDao.findByQuery("SELECT p FROM Producto p JOIN p.tienda t WHERE t.id = :tiendaId  ", 
					new String[] {"tiendaId"}, new Object[] {id}, manager);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager.isOpen()) {
				manager.close();
			}
		}

		return Response.ok().entity(new GenericEntity<List<Producto>>(productos) {
		}).build();

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("findbyid/{id}")
	public Response findById(@PathParam(value="id") Long id) {

		EntityManager manager = JPAUtil.getManager();

		Producto producto = null;

		try {
			producto = productoDao.findById(id, manager);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager.isOpen()) {
				manager.close();
			}
		}

		return Response.ok().entity(new GenericEntity<Producto>(producto) {
		}).build();

	}
		
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("create")
	public Response create(Producto producto) {
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		
		EntityManager manager = JPAUtil.getManager();
		EntityTransaction transaction = manager.getTransaction();

		transaction.begin();

		try {
			
			productoDao.persist(producto, manager);
			transaction.commit();
			
			return Response.ok().entity(gson.toJson("el producto fue registrada exitosamente"))				
					.build();

		} catch (Exception e) {

			if(manager.isOpen()) {
				transaction.rollback();				
			}

			return Response.ok().entity(gson.toJson(e.getMessage()))
					.build();
			
		} finally {
			if(manager.isOpen()) {
				manager.close();
			}
		}
		
	}

	@DELETE
	@Path("delete/{id}")
	public Response delete(@PathParam(value="id") Long id) {

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		EntityManager manager = JPAUtil.getManager();
		EntityTransaction transaction = manager.getTransaction();

		transaction.begin();

		try {
			
			productoDao.delete(new Long(id), manager);
			transaction.commit();
			
			return Response.ok().entity(gson.toJson("La producto fue eliminada exitosamente"))			
					.build();
					
		} catch (Exception e) {

			if(manager.isOpen()) {
				transaction.rollback();				
			}

			e.getStackTrace();
			
			return Response.ok().entity(gson.toJson(e.getMessage()))				
					.build();

		} finally {
			if(manager.isOpen()) {
				manager.close();
			}
		}
		
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("edit")
	public Response edit(Producto producto) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		
		EntityManager manager = JPAUtil.getManager();
		EntityTransaction transaction = manager.getTransaction();

		transaction.begin();

		try {
			
			productoDao.merge(producto, manager);
			transaction.commit();
			
			return Response.ok().entity(gson.toJson("el producto fue modificada exitosamente"))				
					.build();

		} catch (Exception e) {

			if(manager.isOpen()) {
				transaction.rollback();				
			}

			return Response.ok().entity(gson.toJson(e.getMessage()))
					.build();
			
		} finally {
			if(manager.isOpen()) {
				manager.close();
			}
		}
	}
}
