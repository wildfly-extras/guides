/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.examples;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/")
public class GettingStartedEndpoint {

    @Inject
    JsonWebToken jwt;

    @Inject
    private GettingStartedService service;

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello(final @PathParam("name") String name) {
        String response = service.hello(name) + " JWT2:" + (jwt!=null? jwt.getSubject():null);

        return Response.ok(response).build();
    }
}
