package uk.me.gumbley.minimiser.gui.mm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenuItem;

import uk.me.gumbley.minimiser.gui.mm.Menu.MenuIdentifier;

/**
 * Provides wiring between MenuItems (referenced via MenuIdentifiers) and their
 * ActionListeners.
 * 
 * @author matt
 *
 */
public class MenuWiring {

    /**
     * A stash for JMenuItems and their ActionListeners
     * 
     * @author matt
     *
     */
    private class MenuDetails {
        private final JMenuItem stashMenuItem;
        private ActionListener stashActionListener;

        /**
         * Create a MenuDetails for a given JMenuItem, with no ActionListener
         * until one is set.
         * 
         * @param jMenuItem the JMenuItem for this stash 
         */
        public MenuDetails(final JMenuItem menuItem) {
            this.stashMenuItem = menuItem;
            this.stashActionListener = null;
        }

        /**
         * @return the actionListener
         */
        public ActionListener getActionListener() {
            return stashActionListener;
        }

        /**
         * @param actionListener the actionListener to set
         */
        public void setActionListener(final ActionListener actionListener) {
            this.stashActionListener = actionListener;
        }

        /**
         * @return the menuItem
         */
        public JMenuItem getMenuItem() {
            return stashMenuItem;
        }
    }
    private Map<MenuIdentifier, MenuDetails> menuDetailsMap;

    /**
     * Create some MenuWiring
     */
    public MenuWiring() {
        menuDetailsMap = new HashMap<MenuIdentifier, MenuDetails>();
    }

    /**
     * Obtain the ActionListener for a given MenuIdentifier.
     * 
     * @param menuIdentifier the MenuIdentifier to search for an ActionListener
     * @return the ActionListener, if one has been set, or null if not set.
     */
    public ActionListener getActionListener(final MenuIdentifier menuIdentifier) {
        synchronized (menuDetailsMap) {
            MenuDetails menuDetails = menuDetailsMap.get(menuIdentifier);
            if (menuDetails == null) {
                return null;
            }
            return menuDetails.getActionListener();
        }
    }

    /**
     * Obtain the JMenuItem for a given MenuIdentifier
     * @param menuIdentifier the MenuIdentifier to search for a JMenuItem
     * @return the JMenuItem, if one has been set for this MenuIdentifier, or
     * null if not set.
     */
    public JMenuItem getMenuItem(final MenuIdentifier menuIdentifier) {
        synchronized (menuDetailsMap) {
            MenuDetails menuDetails = menuDetailsMap.get(menuIdentifier);
            if (menuDetails == null) {
                return null;
            }
            return menuDetails.getMenuItem();
        }
    }
    
    /**
     * Generate a direct ActionListener for a given MenuIdentifier which will,
     * at runtime, lookup the indirect ActionListener for the MenuIdentifier,
     * and if set, call it.
     *  
     * @param menuIdentifier the MenuIdentifier to generate a direct
     * ActionListener for.
     * @return a direct ActionListener.
     */
    private ActionListener generateDirectActionListener(final MenuIdentifier menuIdentifier) {
        // adding a level of temporal indirection here so that listeners
        // can be wired up after the menu has been created
        return new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                synchronized (menuDetailsMap) {
                    MenuDetails menuDetails = menuDetailsMap.get(menuIdentifier);
                    if (menuDetails == null) {
                        return;
                    }
                    ActionListener actionListener = menuDetails.getActionListener();
                    if (actionListener != null) {
                        actionListener.actionPerformed(e);
                    }
                }
            }
        };
    }
    
    /**
     * Create the mapping between a JMenuItem and its MenuIdentifier, and set
     * the JMenuItem's direct ActionListener.
     * 
     * @param menuIdentifier the MenuIdentifier to create a mapping for
     * @param menuItem the JMenuItem to map
     */
    public void storeMenuItem(final MenuIdentifier menuIdentifier, final JMenuItem menuItem) {
        synchronized (menuDetailsMap) {
            menuDetailsMap.put(menuIdentifier, new MenuDetails(menuItem));
            menuItem.addActionListener(generateDirectActionListener(menuIdentifier));
        }
    }
    
    /**
     * Store an indirect ActionListener for a MenuIdentifier. The JMenuItem would have
     * an initial direct ActionListener bound - see storeMenuItem. This sets the 
     * indirect ActionListener - the one one that will be triggered by the direct
     * ActionListener
     * @param menuIdentifier the MenuIdentifier to set the indirect ActionListener for
     * @param actionListener the indirect ActionListener.
     */
    public void setActionListener(final MenuIdentifier menuIdentifier, final ActionListener actionListener) {
        synchronized (menuDetailsMap) {
            MenuDetails menuDetails = menuDetailsMap.get(menuIdentifier);
            if (menuDetails != null) {
                menuDetails.setActionListener(actionListener);
            }
        }
    }

    /**
     * Unit test method for dipatching an event to direct listeners.
     * @param menuIdentifier the MenuIdentifier to dispatch for
     * @param event the event to dispatch
     */
    void injectActionEvent(final MenuIdentifier menuIdentifier, final ActionEvent event) {
        synchronized (menuDetailsMap) {
            MenuDetails menuDetails = menuDetailsMap.get(menuIdentifier);
            if (menuDetails != null && menuDetails.getMenuItem() != null) {
                for (final ActionListener actionListener : menuDetails.getMenuItem().getActionListeners()) {
                    actionListener.actionPerformed(event);
                }
            }
        }
    }
}
