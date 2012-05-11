/**
 * Copyright (C) 2008-2010 Matt Gumbley, DevZendo.org <http://devzendo.org>
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


/**
 * @author matt
 *
 */
public final class PrepareShutdownLifecycle implements Lifecycle, ShutdownPreparable {
    private boolean prepared = false;
    private int count = 0;
    private int ctorCount;
    private int shutdownCount;
    private int startupCount;
    private int prepareForShutdownCount;

    public PrepareShutdownLifecycle() {
        ctorCount = count++;
    }

    public void shutdown() {
        shutdownCount = count++;
    }

    public void startup() {
        startupCount = count++;
    }
    
    public void prepareForShutdown() {
        prepared = true;
        prepareForShutdownCount = count++;
    }

    public boolean isPrepared() {
        return prepared;
    }

    public int getCtorCount() {
        return ctorCount;
    }

    public int getShutdownCount() {
        return shutdownCount;
    }

    public int getStartupCount() {
        return startupCount;
    }

    public int getPrepareForShutdownCount() {
        return prepareForShutdownCount;
    }
}
