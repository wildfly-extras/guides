/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.examples;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.wildfly.security.http.oidc.OidcSecurityContext;

import java.io.IOException;

@Path("/")
public class GettingStartedEndpoint {

    @Context
    private HttpServletRequest httpServletRequest;

    @Inject
    @RestClient
    private GettingStartedEndpoint1Interface service1;

    @Inject
    @RestClient
    private GettingStartedEndpoint2Interface service2;

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello(final @PathParam("name") String name) throws IOException {
        Response response;
        OidcSecurityContext oidcSecurityContext = getOidcSecurityContext(httpServletRequest);
        if (oidcSecurityContext != null) {
            String authzHeaderValue = "Bearer " + oidcSecurityContext.getTokenString();
            System.out.println("\n\n[JWT] service2 Token: " + authzHeaderValue + "\n\n");
            return service2.sayHello(authzHeaderValue, name);
        } else {
            System.out.println("\n\n[JWT] No token :(\n\n");
            return service2.sayHello(null, name);
        }
    }

    private OidcSecurityContext getOidcSecurityContext(HttpServletRequest req) {
        return (OidcSecurityContext) req.getAttribute(OidcSecurityContext.class.getName());
    }
}
