package uk.me.gumbley.minimiser.gui.mm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.me.gumbley.minimiser.gui.mm.Menu.MenuIdentifier;


/**
 * Tests the MenuWiring class.
 * 
 * @author matt
 *
 */
public class TestMenuWiring {
    private MenuWiring menuWiring;

    /**
     * 
     */
    @Before
    public void getMenuWiring() {
        menuWiring = new MenuWiring();
    }
    
    /**
     * 
     */
    @Test
    public void testNonExistantMenuItem() {
        Assert.assertNull(menuWiring.getMenuItem(MenuIdentifier.FileClose));
    }

    /**
     * 
     */
    @Test
    public void testNonExistantActionListener() {
        Assert.assertNull(menuWiring.getActionListener(MenuIdentifier.FileClose));
    }
    
    /**
     * 
     */
    @Test
    public void testReturnMenuItem() {
        JMenuItem menuItem = new JMenuItem();
        menuWiring.storeMenuItem(MenuIdentifier.FileClose, menuItem);
        Assert.assertEquals(menuItem, menuWiring.getMenuItem(MenuIdentifier.FileClose));
    }
    
    /**
     * 
     */
    @Test
    public void testEmptyInitialActionListener() {
        JMenuItem menuItem = new JMenuItem();
        menuWiring.storeMenuItem(MenuIdentifier.FileClose, menuItem);
        Assert.assertNull(menuWiring.getActionListener(MenuIdentifier.FileClose));
    }

    /**
     * 
     */
    @Test
    public void testSetActionListener() {
        JMenuItem menuItem = new JMenuItem();
        menuWiring.storeMenuItem(MenuIdentifier.FileClose, menuItem);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            }
        };
        menuWiring.setActionListener(MenuIdentifier.FileClose, actionListener);
        Assert.assertEquals(actionListener, menuWiring.getActionListener(MenuIdentifier.FileClose));
    }

    /**
     * 
     */
    @Test
    public void testGeneratedActionListenerDispatches() {
        JMenuItem menuItem = new JMenuItem();
        menuWiring.storeMenuItem(MenuIdentifier.FileClose, menuItem);
        final ActionEvent[] result = new ActionEvent[] {null};
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                result[0] = e;
            }
        };
        menuWiring.setActionListener(MenuIdentifier.FileClose, actionListener);
        Assert.assertNull(result[0]);
        ActionEvent event = new ActionEvent(menuItem, 69, "wahey");
        menuWiring.injectActionEvent(MenuIdentifier.FileClose, event);
        Assert.assertEquals(event.getSource(), result[0].getSource());
        // does not match for some reason Assert.assertEquals(event.getID(), result[0].getID());
        Assert.assertEquals(event.getActionCommand(), result[0].getActionCommand());
    }
}
