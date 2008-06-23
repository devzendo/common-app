package uk.me.gumbley.minimiser.gui;

import java.awt.Component;
import java.awt.Cursor;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import org.apache.log4j.Logger;
import uk.me.gumbley.commoncode.gui.GUIUtils;

/**
 * Handlings make benefit great user interaction of hourglass/normal cursor.
 * @author borat
 *
 */
public final class CursorManager {
    private static final Logger LOGGER = Logger.getLogger(CursorManager.class);
    private static final Cursor HOURGLASS = new Cursor(Cursor.WAIT_CURSOR);
    private static final Cursor NORMAL = new Cursor(Cursor.DEFAULT_CURSOR);
    private JFrame frame = null;

    /**
     * Instantiate the CursorManager, which won't be able to effect change
     * of the cursor until a main component has been set.
     */
    public CursorManager() {
    }
    
    /**
     * Set the application's main frame.
     * @param mainFrame the main application's frame
     */
    public void setMainFrame(final JFrame mainFrame) {
        frame = mainFrame;
        LOGGER.debug("CursorManager's main frame has been set to " + mainFrame);
    }
    
    /**
     * Set the hourglass cursor, if a main component has been set.
     */
    public void hourglass() {
        LOGGER.debug("Setting hourglass cursor");
        if (frame != null) {
            frame.setCursor(HOURGLASS);
        }
        final Component glassPane = getGlassPane();
        if (glassPane != null) {
            glassPane.setEnabled(false);
            glassPane.setVisible(true);
        }
    }
    
    /**
     * Set the hourglass cursor, if a main component has been set. This
     * always runs on the event thread. If you're sure you're already on
     * the event thread, use hourglass().
     */
    public void hourglassViaEventThread() {
        final Runnable r = new Runnable() {
            public void run() {
                hourglass();
            }
        };
        GUIUtils.runOnEventThread(r);
    }

    /**
     * Set the normal cursor, if the main component has been set. 
     */
    public void normal() {
        LOGGER.debug("Setting normal cursor");
        if (frame != null) {
            frame.setCursor(NORMAL);
        }
        final Component glassPane = getGlassPane();
        if (glassPane != null) {
            glassPane.setVisible(false);
            glassPane.setEnabled(true);
            glassPane.setCursor(NORMAL);
        }
    }

    /**
     * Set the normla cursor, if a main component has been set. This
     * always runs on the event thread. If you're sure you're already on
     * the event thread, use normal().
     */
    public void normalViaEventThread() {
        final Runnable r = new Runnable() {
            public void run() {
                normal();
            }
        };
        GUIUtils.runOnEventThread(r);
    }

    private Component getGlassPane() {
        if (frame == null) {
            LOGGER.warn("Frame is null");
            return null;
        }
        final JRootPane rootPane = frame.getRootPane();
        if (rootPane == null) {
            LOGGER.warn("JRootPane is null");
            return null;
        }
        final Component glassPane = rootPane.getGlassPane();
        if (glassPane == null) {
            LOGGER.warn("GlassPane is null");
        }
        return glassPane;
    }
}
