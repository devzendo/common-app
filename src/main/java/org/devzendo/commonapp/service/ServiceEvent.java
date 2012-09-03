package org.devzendo.commonapp.service;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.devzendo.commoncode.patterns.observer.ObservableEvent;

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
public class ServiceEvent implements ObservableEvent {
    private final ServiceEventType eventType;
    private final String serviceName;
    private final String description;

    public ServiceEvent(final ServiceEventType eventType, final String serviceName, final String description) {
        this.eventType = eventType;
        this.serviceName = serviceName;
        this.description = description;
    }

    public ServiceEventType getEventType() {
        return eventType;
    }

    public String getDescription() {
        return description;
    }

    public String getServiceName() {
        return serviceName;
    }

    @Override
    public String toString() {
        return eventType + ": " + serviceName + " - '" + description + "'";
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1, 31).
                append(eventType).
                append(serviceName).
                append(description).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ServiceEvent other = (ServiceEvent) obj;

        return new EqualsBuilder().
                append(this.eventType, other.eventType).
                append(this.serviceName, other.serviceName).
                append(this.description, other.description).
                isEquals();
    }

}
