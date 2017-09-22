package org.devzendo.commonapp.service;

import org.devzendo.commonapp.util.OrderMonitor;

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
public class OrderService implements Service {
    private final OrderMonitor monitor;
    private final String name;

    public OrderService(final OrderMonitor monitor, final String name) {
        this.monitor = monitor;
        this.name = name;
    }

    public void startup(final ServiceManagerProxy proxy) {
        monitor.add(name + " startup");
    }

    public void prepareForShutdown() {
        monitor.add(name + " prepareForShutdown");
    }

    public void shutdown() {
        monitor.add(name + " shutdown");
    }
}
