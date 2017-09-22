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

import java.io.File;

import org.devzendo.commoncode.string.StringUtils;


/**
 * Abstraction for working with the directory that holds preference storage,
 * this implementation stores the preferences under the user's home directory.
 * 
 * @author matt
 *
 */
public final class DefaultPrefsLocation implements PrefsLocation {
    private File mAbsolutePrefsDir;
    private File mAbsolutePrefsFile;
    private String mUserHome;
    private final String mPrefsDir;
    private final String mPrefsFile;

    /**
     * Initialise a PrefsLocation with the standard user home directory.
     * 
     * @param prefsDir the directory under the home where the prefs file is to
     * be stored. Note that this should be a relative path, not absolute.
     * 
     * @param prefsFile the name of the prefs file.
     */
    public DefaultPrefsLocation(final String prefsDir, final String prefsFile) {
        if (new File(prefsDir).isAbsolute()) {
            throw new IllegalArgumentException("Preferences directory '" + prefsDir + "' must not be absolute");
        }
        mPrefsDir = prefsDir;
        mPrefsFile = prefsFile;
        mUserHome = System.getProperty("user.home");
        initialise();
    }

    /**
     * Initialise a PrefsLocation with a specific directory for the user home.
     * Note that this should be a relative path, not absolute.
     * This variant of the constructor is used for unit testing (although since
     * there is the PrefsLocation interface, a mock could be used)
     * 
     * @param prefsDir the directory under the home where the prefs file is to be stored.
     * @param prefsFile the name of the prefs file.
     * @param home the home directory to use
     */
    public DefaultPrefsLocation(final String prefsDir, final String prefsFile, final String home) {
        this(prefsDir, prefsFile);
        mUserHome = home;
        initialise();
    }
    
    private void initialise() {
        mAbsolutePrefsDir = new File(StringUtils.slashTerminate(mUserHome) + mPrefsDir);
        mAbsolutePrefsFile = new File(StringUtils.slashTerminate(mAbsolutePrefsDir.getAbsolutePath()) + mPrefsFile);
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean prefsDirectoryExists() {
        return mAbsolutePrefsDir.exists();
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean createPrefsDirectory() {
        return mAbsolutePrefsDir.mkdir();
    }

    /**
     * {@inheritDoc}
     */
    public File getPrefsDir() {
        return mAbsolutePrefsDir;
    }

    /**
     * {@inheritDoc}
     */
    public File getPrefsFile() {
        return mAbsolutePrefsFile;
    }
}
