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
import java.util.logging.Logger;

@Path("/")
public class GettingStartedEndpoint {
    private static final Logger LOGGER = Logger.getLogger(GettingStartedEndpoint.class.toString());

    @Context
    private HttpServletRequest httpServletRequest;

    @Inject
    @RestClient
    private GettingStartedEndpointClient client;

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello(final @PathParam("name") String name) throws IOException {
        Response response;
        OidcSecurityContext oidcSecurityContext = getOidcSecurityContext(httpServletRequest);
        if (oidcSecurityContext != null) {
            String authzHeaderValue = "Bearer " + oidcSecurityContext.getTokenString();
            LOGGER.info(String.format("\n\n[JWT] service Token: %s\n\n", authzHeaderValue));
            return client.sayHello(authzHeaderValue, name);
        } else {
            LOGGER.info("\n\n[JWT] No token :(\n\n");
            return client.sayHello(null, name);
        }
    }

    private OidcSecurityContext getOidcSecurityContext(HttpServletRequest req) {
        return (OidcSecurityContext) req.getAttribute(OidcSecurityContext.class.getName());
    }
}
