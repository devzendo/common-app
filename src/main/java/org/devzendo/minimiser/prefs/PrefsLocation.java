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
    private File absolutePrefsDir;
    private File absolutePrefsFile;
    private String userHome;
    private final String mPrefsDir;
    private final String mPrefsFile;

    /**
     * Initialise a PrefsLocation with the standard user home directory.
     * 
     * @param prefsDir the directory under the home where the prefs file is to be stored.
     * @param prefsFile the name of the prefs file.
     */
    public PrefsLocation(final String prefsDir, final String prefsFile) {
        mPrefsDir = prefsDir;
        mPrefsFile = prefsFile;
        userHome = System.getProperty("user.home");
        initialise();
    }

    private void initialise() {
        absolutePrefsDir = new File(StringUtils.slashTerminate(userHome) + mPrefsDir);
        absolutePrefsFile = new File(StringUtils.slashTerminate(absolutePrefsDir.getAbsolutePath()) + mPrefsFile);
    }
    
    /**
     * Initialise a PrefsLocation with a specific directory for the user home.
     * This variant of the constructor is used for unit testing.
     * 
     * @param prefsDir the directory under the home where the prefs file is to be stored.
     * @param prefsFile the name of the prefs file.
     * @param home the home directory to use
     */
    public PrefsLocation(final String prefsDir, final String prefsFile, final String home) {
        this(prefsDir, prefsFile);
        userHome = home;
        initialise();
    }
    
    /**
     * Does the directory containing the preferences file exist?
     * @return true if it exists, false if it doesn't.
     */
    public boolean prefsDirectoryExists() {
        return absolutePrefsDir.exists();
    }
    
    /**
     * Create the prefs directory.
     * @return true iff the directory was created; false if it was not. Note
     * that if it existed anyway, you'd get false.
     * @see File.mkdir
     */
    public boolean createPrefsDirectory() {
        return absolutePrefsDir.mkdir();
    }

    /**
     * @return the prefsDir
     */
    public File getPrefsDir() {
        return absolutePrefsDir;
    }

    /**
     * @return the prefsFile
     */
    public File getPrefsFile() {
        return absolutePrefsFile;
    }
}
