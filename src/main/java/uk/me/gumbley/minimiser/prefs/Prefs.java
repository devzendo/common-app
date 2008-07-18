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
}