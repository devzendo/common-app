package org.devzendo.commonapp.service;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
public final class ServiceState {
    public static final ServiceState SERVICE_BEFORESTARTUP = new ServiceState("BeforeStartup");
    public static final ServiceState SERVICE_STARTING = new ServiceState("Starting");
    public static final ServiceState SERVICE_STARTED = new ServiceState("Started");
    public static final ServiceState SERVICE_FAULTY = new ServiceState("Faulty");
    public static final ServiceState SERVICE_INACTIVE = new ServiceState("Inactive");
    public static final ServiceState SERVICE_ACTIVE = new ServiceState("Active");
    public static final ServiceState SERVICE_STOPPING = new ServiceState("Stopping");
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