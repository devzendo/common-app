package uk.me.gumbley.minimiser.prefs;

import uk.me.gumbley.commoncode.file.INIFile;

/**
 * Storage API for user preferences. Encapsulates the storage mechanism.
 * 
 * @author matt
 *
 */
public final class Prefs implements IPrefs {
    private String prefsFilePath;
    private INIFile iniFile;

    // The section names and preference items
    private static final String UI_GEOMETRY = "geometry";
    private static final String WIZARD_PANEL_SIZE = "wizard_panel_size";
    private static final String SECTION_UI = "ui";
    private static final String SECTION_WIZARD = "wizard";
    private static final String SECTION_RECENTFILES = "recentfiles";

    /**
     * Create a Prefs object backed by a file
     * @param prefsFile the file path
     */
    public Prefs(final String prefsFile) {
        prefsFilePath = prefsFile;
        iniFile = new INIFile(prefsFilePath);
    }
    
    /**
     * {@inheritDoc}
     */
    public String getWindowGeometry(final String windowName) {
        return iniFile.getValue(SECTION_UI, formWindowGeometryKey(windowName), "");
    }

    private String formWindowGeometryKey(final String windowName) {
        return UI_GEOMETRY + "_" + windowName;
    }

    /**
     * {@inheritDoc}
     */
    public void setWindowGeometry(final String windowName, final String geometry) {
        iniFile.setValue(SECTION_UI, formWindowGeometryKey(windowName), geometry);
    }

    /**
     * {@inheritDoc}
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

    /**
     * {@inheritDoc}
     */
    public String getWizardPanelSize() {
        return iniFile.getValue(SECTION_WIZARD, WIZARD_PANEL_SIZE, "");
    }

    /**
     * {@inheritDoc}
     */
    public void setWizardPanelSize(final String size) {
        iniFile.setValue(SECTION_WIZARD, WIZARD_PANEL_SIZE, size);
    }

    /**
     * {@inheritDoc}
     */
    public String[] getRecentFiles() {
        return iniFile.getArray(SECTION_RECENTFILES);
    }

    /**
     * {@inheritDoc}
     */
    public void setRecentFiles(final String[] paths) {
        iniFile.setArray(SECTION_RECENTFILES, paths);
    }
}
