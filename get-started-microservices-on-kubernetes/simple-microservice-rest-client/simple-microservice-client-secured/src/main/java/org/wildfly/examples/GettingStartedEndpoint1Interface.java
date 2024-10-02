package org.wildfly.examples;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterClientHeaders
@RegisterRestClient(configKey="simple-microservice-server1")
@Path("/")
public interface GettingStartedEndpoint1Interface {
	@GET
	@Path("/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayHello(@HeaderParam("Authorization") String authorization, final @PathParam("name") String name);
}
