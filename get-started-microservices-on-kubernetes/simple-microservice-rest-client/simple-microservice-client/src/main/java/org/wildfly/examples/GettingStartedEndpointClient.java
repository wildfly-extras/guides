/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.examples;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey="simple-microservice-server")
@Path("/hello")
public interface GettingStartedEndpointClient {
	@GET
	@Path("/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	Response sayHello(@PathParam("name") String name);
}
