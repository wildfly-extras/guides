/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.wildfly.guide.testing.resources;

import java.util.concurrent.atomic.AtomicReference;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseBroadcaster;

/**
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
@ApplicationScoped
public class Producers {

    private final AtomicReference<SseBroadcaster> broadcaster = new AtomicReference<>();

    @Inject
    private Sse sse;

    /**
     * Creates the broadcaster used for the application via injection.
     *
     * @return the broadcaster to use
     */
    @Produces
    @ApplicationScoped
    public SseBroadcaster broadcaster() {
        return broadcaster.updateAndGet(sseBroadcaster -> {
            SseBroadcaster result = sseBroadcaster;
            if (result == null) {
                result = sse.newBroadcaster();
            }
            return result;
        });
    }
}
