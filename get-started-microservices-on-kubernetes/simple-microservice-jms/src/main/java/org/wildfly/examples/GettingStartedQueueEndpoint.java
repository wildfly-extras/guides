/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.examples;

import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSDestinationDefinition;
import jakarta.jms.JMSDestinationDefinitions;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
@JMSDestinationDefinitions(
		value = {
				@JMSDestinationDefinition(
						name = "java:/queue/gettingStartedQueue",
						interfaceName = "jakarta.jms.Queue",
						destinationName = "getting-started-queue",
						properties = {"enable-amq1-prefix=false"}
				)
		}
)
@Path("/message")
public class GettingStartedQueueEndpoint {

	@Resource(lookup="java:/queue/gettingStartedQueue")
	private Queue queue;

	@Inject
	private JMSContext context;

	@GET
	@Path("/send")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sendMessage(final @QueryParam("content") String content) throws JMSException {
		String response = "Sent " + content + " to " + queue.getQueueName();
		context.createProducer().send(queue, content);
		return Response.ok(response).build();
	}
}