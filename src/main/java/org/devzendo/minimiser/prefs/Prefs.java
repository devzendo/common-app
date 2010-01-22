package org.devzendo.minimiser.prefs;

import org.devzendo.commoncode.patterns.observer.Observer;

/**
 * Storage API for user preferences. Encapsulates the storage mechanism.
 * 
 * @author matt
 *
 */
public interface Prefs {
    /**
     * The various sections that the Prefs storage is split into. Changes of
     * preference settings in any of these sections can be listened for.
     * 
     * Although all sections are listed here, not all sections will have
     * changes propagated; consult the JavaDoc for each section's mutator
     * methods to see if a change would be propagated. 
     *
     */
    enum PrefsSection {
        /**
         * Window geometry 
         */
        GEOMETRY,
        
        /**
         * The recorded size of the wizard panels, computed on first start,
         * to improve subsequent startup speed.
         */
        WIZARD_PANEL_SIZE,
        
        /**
         * The list of recently-opened files.
         */
        RECENT_FILES,
        
        /**
         * The list of open files, that'll be re-opened on startup.
         */
        OPEN_FILES,
        
        /**
         * The last active file, that'll be switched to on startup.
         */
        LAST_ACTIVE_FILE,
        
        /**
         * For each open database, which tabs are open. 
         */
        OPEN_TABS,
        
        /**
         * For each open database, the tab that'll be switched to on startup. 
         */
        LAST_ACTIVE_TAB,
        
        /**
         * Which tabs are hidden, e.g. developer diagnostic tabs like SQL tab.
         */
        HIDDEN_TABS,
        
        /**
         * Various boolean flags
         */
        BOOLEAN_FLAGS,
    }
    
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
     * Obtain the currently active tab for a given database
     * @param databaseName the database with an active tab
     * @return the text of the tab that's the active one
     */
    String getActiveTab(String databaseName);


    /**
     * Set the active tab for a given database
     * @param databaseName the database with an active tab
     * @param tabName the name of the active tab
     */
    void setActiveTab(String databaseName, String tabName);

    /**
     * Is the named tab hidden?
     * @param tabName the name of the tab, taken from TabIdentifier::toString,
     * not TabIdentifier::getDisplayableName
     * @return true iff the tab is hidden 
     */
    boolean isTabHidden(String tabName);

    /**
     * Indicate that the named tab is hidden.
     * @param tabName the name of the tab, taken from TabIdentifier::toString,
     * not TabIdentifier::getDisplayableName
     */
    void setTabHidden(String tabName);

    /**
     * Indicate that the named tab is shown, i.e. not hidden.
     * @param tabName the name of the tab, taken from TabIdentifier::toString,
     * not TabIdentifier::getDisplayableName
     */
    void clearTabHidden(String tabName);

    /**
     * Add an observer of prefs change events
     * @param observer the observer
     */
    void addChangeListener(Observer<PrefsEvent> observer);

    /**
     * Obtain the current software version
     * @return the currently stored software version, or null if none has yet
     * been stored.
     */
    String getCurrentSoftwareVersion();

    /**
     * Store the current software version
     * @param version the new version to be stored
     */
    void setCurrentSoftwareVersion(String version);

    /**
     * Has the user indicated that they don't want to see a particular message
     * again?
     * @param messageId The key relating to the message
     * @return true iff they do not want to see the message again
     */
    boolean isDontShowThisAgainFlagSet(String messageId);

    /**
     * The user doesn't want to see this message again. Remember it.
     * @param messageId The key relating to the message
     */
    void setDontShowThisAgainFlag(String messageId);

    /**
     * The user has indicated that the <em>do</em> want to see this message
     * again. Remember their choice. 
     * @param messageId The key relating to the message
     */
    void clearDontShowThisAgainFlag(String messageId);

    /**
     * The user wants to see all messages they'd poreviously said 'no' to.
     */
    void clearAllDontShowThisAgainFlags();

    /**
     * Is a specific boolean flag set?
     * @param flagName the name of the flag to check
     * @return true iff the flag is set
     */
    boolean isBooleanFlagSet(BooleanFlag flagName);

    /**
     * Store the value of a specific boolean flag.
     * @param flagName the name of the flag to set
     * @param allowed true or false
     */
    void setBooleanFlag(BooleanFlag flagName, boolean allowed);

    /**
     * When was the last check made for availability of updates?
     * @return a String in the form DD/MM/YYYY, or the empty string if the
     * check has never been made.
     */
    String getDateOfLastUpdateAvailableCheck();

    /**
     * Set the date of the last check for availablility of updates, in a UK
     * Locale MEDIUM format, e.g. Dec 8, 2008 - this isn't displayed, it's just used in
     * a string comparison to see whether the last update happened today or
     * not.
     * @param ukFormatDateString the date in a UK MEDIUM format e.g. Dec 8, 2008
     */
    void setDateOfLastUpdateAvailableCheck(String ukFormatDateString);

    /**
     * What is the last-retrieved version of the software from the update site?
     * @return the version number, or the empty string if this has not been
     * retrieved before.
     */
    String getLastRemoteUpdateVersion();

    /**
     * Set the version of the software as found on the remote update site 
     * @param version the software version
     */
    void setLastRemoteUpdateVersion(String version);
}