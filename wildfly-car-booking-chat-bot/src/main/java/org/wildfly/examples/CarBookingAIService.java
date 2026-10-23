package org.wildfly.examples;

import java.time.temporal.ChronoUnit;

import dev.langchain4j.cdi.spi.RegisterAIService;
import dev.langchain4j.service.SystemMessage;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@RegisterAIService(
        chatModelName = "chat-model",
        chatMemoryName = "chat-memory",
        tools = CarBookingService.class,
        contentRetrieverName = "doc-retriever"
)
public interface CarBookingAIService {

    @SystemMessage("""
            You are a customer support agent of a car rental company named 'Miles of Smiles'.
            Before providing information about booking or canceling a booking, you MUST always check:
            booking number, customer name and surname.
            
            You should not answer to any request not related to car booking or Miles of Smiles company general information.
            When a customer wants to cancel a booking, you must check his name and the Miles of Smiles cancellation policy first.
            
            Any cancellation request must comply with cancellation policy both for the delay and the duration.
            
            Today is {{current_date}}.
            """)
    @Timeout(unit = ChronoUnit.MINUTES, value = 5)
    @Retry(maxRetries = 2)
    @Fallback(fallbackMethod = "fallback")
    String chat(String question);

    default String fallback(String question) {
        return "Sorry, I'm having trouble processing your request at the moment. Please try again later.";
    }
}