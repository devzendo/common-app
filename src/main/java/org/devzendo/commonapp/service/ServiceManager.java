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
 * <li> starting and stopping them as directed</li>
 * <li> ordering the startup and shutdown according to their declared dependencies</li>
 * <li> notifying listeners of start and stop events</li>
 * <li> allowing callers to query the status of services, start them (with their
 * dependencies), and stop them (stopping their dependencies first)</li>
 * </ul>
 */
public interface ServiceManager {
    /**
     * Start up all Services.
     */
    void startup();

    /**
     * Prepare all Services for shutdown then shut them all down.
     */
    void shutdown();

    void addServiceListener(ServiceListener listener);

    void removeServiceListener(ServiceListener listener);

    List<ServiceEvent> getStatuses();

    ServiceEvent getStatus(String serviceName);
}
