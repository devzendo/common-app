package uk.me.gumbley.minimiser.prefs;

import java.io.File;
import uk.me.gumbley.commoncode.string.StringUtils;

/**
 * Utility for working with the directory that holds preference storage.
 * @author matt
 *
 */
public final class PrefsLocation {
    private static final String MINIMISER_PREFS = "minimiser.prefs";
    private static final String DOT_MINIMISER = ".minimiser";
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
        prefsDir = new File(StringUtils.slashTerminate(userHome) + DOT_MINIMISER);
        prefsFile = new File(StringUtils.slashTerminate(prefsDir.getAbsolutePath()) + MINIMISER_PREFS);
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
