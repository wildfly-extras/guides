package org.wildfly.examples;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/chat")
public class ChatBotResource {
    private static final Logger LOGGER = Logger.getLogger(ChatBotResource.class.getName());

    @Inject
    CarBookingAIService aiService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response chatWithAssistant(@QueryParam("question") String question) {
        try {
            return Response.ok(aiService.chat(question))
                    .build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while chatting with assistant for question: " + question, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("My failure reason is:\n\n" + e.getMessage())
                    .build();
        }
    }
}