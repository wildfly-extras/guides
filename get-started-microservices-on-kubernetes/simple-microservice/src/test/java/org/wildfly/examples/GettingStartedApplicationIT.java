/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Run integration tests against the server and the deployed application.
 */
@RunAsClient
@ExtendWith(ArquillianExtension.class)
public class GettingStartedApplicationIT {

    @Test
    public void testHelloEndpoint() {
        try (Client client = ClientBuilder.newClient()) {
            Response response = client
                    .target(URI.create("http://localhost:8080/"))
                    .path("/hello/World")
                    .request()
                    .get();

            assertEquals(200, response.getStatus());
            assertEquals("Hello 'World'.", response.readEntity(String.class));

        }
    }
}
