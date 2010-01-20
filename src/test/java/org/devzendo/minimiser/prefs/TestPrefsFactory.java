package org.devzendo.minimiser.prefs;

import java.io.File;
import java.io.IOException;

import org.devzendo.minimiser.springloader.ApplicationContext;
import org.devzendo.minimiser.springloader.SpringLoaderUnittestCase;
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

        final Prefs prefs = getPrefs();
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

        final Prefs prefs1 = getPrefs();
        final Prefs prefs2 = getPrefs();
        Assert.assertSame(prefs1, prefs2);
    }

    private Prefs getPrefs() {
        return getSpringLoader().getBean("prefs", DefaultPrefsImpl.class);
    }

    private PrefsFactory getPrefsFactory() {
        return getSpringLoader().getBean("&prefs", PrefsFactory.class);
    }
}
