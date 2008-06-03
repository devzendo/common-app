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
     * @return a String of the form x,y,width,height.
     */
    public String getWindowGeometry() {
        return iniFile.getValue(SECTION_UI, UI_GEOMETRY, "0,0,640,480");
    }

    /**
     * Store the Window Geometry
     * @param geometry a String of the form x,y,width,height.
     */
    public void setWindowGeometry(final String geometry) {
        iniFile.setValue(SECTION_UI, UI_GEOMETRY, geometry);
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
