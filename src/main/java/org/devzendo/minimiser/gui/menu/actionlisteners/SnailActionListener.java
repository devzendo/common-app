package org.devzendo.minimiser.gui.menu.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.devzendo.minimiser.gui.CursorManager;

import uk.me.gumbley.commoncode.gui.GUIUtils;

/**
 * An ActionListener base class that can be attached to say a MenuItem, such
 * that when the user selects the menu item, the event thread isn't so tied up
 * with doing the menu item's code so that it can't redraw. The effect is that
 * the menu gets "stuck" on screen for a short while.
 * So, start an hourglass, and spawn a separate thread to let the EDT catch up.
 * 
 * @author matt
 *
 */
public abstract class SnailActionListener implements ActionListener {
    private final CursorManager cursorManager;

    /**
     * Create a SnailActionListener
     * @param cursor the cursor manager
     */
    public SnailActionListener(final CursorManager cursor) {
        this.cursorManager = cursor;
    }
    
    /**
     * {@inheritDoc}
     */
    public final void actionPerformed(final ActionEvent e) {
        cursorManager.hourglass(this.getClass().getSimpleName());
        new Thread(new Runnable() {
            public void run() {
                GUIUtils.invokeLaterOnEventThread(new Runnable() {
                    public void run() {
                        actionPerformedSlowly(e);
                        actionFinished();
                    }
                });
            }
        }).start();
    }
    
    /**
     * Perform the action.
     * @param e the ActionEvent to process.
     */
    public abstract void actionPerformedSlowly(final ActionEvent e);

    private void actionFinished() {
        cursorManager.normal(this.getClass().getSimpleName());
    }
    
    /**
     * @return the cursor manager
     */
    protected final CursorManager getCursorManager() {
        return cursorManager;
    }
}
