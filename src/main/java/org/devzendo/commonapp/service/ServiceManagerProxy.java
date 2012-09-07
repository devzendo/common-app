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

/**
 * The mechanism via which a Service propagates a change in its state to the
 * ServiceManager (from where it will be sent out to all connected listeners).
 *
 */
public interface ServiceManagerProxy {
    /**
     * This Service is inactive, probably waiting for other services or system
     * facilities to become available.
     * @param description a human-readable description of the reason for
     *                    inactivity.
     */
    void inactive(String description);

    /**
     * This Service is active, available to do work (whatever that means;
     * however such work requests are received by the Service is not defined
     * in the Service management subsystem).
     * @param description a human-readable description of the reason for
     *                    activity.
     */
    void active(String description);

    /**
     * The Service is not changing state, just propogating a change of
     * description
     * @param description the updated description.
     */
    void changeDescription(String description);
}
