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
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/")
public class GettingStartedEndpoint {

    @Inject
    JsonWebToken jwt;

    @Inject
    @RestClient
    private GettingStartedEndpointInterface remoteService;

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello(final @PathParam("name") String name) {
        return remoteService.sayHello(name + " JWT1:" + (jwt!=null? jwt.getSubject():null));
    }
}