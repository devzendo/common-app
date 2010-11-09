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

import org.devzendo.commonspring.springloader.ApplicationContext;
import org.devzendo.commonspring.springloader.SpringLoaderUnittestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test the Prefs FactoryBean
 * @author matt
 *
 */
@ApplicationContext("org/devzendo/minimiser/prefs/PrefsTestCase.xml")
public final class TestPrefsFactory extends SpringLoaderUnittestCase {

    /**
     * @throws IOException on failure
     *
     */
    @Test
    public void testStoreTemporaryPrefs() throws IOException {
        final File tempFile = File.createTempFile("minimiser-unit-test", "prefs").getAbsoluteFile();
        tempFile.deleteOnExit();

        final PrefsFactory prefsFactory = getPrefsFactory();
        Assert.assertNotNull(prefsFactory);
        prefsFactory.setPrefs(tempFile.getAbsolutePath());

        final MiniMiserPrefs prefs = getPrefs();
        Assert.assertTrue(prefs instanceof DefaultMiniMiserPrefsImpl);
        Assert.assertNotNull(prefs);

        Assert.assertEquals(tempFile.getAbsolutePath(), prefs.getAbsolutePath());
    }

    /**
     * @throws IOException on failure
     */
    @Test
    public void itsASingleton() throws IOException {
        final File tempFile = File.createTempFile("minimiser-unit-test", "prefs").getAbsoluteFile();
        tempFile.deleteOnExit();
        getPrefsFactory().setPrefs(tempFile.getAbsolutePath());

        final MiniMiserPrefs prefs1 = getPrefs();
        final MiniMiserPrefs prefs2 = getPrefs();
        Assert.assertSame(prefs1, prefs2);
    }

    private MiniMiserPrefs getPrefs() {
        return getSpringLoader().getBean("prefs", DefaultMiniMiserPrefsImpl.class);
    }

    private PrefsFactory getPrefsFactory() {
        return getSpringLoader().getBean("&prefs", PrefsFactory.class);
    }
}
