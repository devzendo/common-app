package uk.me.gumbley.minimiser.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * A ListCellRenderer that renders a subtle stripe effect.
 * @author matt
 *
 */
public class StripyListCellRenderer extends DefaultListCellRenderer implements ListCellRenderer  {
    private static final long serialVersionUID = -471403224429769232L;

    /**
     * {@inheritDoc}
     */
    public Component getListCellRendererComponent(
            final JList list,
            final Object value,
            final int index,
            final boolean isSelected,
            final boolean cellHasFocus) {
        final Component ret = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if ((index & 0x01) == 0x01) {
            setBackground(slightlyDarker(getBackground()));
        }
        return ret;
    }
    
    private static final double FACTOR = 0.92;

    private Color slightlyDarker(final Color color) {
        return new Color(Math.max((int) (color.getRed() * FACTOR), 0), 
                 Math.max((int) (color.getGreen() * FACTOR), 0),
                 Math.max((int) (color.getBlue() * FACTOR), 0));
    }
}
