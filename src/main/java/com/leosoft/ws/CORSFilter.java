/**
 * 
 */
package com.leosoft.ws;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

/**
 * @author leonc
 *
 */
public class CORSFilter implements ContainerResponseFilter {

	/* (non-Javadoc)
	 * @see com.sun.jersey.spi.container.ContainerResponseFilter#filter(com.sun.jersey.spi.container.ContainerRequest, com.sun.jersey.spi.container.ContainerResponse)
	 */
	@Override
	public ContainerResponse filter(ContainerRequest arg0, ContainerResponse cresp) {
				
		cresp.getHttpHeaders().putSingle("Access-Control-Allow-Origin", "*");
        cresp.getHttpHeaders().putSingle("Access-Control-Allow-Credentials", "true");
        cresp.getHttpHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
        cresp.getHttpHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Accept");

        return cresp;
	}

}
