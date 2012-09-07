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

import java.util.List;

/**
 * The ServiceManager controls the lifecycle of services:
 * <ul>
 * <li> starting and stopping them as directed, in the declared order</li>
 * <li> notifying listeners of starting/started/faulty, active/inactive, stopping/stopped events</li>
 * <li> allowing callers to query the status of services</li>
 * </ul>
 */
public interface ServiceManager {
    /**
     * Start up all Services in the order declared in the application context.
     */
    void startup();

    /**
     * Prepare all Services for shutdown then shut them all down, in reverse
     * order as declared in the application context.
     */
    void shutdown();

    /**
     * Add a listener to service events. This will be called when services
     * change their state.
     * @param listener the listener to add.
     */
    void addServiceListener(ServiceListener listener);

    /**
     * Remove a service event listener. This listener will no longer be
     * notified of service events.
     * @param listener the listener to remove.
     */
    void removeServiceListener(ServiceListener listener);

    /**
     * Obtain the current statuses of all declared services.
     * @return a list of service status descriptions.
     */
    List<ServiceStatus> getStatuses();

    /**
     * Obtain the current status of a named service.
     * @param serviceName the name of the service bean as declared in the
     *                    application context.
     * @return the service status or null if this service is not known.
     */
    ServiceStatus getStatus(String serviceName);

    /**
     * Obtain a Service by name
     * @param serviceName the name of the Service
     * @param serviceClass the subclass of Service you expect
     * @param <S> the Service subclass type parameter
     * @return the Service instance, or null if the Service is unknown.
     */
    <S extends Service> S getService(String serviceName, Class<S> serviceClass);
}
