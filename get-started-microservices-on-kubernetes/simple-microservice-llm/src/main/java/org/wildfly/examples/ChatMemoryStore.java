/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.examples;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import jakarta.enterprise.context.SessionScoped;

import java.io.Serializable;

@SessionScoped
public class ChatMemoryStore implements Serializable {
    ChatMemory memory = MessageWindowChatMemory.withMaxMessages(5);

    public ChatMemory getMemory() {
		return memory;
	}
}
