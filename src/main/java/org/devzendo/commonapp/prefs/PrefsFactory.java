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

package org.devzendo.commonapp.prefs;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;

/**
 * A Spring FactoryBean for the Prefs Singleton
 * @author matt
 *
 */
public final class PrefsFactory implements FactoryBean<Prefs> {
    private static final Logger LOGGER = Logger.getLogger(PrefsFactory.class);
    private Prefs mFactoryPrefs;
    private Class<? extends Prefs> mPrefsClass;

    /**
     * {@inheritDoc}
     */
    public Prefs getObject() throws Exception {
        LOGGER.debug(String.format("PrefsFactory returning %s as prefs object", mFactoryPrefs));
        return mFactoryPrefs;
    }

    /**
     * {@inheritDoc}
     */
    public Class<?> getObjectType() {
        return mPrefsClass;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isSingleton() {
        return true;
    }

    /**
     * Factory population method
     * @param prefs a previously instantiated Prefs object
     */
    public void setPrefs(final Prefs prefs) {
        LOGGER.debug(String.format("PrefsFactory being populated with %s as prefs object", prefs.getClass().getSimpleName()));
        mFactoryPrefs = prefs;
    }

    /**
     * @param prefsClass the concrete prefs class returned by this factory
     */
    public void setPrefsClass(final Class<? extends Prefs> prefsClass) {
        LOGGER.debug(String.format("PrefsFactory being populated with %s as prefs class", prefsClass.getSimpleName()));
        mPrefsClass = prefsClass;
    }
}
