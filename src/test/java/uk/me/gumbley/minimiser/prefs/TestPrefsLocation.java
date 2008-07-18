package uk.me.gumbley.minimiser.prefs;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import uk.me.gumbley.minimiser.logging.LoggingTestCase;


/**
 * Test PrefsLocation
 * @author matt
 *
 */
public final class TestPrefsLocation extends LoggingTestCase {
    private static final Logger LOGGER = Logger
            .getLogger(TestPrefsLocation.class);
    /**
     * 
     */
    @Test
    public void testNonexistantPrefsLocation() {
        final PrefsLocation prefsLocation = new PrefsLocation("/this/directory/does/not/exist");
        Assert.assertFalse(prefsLocation.prefsDirectoryExists());
    }
    
    /**
     * 
     */
    @Test
    public void testPrefsDirCreationFails() {
        final PrefsLocation prefsLocation = new PrefsLocation("/this/directory/does/not/exist");
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
        final PrefsLocation prefsLocation = new PrefsLocation(tempDir);
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
