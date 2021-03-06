package org.devzendo.commonapp.service;

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
public class InactiveThenActiveService extends WaitForEndOfActivityService implements Service {

    public void startup(final ServiceManagerProxy serviceManagerProxy) {
        final Thread thread = new Thread(new Runnable() {
            public void run() {
                serviceManagerProxy.inactive("Short wait");
                try {
                    Thread.sleep(500);
                } catch (final InterruptedException e) {
                    // noop
                }
                serviceManagerProxy.active("Finally started");
                countDown();
            }
        });
        thread.start();
    }

    public void prepareForShutdown() {
    }

    public void shutdown() {
    }
}
