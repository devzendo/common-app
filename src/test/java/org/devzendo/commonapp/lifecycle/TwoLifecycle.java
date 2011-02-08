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
public final class TwoLifecycle implements Lifecycle {
    private final OneLifecycle oneLifecycle;
    /**
     * @param one the one that's configured in
     */
    public TwoLifecycle(final OneLifecycle one) {
        this.oneLifecycle = one;
    }
    /**
     * {@inheritDoc}
     */
    public void shutdown() {
        oneLifecycle.shutdown();
    }

    /**
     * {@inheritDoc}
     */
    public void startup() {
        oneLifecycle.startup();
    }
    
    /**
     * @return the state
     */
    public String getState() {
        return oneLifecycle.getState();
    }
    /**
     * @return the one
     */
    public Object getOne() {
        return oneLifecycle;
    }
}
