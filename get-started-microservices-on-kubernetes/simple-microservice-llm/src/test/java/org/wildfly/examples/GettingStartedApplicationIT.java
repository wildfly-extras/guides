/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Disabled;

/**
 * Run integration tests against the server and the deployed application.
 */
@RunAsClient
@ExtendWith(ArquillianExtension.class)
public class GettingStartedApplicationIT {

    @Disabled("Run manually after starting the Ollama docker image and having pulled the llama3 as described in the README.md file")
    @Test
    public void testHelloEndpoint() {
        System.setProperty("OLLAMA_CHAT_URL", "http://127.0.0.1:11434");
        System.setProperty("OLLAMA_CHAT_LOG_REQUEST", "true");
        System.setProperty("OLLAMA_CHAT_LOG_RESPONSE", "true");
        System.setProperty("OLLAMA_CHAT_TEMPERATURE", "0.9");
        System.setProperty("OLLAMA_CHAT_MODEL_NAME", "llama3");

        try (Client client = ClientBuilder.newClient()) {
            Response response = client
                    .target(URI.create("http://localhost:8080/"))
                    .path("/api/Tommaso")
                    .request()
                    .get();

            assertEquals(200, response.getStatus());
            assertTrue(response.readEntity(String.class).contains("Tommaso"));
        }
    }
}
