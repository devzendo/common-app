package org.devzendo.commonapp.service;

import org.apache.log4j.Logger;

/**
 * Copyright (C) 2008-2012 Matt Gumbley, DevZendo.org http://devzendo.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class StubService implements Service {
    private static final Logger LOGGER = Logger.getLogger(StubService.class);

    private String state = "new-init";
    private boolean prepareShutdownCalled = false;
    private final String name;

    public StubService(final String name) {
        this.name = name;
        LOGGER.info("Constructed StubService '" + name + "'");
        state = "ctor";
    }

    public void shutdown() {
        state = "shut down";
    }

    public void startup(final ServiceManagerProxy proxy) {
        state = "started";
    }

    public void prepareForShutdown() {
        prepareShutdownCalled = true;
    }

    public String getState() {
        return state;
    }

    public boolean wasPrepareShutdownCalled() {
        return prepareShutdownCalled;
    }
}
