package uk.me.gumbley.minimiser.gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import uk.me.gumbley.commoncode.os.OSTypeDetect;
import uk.me.gumbley.commoncode.os.OSTypeDetect.OSType;
import uk.me.gumbley.minimiser.pluginmanager.AppDetails;

import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;

/**
 * Encapsulates the setting of the look and feel.
 * 
 * @author matt
 *
 */
public final class Beautifier {
    private static final Logger LOGGER = Logger.getLogger(Beautifier.class);
    private Beautifier() {
        // nop
    }
    /**
     * Make the UI more beautiful. Unless we're on a Mac, in which
     * case we're already beautiful.
     * @param appDetails the application details
     */
    public static void makeBeautiful(final AppDetails appDetails) {
        if (OSTypeDetect.getInstance().getOSType() == OSType.MacOSX) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            // TODO get the app name from the application plugin
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", appDetails.getApplicationName());
        } else {
            try {
                UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
            } catch (final UnsupportedLookAndFeelException e) {
                LOGGER.warn("Plastic XP look and feel is not supported: " + e.getMessage());
            }
        }
    }
}
