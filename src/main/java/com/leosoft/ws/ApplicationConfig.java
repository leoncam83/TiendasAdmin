package com.leosoft.ws;

import java.util.Set;

import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new java.util.HashSet<>();
		addRestResourceClasses(resources);
		return super.getClasses();
	}

	private void addRestResourceClasses(Set<Class<?>> resources) {
		resources.add(TiendaRest.class);
		resources.add(ProductoRest.class);
		
	}
	
}
