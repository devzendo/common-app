package uk.me.gumbley.minimiser.gui.menu;

/**
 * An AbstractRebuildableMenuGroup is a menu group that can be rebuilt. These
 * are used for menus that add/remove MenuItems, such as the View menu (when
 * certain tabs are enabled/disabled), the Window menu (when databases are
 * added/removed), and the File menu (when databases are opened, the Recent
 * files list needs updating).
 * 
 * @author matt
 */
public abstract class AbstractRebuildableMenuGroup extends AbstractMenuGroup {
    /**
     * Construct given the main menu, which subclasses will probably use to
     * cause updates to other parts of the menu system.
     * 
     * @param wiring the menu wiring
     */
    public AbstractRebuildableMenuGroup(final MenuWiring wiring) {
        super(wiring);
    }
    
    /**
     * Some event has happened, an update to the menu group's model, perhaps,
     * which means it has to be rebuilt.
     */
    public abstract void rebuildMenuGroup();
}
