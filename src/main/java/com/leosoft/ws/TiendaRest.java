/**
 * 
 */
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
import com.leosoft.dao.TiendaDao;
import com.leosoft.dao.TiendaDaoImpl;
import com.leosoft.entities.Tienda;
import com.leosoft.util.jpa.JPAUtil;

/**
 * @author leonc
 *
 */
@Path("tienda")
public class TiendaRest {

	TiendaDao tiendaDao = new TiendaDaoImpl();  

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("findall")
	public Response findAll() {

		EntityManager manager = JPAUtil.getManager();

		List<Tienda> tiendas = null;

		try {
			tiendas = tiendaDao.findAll(manager, new String[]{"nombre"}, new Boolean[]{true});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager.isOpen()) {
				manager.close();
			}
		}

		return Response.ok().entity(new GenericEntity<List<Tienda>>(tiendas) {
		}).build();

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
			
			tiendaDao.delete(new Long(id), manager);
			transaction.commit();
			
			return Response.ok().entity(gson.toJson("La tienda fue eliminada exitosamente"))			
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

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("create")
	public Response create(Tienda tienda) {
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		
		EntityManager manager = JPAUtil.getManager();
		EntityTransaction transaction = manager.getTransaction();

		transaction.begin();

		try {
			
			tiendaDao.persist(tienda, manager);
			transaction.commit();
			
			return Response.ok().entity(gson.toJson("La tienda fue registrada exitosamente"))				
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

	@PUT
	@Path("edit")
	public Response edit(Tienda tienda) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		
		EntityManager manager = JPAUtil.getManager();
		EntityTransaction transaction = manager.getTransaction();

		transaction.begin();

		try {
			
			tiendaDao.merge(tienda, manager);
			transaction.commit();
			
			return Response.ok().entity(gson.toJson("La tienda fue modificada exitosamente"))				
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
