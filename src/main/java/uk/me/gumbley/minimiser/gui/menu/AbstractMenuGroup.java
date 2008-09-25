package uk.me.gumbley.minimiser.gui.menu;

import javax.swing.JMenu;

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
    private final MenuImpl mainMenu;

    /**
     * Construct given the main menu, which subclasses will probably use to
     * cause updates to other parts of the menu system.
     * @param wiring the menu wiring
     * @param state the menu state
     * @param menu the main menu
     */
    public AbstractMenuGroup(final MenuWiring wiring, final MenuState state, final MenuImpl menu) {
        this.menuWiring = wiring;
        this.menuState = state;
        this.mainMenu = menu;
        
    }

    /**
     * Get the main menu
     * @return the main menu
     */
    protected final MenuImpl getMainMenu() {
        return mainMenu;
    }
    
    /**
     * Get the menu state
     * @return the menu state
     */
    protected final MenuState getMenuState() {
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
}
