package uk.me.gumbley.minimiser.prefs;

import uk.me.gumbley.commoncode.file.INIFile;

/**
 * Storage API for user preferences. Encapsulates the storage mechanism.
 * 
 * @author matt
 *
 */
public final class Prefs {
    private String prefsFilePath;
    private INIFile iniFile;

    // The section names and preference items
    private static final String UI_GEOMETRY = "geometry";
    private static final String SECTION_UI = "ui";

    /**
     * Create a Prefs object backed by a file
     * @param prefsFile the file path
     */
    public Prefs(final String prefsFile) {
        prefsFilePath = prefsFile;
        iniFile = new INIFile(prefsFilePath);
    }
    
    /**
     * Obtain the stored Window Geometry
     * @param windowName a window name
     * @return a String of the form x,y,width,height.
     */
    public String getWindowGeometry(final String windowName) {
        return iniFile.getValue(SECTION_UI, formWindowGeometryKey(windowName), "");
    }

    private String formWindowGeometryKey(final String windowName) {
        return UI_GEOMETRY + "_" + windowName;
    }

    /**
     * Store the Window Geometry
     * @param windowName a window name
     * @param geometry a String of the form x,y,width,height.
     */
    public void setWindowGeometry(final String windowName, final String geometry) {
        iniFile.setValue(SECTION_UI, formWindowGeometryKey(windowName), geometry);
    }

    /**
     * Where is the prefs file?
     * @return the absolute path of the prefs file
     */
    public String getAbsolutePath() {
        return prefsFilePath;
    }
    
    /**
     * {@inheritDoc}
     */
    public String toString() {
        return prefsFilePath;
    }
}
