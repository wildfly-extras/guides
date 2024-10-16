/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.examples;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/")
public class GettingStartedEndpoint {

	@Inject
	@ConfigProperty(name = "mp.jwt.verify.publickey.location")
	private String publicKeyLocation;

	@Inject
	JsonWebToken jwt;

	@GET
	@Path("/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	@RolesAllowed({"admin"})
	public Response sayHello(final @PathParam("name") String name) {
		System.out.println("mp.jwt.verify.publickey.location=" + publicKeyLocation);

		String response =
				"Hello " + name
						+ (jwt != null ? (" Subject:" + jwt.getSubject()) : null)
						+ (jwt != null ? (" Issuer: " + jwt.getIssuer()) : null);

		return Response.ok(response).build();
	}
}
