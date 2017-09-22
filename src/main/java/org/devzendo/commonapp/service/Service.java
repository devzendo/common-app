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
package org.devzendo.commonapp.service;

/**
 * A Service is an application service whose lifecycle is managed by the
 * ServiceManager. It exists in one of several states, and typically is active,
 * having its own thread, listening to other system state in order to direct its
 * activities, and switching into the active/inactive state as necessary.
 *
 * The constructor of the Service (as declared in the application context)
 * should take any system objects to which the Service connects, and wire up
 * listeners etc. A Service's main thread should be created, and possibly
 * started (this can also happen in startup). No other activity should take
 * place there.
 *
 * The mechanism via which Services receive requests for processing is not
 * part of the Service interface.
 */
public interface Service {

    /**
     * Start up the Service. Start threads, etc.
     * As part of the startup, all Services should indicate whether the Service
     * is active/inactive via the serviceManagerProxy.
     *
     * @param serviceManagerProxy the interface to the ServiceManager, by which
     *                            a Service can indicate changes in its
     *                            active/inactive state, and set its current
     *                            description.
     */
    void startup(ServiceManagerProxy serviceManagerProxy);

    /**
     * Prepare to shut down the component. This method is called on all Services
     * before all Services are shut down.
     */
    void prepareForShutdown();

    /**
     * Shut down the Service.
     */
    void shutdown();
}
