package uk.me.gumbley.minimiser.gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.log4j.Logger;
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
     * Make the UI more beautiful.
     */
    public static void makeBeautiful() {
        try {
            UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
        } catch (final UnsupportedLookAndFeelException e) {
            LOGGER.warn("Plastic XP look and feel is not supported: " + e.getMessage());
        }
    }


}
