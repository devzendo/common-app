package uk.me.gumbley.minimiser.gui;

import java.awt.Rectangle;
import javax.swing.JFrame;
import org.apache.log4j.Logger;
import uk.me.gumbley.minimiser.prefs.Prefs;

/**
 * Stores the geometry of Windows in the Prefs, and allows
 * them to be restored on creation.
 * 
 * @author matt
 *
 */
public final class WindowGeometryStore {
    private static final Logger LOGGER = Logger
            .getLogger(WindowGeometryStore.class);
    private final Prefs prefs;

    /**
     * Create the WindowGeometryStore
     * @param preferences the preferences used for storage
     */
    public WindowGeometryStore(final Prefs preferences) {
        this.prefs = preferences;
    }

    /**
     * Does this frame have any geometry stored? Use this on
     * creating your frame. If there is no stored geometry,
     * you may want to set a default, or pack();
     * @param frame the frame to check for geometry storage.
     * @return true if this frame has had its geometry stored.
     */
    public boolean hasStoredGeometry(final JFrame frame) {
        final String name = frame.getName();
        LOGGER.debug("Trying to load stored geometry for JFrame '" + name + "'");
        final String geomStr = prefs.getWindowGeometry(name);
        return (!geomStr.equals(""));
    }
    
    /**
     * Load and set the geometry for this frame, if there is any stored.
     * @param frame the frame whose geometry should be loaded and set.
     */
    public void loadGeometry(final JFrame frame) {
        final String name = frame.getName();
        LOGGER.debug("Trying to load stored geometry for JFrame '" + name + "'");
        final String geomStr = prefs.getWindowGeometry(name);
        if (geomStr.equals("")) {
            LOGGER.debug("No geometry stored for JFrame '" + name + "'");
            return;
        }
        LOGGER.debug("Setting starting geometry is " + geomStr);
        // x,y,width,height
        final String[] geomNumStrs = geomStr.split(",");
        final int[] geomNums = new int[geomNumStrs.length];
        for (int i = 0; i < geomNumStrs.length; i++) {
            geomNums[i] = Integer.parseInt(geomNumStrs[i]);
        }
        frame.setBounds(geomNums[0], geomNums[1], geomNums[2], geomNums[3]);
        LOGGER.debug("Starting geometry set for window '" + name + "'");
    }

    /**
     * Store this frame's geometry for later restoration.
     * Typically called when the frame is about to close.
     * @param frame the frame whose geometry will be stored.
     */
    public void saveGeometry(final JFrame frame) {
        final String windowName = frame.getName();
        LOGGER.debug("should we store window " + windowName);
        final Rectangle rect = frame.getBounds();
        final String geomStr = String.format("%d,%d,%d,%d",
            rect.x, rect.y, rect.width, rect.height);
        LOGGER.debug("Storing window '" + windowName + "' geometry " + geomStr);
        prefs.setWindowGeometry(windowName, geomStr);
    }
}
