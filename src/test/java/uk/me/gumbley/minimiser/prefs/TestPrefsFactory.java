package uk.me.gumbley.minimiser.prefs;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import uk.me.gumbley.minimiser.springloader.ApplicationContext;
import uk.me.gumbley.minimiser.springloader.SpringLoaderUnittestCase;

/**
 * Test the Prefs FactoryBean
 * @author matt
 *
 */
@ApplicationContext("uk/me/gumbley/minimiser/prefs/PrefsTestCase.xml")
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
        
        IPrefs prefs = getPrefs();
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
        
        IPrefs prefs1 = getPrefs();
        IPrefs prefs2 = getPrefs();
        Assert.assertSame(prefs1, prefs2);
    }

    private IPrefs getPrefs() {
        return getSpringLoader().getBean("prefs", Prefs.class);
    }

    private PrefsFactory getPrefsFactory() {
        return getSpringLoader().getBean("&prefs", PrefsFactory.class);
    }
}
