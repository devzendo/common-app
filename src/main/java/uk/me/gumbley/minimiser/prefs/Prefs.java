package uk.me.gumbley.minimiser.prefs;

/**
 * Storage API for user preferences. Encapsulates the storage mechanism.
 * 
 * @author matt
 *
 */
public interface Prefs {
    /**
     * Obtain the stored Window Geometry
     * @param windowName a window name
     * @return a String of the form x,y,width,height.
     */
    String getWindowGeometry(String windowName);

    /**
     * Store the Window Geometry
     * @param windowName a window name
     * @param geometry a String of the form x,y,width,height.
     */
    void setWindowGeometry(
            String windowName,
            String geometry);

    /**
     * Where is the prefs file?
     * @return the absolute path of the prefs file
     */
    String getAbsolutePath();

    /**
     * What is the size we want our wizard panels to be? It takes time to
     * compute this, so we do it once and store it.
     * @return A String in the form "width,height", or "" if it hasn't been
     * computed and stored yet.
     */
    String getWizardPanelSize();

    /**
     * Store the computed size of wizard panels.
     * @param size A String of the form "width,height", e.g. "200,300".
     */
    void setWizardPanelSize(String size);

    /**
     * Obtain the recent files list
     * @return an array of path names to databases
     */
    String[] getRecentFiles();

    /**
     * Store the recent files list
     * @param paths an array of path names to databases
     */
    void setRecentFiles(String[] paths);

    /**
     * Store the open files list
     * @param paths an array of path names to databases
     */
    void setOpenFiles(String[] paths);

    /**
     * Obtain the open files list
     * @return an array of path names to databases
     */
    String[] getOpenFiles();

    /**
     * Store the name of the last active database in the open list
     * @param databaseName the database name
     */
    void setLastActiveFile(final String databaseName);

    /**
     * Obtain the name of the last active database
     * @return the last active database's name
     */
    String getLastActiveFile();

    /**
     * There is now no last active file, so clear the record of it.
     */
    void clearLastActiveFile();

    /**
     * Get the list of open tab names for a given database
     * @param databaseName the database name
     * @return the list of open tabs' names, always an array, never null
     */
    String[] getOpenTabs(String databaseName);

    /**
     * Store the names of the open tabs for a given database
     * @param databaseName the database name
     * @param tabNames the names of the open tabs
     */
    void setOpenTabs(String databaseName, String[] tabNames);

    /**
     * Is the named tab hidden?
     * @param tabName the name of the tab, taken from TabIdentifier::toString,
     * not TabIdentifier::getDisplayableName
     * @return true iff the tab is hidden 
     */
    boolean isTabHidden(final String tabName);

    /**
     * Indicate that the named tab is hidden.
     * @param tabName the name of the tab, taken from TabIdentifier::toString,
     * not TabIdentifier::getDisplayableName
     */
    void setTabHidden(final String tabName);

    /**
     * Indicate that the named tab is shown, i.e. not hidden.
     * @param tabName the name of the tab, taken from TabIdentifier::toString,
     * not TabIdentifier::getDisplayableName
     */
    void clearTabHidden(final String tabName);
}