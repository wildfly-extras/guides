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
    @ConfigProperty(name="mp.jwt.verify.issuer")
    private String issuer;

    @Inject
    @ConfigProperty(name="mp.jwt.verify.publickey.location")
    private String publickeyLocation;

    @Inject
    JsonWebToken jwt;

    @Inject
    private GettingStartedService service;

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({"user"})
    public Response sayHello(final @PathParam("name") String name) {
        System.out.println("mp.jwt.verify.issuer=" + issuer);
        System.out.println("mp.jwt.verify.publickey.location=" + publickeyLocation);

        String response =
                service.hello(name)
                        + " Subject:" + (jwt!=null? jwt.getSubject():null)
                        + " Issuer: " + jwt.getIssuer();

        return Response.ok(response).build();
    }
}
