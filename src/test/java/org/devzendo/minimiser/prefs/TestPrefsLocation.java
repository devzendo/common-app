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
import java.io.IOException;

import org.apache.log4j.Logger;
import org.devzendo.commoncode.logging.LoggingUnittestHelper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Test PrefsLocation
 * @author matt
 *
 */
public final class TestPrefsLocation  {
    private static final Logger LOGGER = Logger
            .getLogger(TestPrefsLocation.class);
    private static final String PREFS_FILE = "minimiser.prefs";
    private static final String PREFS_DIRECTORY = ".minimiser";
    /**
     * 
     */
    @BeforeClass
    public static void setupLogging() {
        LoggingUnittestHelper.setupLogging();
    }

    /**
     * 
     */
    @Test
    public void testNonexistantPrefsLocation() {
        final PrefsLocation prefsLocation = new PrefsLocation(PREFS_DIRECTORY, PREFS_FILE, "/this/directory/does/not/exist");
        Assert.assertFalse(prefsLocation.prefsDirectoryExists());
    }
    
    /**
     * 
     */
    @Test
    public void testPrefsDirCreationFails() {
        final PrefsLocation prefsLocation = new PrefsLocation(PREFS_DIRECTORY, PREFS_FILE,"/this/directory/does/not/exist");
        Assert.assertFalse(prefsLocation.createPrefsDirectory());
    }
    
    /**
     * @throws IOException on failure
     */
    @Test
    public void testPrefsDirCreationOk() throws IOException {
        final File tempDirFile = File.createTempFile("minimiser-unit-test-dir", "dir");
        tempDirFile.deleteOnExit();
        final String tempDir = tempDirFile.getParent();
        LOGGER.info("Temporary directory is " + tempDir);
        final PrefsLocation prefsLocation = new PrefsLocation(PREFS_DIRECTORY, PREFS_FILE, tempDir);
        Assert.assertFalse(prefsLocation.prefsDirectoryExists());
        final File prefsDir = prefsLocation.getPrefsDir();
        final File prefsFile = prefsLocation.getPrefsFile();
        LOGGER.info("Temp prefs dir is " + prefsDir.getAbsolutePath());
        LOGGER.info("Temp prefs file is " + prefsFile.getAbsolutePath());
        Assert.assertFalse(prefsDir.exists());
        Assert.assertFalse(prefsFile.exists());
        try {
            Assert.assertTrue(prefsLocation.createPrefsDirectory());
            Assert.assertTrue(prefsDir.exists());
        } finally {
            Assert.assertTrue(prefsDir.delete());
            Assert.assertFalse(prefsDir.exists());
        }
    }
}
