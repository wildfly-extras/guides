/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.examples.mdb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

import java.util.logging.Logger;

@MessageDriven(
		name = "GettingStartedQueueMDB",
		activationConfig = {
				@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/gettingStartedQueue"),
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
				@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")}
)
public class GettingStartedQueueMDB implements MessageListener {

	private static final Logger LOGGER = Logger.getLogger(GettingStartedQueueMDB.class.toString());

	public void onMessage(Message rcvMessage) {
		TextMessage msg = null;
		try {
			if (rcvMessage instanceof TextMessage) {
				msg = (TextMessage) rcvMessage;
				LOGGER.info("Received Message from queue: " + msg.getText());
			} else {
				LOGGER.warning("Message of wrong type: " + rcvMessage.getClass().getName());
			}
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
}