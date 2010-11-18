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
import org.devzendo.commoncode.file.INIFile;

/**
 * A simple prefs for unit tests.
 * @author matt
 *
 */
public final class SamplePrefsImpl implements Prefs, SamplePrefs {
    private static final Logger LOGGER = Logger.getLogger(SamplePrefsImpl.class);
    private final String prefsFilePath;
    private final INIFile iniFile;

    /**
     * Create a Prefs object backed by a file
     * @param prefsFile the file path
     */
    public SamplePrefsImpl(final String prefsFile) {
        prefsFilePath = prefsFile;
        LOGGER.info("Creating default prefs file at " + prefsFile);
        iniFile = new INIFile(prefsFilePath);
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasFunkiness() {
        return iniFile.getBooleanValue("style", "funkiness");
    }
    
}
