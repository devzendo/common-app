package org.devzendo.minimiser.gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import uk.me.gumbley.commoncode.os.OSTypeDetect;
import uk.me.gumbley.commoncode.os.OSTypeDetect.OSType;

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
     * Make the UI more beautiful. Unless we're on a Mac, in which case we're
     * already beautiful.
     * 
     */
    public static void makeBeautiful() {
        if (OSTypeDetect.getInstance().getOSType() == OSType.MacOSX) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            
            // set system properties here that affect Quaqua
            // for example the default layout policy for tabbed
            // panes:
            System.setProperty(
               "Quaqua.tabLayoutPolicy", "wrap"
            );

            // set the Quaqua Look and Feel in the UIManager
            try {
                 UIManager.setLookAndFeel(
                     "ch.randelshofer.quaqua.QuaquaLookAndFeel"
                 );
            // set UI manager properties here that affect Quaqua
            } catch (final Exception e) {
                // take an appropriate action here
            }
        } else {
            try {
                UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
            } catch (final UnsupportedLookAndFeelException e) {
                LOGGER.warn("Plastic XP look and feel is not supported: " + e.getMessage());
            }
        }
    }
}
