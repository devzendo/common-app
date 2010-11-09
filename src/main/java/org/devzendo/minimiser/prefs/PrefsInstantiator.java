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

/**
 * Instatiates the Prefs for a given application. Can be changed for different
 * applications.
 * 
 * @author matt
 *
 */
public interface PrefsInstantiator {
    /**
     * @param prefsLocation the location of the Prefs for this application.
     * @return a freshly-instantiated Prefs object for this applciation.
     */
    MiniMiserPrefs instantiatePrefs(PrefsLocation prefsLocation);
    
    /**
     * @return the class used by this application for storing prefs; to be
     * stored in the PrefsFactory.
     */
    Class<? extends MiniMiserPrefs> getPrefsClass();
}
