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

package org.devzendo.minimiser.prefs;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;

/**
 * A Spring FactoryBean for the Prefs Singleton
 * @author matt
 *
 */
public final class PrefsFactory implements FactoryBean<Prefs> {
    private static final Logger LOGGER = Logger.getLogger(PrefsFactory.class);
    private Prefs factoryPrefs;

    /**
     * {@inheritDoc}
     */
    public Prefs getObject() throws Exception {
        LOGGER.debug(String.format("PrefsFactory returning %s as prefs object", factoryPrefs));
        return factoryPrefs;
    }

    /**
     * {@inheritDoc}
     */
    public Class<?> getObjectType() {
        return DefaultPrefsImpl.class;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isSingleton() {
        return true;
    }
    
    /**
     * Factory population method
     * @param prefsPath the path of the prefs to return as a Singleton
     */
    public void setPrefs(final String prefsPath) {
        LOGGER.debug(String.format("PrefsFactory being populated with %s as prefs object", prefsPath));
        factoryPrefs = new DefaultPrefsImpl(prefsPath);
    }
    

    /**
     * Factory population method, used by unit tests
     * @param prefs a previously instantiated Prefs object
     */
    public void setPrefs(final Prefs prefs) {
        LOGGER.debug(String.format("PrefsFactory being populated with %s as prefs object", prefs.getClass().getSimpleName()));
        factoryPrefs = prefs;
    }
}
