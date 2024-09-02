/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.examples;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;

@ApplicationScoped
public class GettingStartedService {

    @Inject
    private HttpServletRequest httpRequest;

    public String hello(String name) {
        String previousName = (String) httpRequest.getSession().getAttribute("NAME_PARAMETER");
        httpRequest.getSession().setAttribute("NAME_PARAMETER", name);
        return String.format("Hello '%s'." + (previousName == null ? "" : "(last time you were " + previousName + ")"), name);
    }
}