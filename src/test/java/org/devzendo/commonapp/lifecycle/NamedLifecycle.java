/**
 * Copyright (C) 2008-2010 Matt Gumbley, DevZendo.org http://devzendo.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.devzendo.commonapp.lifecycle;

import org.devzendo.commonapp.util.OrderMonitor;


/**
 * A lifecycle that has a name and records its startup and shutdown.
 * @author matt
 *
 */
public final class NamedLifecycle implements Lifecycle {
    private final String lifecycleName;
    private final OrderMonitor orderMonitor;

    /**
     * Construct a NamedLifecycle
     * @param monitor the OtrderMonitor to use for recording
     * @param name the name of this Lifecycle
     */
    public NamedLifecycle(final OrderMonitor monitor, final String name) {
        this.orderMonitor = monitor;
        this.lifecycleName = name;
        if (orderMonitor == null) {
            throw new IllegalArgumentException("no ordermonitor!");
        }
        if (lifecycleName == null) {
            throw new IllegalArgumentException("no name!");
        }
    }
    /**
     * {@inheritDoc}
     */
    public void shutdown() {
        orderMonitor.add(lifecycleName + " shutdown");
    }

    /**
     * {@inheritDoc}
     */
    public void startup() {
        orderMonitor.add(lifecycleName + " startup");
    }
}
