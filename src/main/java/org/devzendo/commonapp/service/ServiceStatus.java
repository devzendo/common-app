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
public class ServiceStatus {
    private final String serviceName;
    private final ServiceEventType serviceState;
    private final String description;
    private final Exception fault;

    public ServiceStatus(final String serviceName, final ServiceEventType serviceState, final String description, final Exception fault) {
        this.serviceName = serviceName;
        this.serviceState = serviceState;
        this.description = description;
        this.fault = fault;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(serviceName);
        sb.append(": ");
        sb.append(serviceState);
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
                append(serviceName).
                append(serviceState).
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
                append(this.serviceName, other.serviceName).
                append(this.serviceState, other.serviceState).
                append(this.description, other.description).
                append(this.fault, other.fault).
                isEquals();
    }
}
