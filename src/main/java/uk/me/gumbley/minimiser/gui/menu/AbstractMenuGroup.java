package uk.me.gumbley.minimiser.gui.menu;

import javax.swing.JMenu;
import uk.me.gumbley.minimiser.gui.menu.Menu.MenuIdentifier;

/**
 * An AbstractMenuGroup is the base class for encapsulations of menus - that is,
 * typically vertical menu strips that offer a specific set of related
 * functions, such as the File, View, Window menus. They could encapsulate
 * subsets of a vertical menu, but usually, it'll be the whole menu.
 * 
 * @author matt
 *
 */
public abstract class AbstractMenuGroup {
    private final MenuWiring menuWiring;
    private final MenuState menuState;

    /**
     * Construct given the main menu, which subclasses will probably use to
     * cause updates to other parts of the menu system.
     * @param wiring the menu wiring
     * @param state the menu state
     */
    public AbstractMenuGroup(final MenuWiring wiring, final MenuState state) {
        this.menuWiring = wiring;
        this.menuState = state;
    }
    
    /**
     * Get the menu state
     * @return the menu state
     */
    protected final MenuState getXMenuState() {
        return menuState;
    }
    
    /**
     * Get the menu wiring
     * @return the menu wiring
     */
    protected MenuWiring getMenuWiring() {
        return menuWiring;
    }
    
    /**
     * If this menu group represents a full menu, it will be returned here.
     * @return the JMenu, or null, if this is not a full menu.
     */
    public abstract JMenu getJMenu();

    /**
     * Create a new JMenuItem, and wire it into the MenuWiring.
     * Called on the EDT
     * @param menuIdentifier the MenuIdentifier
     * @param menuItemText the text for this menu item
     * @param mnemonic the mnemonic
     * @param menu the menu to add it to.
     */
    public final void createMenuItem(final MenuIdentifier menuIdentifier,
            final String menuItemText, final char mnemonic, final JMenu menu) {
        
        getJMenu().add(
            menuWiring.createMenuItem(menuIdentifier, menuItemText, mnemonic));
    }

}
