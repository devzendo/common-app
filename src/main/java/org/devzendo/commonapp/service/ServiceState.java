package org.devzendo.commonapp.service;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
public final class ServiceState {
    /**
     * The state a Service is in when the ServiceManager is constructed, before
     * it is started.
     */
    public static final ServiceState SERVICE_BEFORESTARTUP = new ServiceState("BeforeStartup");
    /**
     * Before a Service's startup method is called.
     */
    public static final ServiceState SERVICE_STARTING = new ServiceState("Starting");
    /**
     * After the Service's startup method has been called successfully.
     */
    public static final ServiceState SERVICE_STARTED = new ServiceState("Started");
    /**
     * After the Service's startup method has been called unsuccessfully, i.e.
     * a RuntimeException has been thrown in startup. The service management
     * subsystem currently defines no recovery mechanism for this state.
     * The RuntimeException thrown is sent with this state change.
     */
    public static final ServiceState SERVICE_FAULTY = new ServiceState("Faulty");
    /**
     * During startup, the Service declares itself inactive. This will be
     * emitted after the SERVICE_STARTED event.
     *
     * Can also be seen after startup at any time, as the Service detects it
     * cannot work for some reason.
     */
    public static final ServiceState SERVICE_INACTIVE = new ServiceState("Inactive");
    /**
     * During startup, the Service declares itself active. This will be
     * emitted after the SERVICE_STARTED event.
     *
     * Can also be seen after startup at any time, as the Service detects it
     * can now work as its necessities have been satisfied.
     */
    public static final ServiceState SERVICE_ACTIVE = new ServiceState("Active");
    /**
     * Sent after the Service's prepareForShutdown method is called, and before
     * its shutdown method is called.
     */
    public static final ServiceState SERVICE_STOPPING = new ServiceState("Stopping");
    /**
     * Sent after the Service's shutdown method is called. (Whether or not a
     * RuntimeException was thrown in shutdown.
     */
    public static final ServiceState SERVICE_STOPPED = new ServiceState("Stopped");

    private final String serviceEventName;

    private ServiceState(final String serviceEventName) {
        this.serviceEventName = serviceEventName;
    }

    @Override
    public String toString() {
        return serviceEventName;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1, 31).append(serviceEventName).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ServiceState other = (ServiceState) obj;

        final EqualsBuilder e = new EqualsBuilder();
        e.append(this.serviceEventName, other.serviceEventName);
        return e.isEquals();
    }
}
