/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.examples;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

@Path("/")
@RequestScoped
public class GettingStartedEndpoint {
    private static final Logger LOGGER = Logger.getLogger(GettingStartedEndpoint.class);

    /**
     * In <b>standalone.xml</b> you will have:
     * <xml>
     *  <chat-language-models>
     *    <ollama-chat-model name="ollama"
     *      base-url="${org.wildfly.ai.ollama.chat.url,env.OLLAMA_CHAT_URL:http://127.0.0.1:11434}"
     *      connect-timeout="600000"
     *      log-requests="${org.wildfly.ai.ollama.chat.log.request,env.OLLAMA_CHAT_LOG_REQUEST:true}"
     *      log-responses="${org.wildfly.ai.ollama.chat.log.response,env.OLLAMA_CHAT_LOG_RESPONSE:true}"
     *      model-name="${org.wildfly.ai.ollama.chat.model.name,env.OLLAMA_CHAT_MODEL_NAME:llama3.1:8b}"
     *      temperature="${org.wildfly.ai.ollama.chat.temperature,env.OLLAMA_CHAT_TEMPERATURE:0.9}"/>
     *   </chat-language-models>
     * </xml>
     */
    @Inject
    @Named(value = "ollama")
    ChatModel model;

    @Inject
    ChatMemoryStore chatMemoryStore;

    /**
     * Asks the remote LLM to greet the person with "name" given by the parameter
     * @param name name of the person to greet
     * @return some greeting message from the LLM
     */
    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello(final @PathParam("name") String name) {


        UserMessage message1 = UserMessage.from("Hi! my name is " + name);
        chatMemoryStore.getMemory().add(message1);
        AiMessage response1 = model.chat(chatMemoryStore.getMemory().messages()).aiMessage();
        chatMemoryStore.getMemory().add(response1);

        LOGGER.info(response1);

        return Response.ok(response1).build();
    }

    /**
     * Asks the remote LLM what the name of the person it just set a greeting to is
     * @return hopefully, if the memory mechanism works, the name of the person the LLM just greeted
     */
    @GET
    @Path("/get-previous-name")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getName() {

        UserMessage message1 = UserMessage.from("Hi! what my name is?");
        chatMemoryStore.getMemory().add(message1);
        AiMessage response1 = model.chat(chatMemoryStore.getMemory().messages()).aiMessage();
        chatMemoryStore.getMemory().add(response1);

        LOGGER.info(response1);

        return Response.ok(response1).build();
    }
}
