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

package org.devzendo.commonapp.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Detector for ordering of calls.
 * 
 * @author matt
 *
 */
public final class OrderMonitor {
    private static final Logger LOGGER = Logger.getLogger(OrderMonitor.class);
    private List<String> ordering;
    private Object lock;
    /**
     * Make an order monitor
     */
    public OrderMonitor() {
        lock = new Object();
        synchronized (lock) {
            ordering = new ArrayList<String>();
        }
    }

    /**
     * @param string a string to add to the ordering
     */
    public void add(final String string) {
        synchronized (lock) {
            LOGGER.info("Adding '" + string + "'");
            ordering.add(string);
        }
    }

    /**
     * @return a list of the current orderings
     */
    public List<String> getOrdering() {
        synchronized (lock) {
            LOGGER.debug("The ordering list contains " + ordering.size() + " entries");
            return new ArrayList<String>(ordering);
        }
    }
    
    /**
     * Reset the list of orderings
     */
    public void reset() {
        synchronized (lock) {
            LOGGER.info("Resetting");
            ordering.clear();
        }
    }
}
