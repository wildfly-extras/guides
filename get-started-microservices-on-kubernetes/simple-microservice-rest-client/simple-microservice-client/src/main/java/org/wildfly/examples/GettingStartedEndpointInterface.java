package org.wildfly.examples;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterClientHeaders
@RegisterRestClient(configKey="simple-microservice-server")
@Path("/")
public interface GettingStartedEndpointInterface {

	@GET
	@Path("/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	Response sayHello(final @PathParam("name") String name);
}
