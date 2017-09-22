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

/**
 * A component that can be notified of the impending lifecycle shutdown.
 *
 * @author matt
 *
 */
public interface ShutdownPreparable {
    
    /**
     * Prepare to shut down the component. All Lifecycles that implement this
     * interface will have this method called before any are shut down.
     */
    void prepareForShutdown();
}
