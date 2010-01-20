package org.devzendo.minimiser.gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import uk.me.gumbley.commoncode.gui.GUIUtils;

/**
 * A ListCellRenderer that renders a subtle stripe effect.
 * @author matt
 *
 */
public final class StripyListCellRenderer extends DefaultListCellRenderer implements ListCellRenderer  {
    private static final long serialVersionUID = -471403224429769232L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Component getListCellRendererComponent(
            final JList list,
            final Object value,
            final int index,
            final boolean isSelected,
            final boolean cellHasFocus) {
        final Component ret = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if ((index & 0x01) == 0x01) {
            setBackground(GUIUtils.slightlyDarkerColor(getBackground()));
        }
        return ret;
    }
}
