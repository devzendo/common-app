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

import java.io.File;

import org.devzendo.commoncode.string.StringUtils;


/**
 * Utility for working with the directory that holds preference storage.
 * @author matt
 *
 */
public final class PrefsLocation {
    private static final String PREFS_FILE = "minimiser.prefs";
    private static final String PREFS_DIRECTORY = ".minimiser";
    private File prefsDir;
    private File prefsFile;
    private String userHome;

    /**
     * Initialise a PrefsLocation with the standard user home directory.
     */
    public PrefsLocation() {
        userHome = System.getProperty("user.home");
        initialise();
    }

    private void initialise() {
        prefsDir = new File(StringUtils.slashTerminate(userHome) + PREFS_DIRECTORY);
        prefsFile = new File(StringUtils.slashTerminate(prefsDir.getAbsolutePath()) + PREFS_FILE);
    }
    
    /**
     * Initialise a PrefsLocation with a specific directory for the user home.
     * This variant of the constructor is used for unit testing.
     * 
     * @param home the home directory to use
     */
    public PrefsLocation(final String home) {
        userHome = home;
        initialise();
    }
    
    /**
     * Does the directory containing the preferences file exist?
     * @return true if it exists, false if it doesn't.
     */
    public boolean prefsDirectoryExists() {
        return prefsDir.exists();
    }
    
    /**
     * Create the prefs directory.
     * @return true iff the directory was created; false if it was not. Note
     * that if it existed anyway, you'd get false.
     * @see File.mkdir
     */
    public boolean createPrefsDirectory() {
        return prefsDir.mkdir();
    }

    /**
     * @return the prefsDir
     */
    public File getPrefsDir() {
        return prefsDir;
    }

    /**
     * @return the prefsFile
     */
    public File getPrefsFile() {
        return prefsFile;
    }
}
