/**
 * Copyright (C) 2008-2012 Matt Gumbley, DevZendo.org <http://devzendo.org>
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.devzendo.commonapp.service;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.devzendo.commoncode.patterns.observer.ObservableEvent;

/**
 * The current status of a Service, can be obtained via the ServiceManager, or
 * emitted to ServiceListeners upon ServiceState change.
 */
public class ServiceStatus implements ObservableEvent {
    private final ServiceState state;
    private final String serviceName;
    private final String description;
    private final Exception fault;

    public ServiceStatus(final ServiceState state, final String serviceName, final String description) {
        this(state, serviceName, description, null);
    }

    public ServiceStatus(final ServiceState state, final String serviceName, final String description, final Exception fault) {
        this.state = state;
        this.serviceName = serviceName;
        this.description = description;
        this.fault = fault;
    }

    /**
     * Obtain the current state of the Service.
     * @return the state the Service is in
     */
    public ServiceState getState() {
        return state;
    }

    /**
     * Obtain the human-readable description of the current Service.
     * @return what it's feeling
     */
    public String getDescription() {
        return description;
    }

    /**
     * Obtain the name of the Service, as declared in the application context.
     * @return the Service bean name
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * If the ServiceState obtained by getState is SERVICE_FAULTY, this gives
     * the Exception that has rendered the Service faulty on startup.
     * @return the Exception causing the Service to be faulty.
     */
    public Exception getFault() {
        return fault;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(state);
        sb.append(": ");
        sb.append(serviceName);
        sb.append(" - '");
        sb.append(description);
        sb.append("'");
        if (fault != null) {
            sb.append(" [");
            sb.append(fault.getMessage());
            sb.append("]");
        }

        return sb.toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1, 31).
                append(state).
                append(serviceName).
                append(description).
                append(fault).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ServiceStatus other = (ServiceStatus) obj;

        return new EqualsBuilder().
                append(this.state, other.state).
                append(this.serviceName, other.serviceName).
                append(this.description, other.description).
                append(this.fault, other.fault).
                isEquals();
    }
}
