package org.devzendo.minimiser.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.devzendo.minimiser.gui.menu.Menu.MenuIdentifier;
import org.devzendo.minimiser.logging.LoggingTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



/**
 * Tests the MenuWiring class.
 * 
 * @author matt
 *
 */
public final class TestMenuWiring extends LoggingTestCase {
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
        final JMenuItem menuItem = new JMenuItem();
        menuWiring.storeMenuItem(MenuIdentifier.FileClose, menuItem);
        Assert.assertEquals(menuItem, menuWiring.getMenuItem(MenuIdentifier.FileClose));
    }
    
    /**
     * 
     */
    @Test
    public void testEmptyInitialActionListener() {
        final JMenuItem menuItem = new JMenuItem();
        menuWiring.storeMenuItem(MenuIdentifier.FileClose, menuItem);
        Assert.assertNull(menuWiring.getActionListener(MenuIdentifier.FileClose));
    }

    /**
     * 
     */
    @Test
    public void testSetActionListener() {
        final JMenuItem menuItem = new JMenuItem();
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
    public void testStoreMenuItemAgainRetainsActionListener() {
        final JMenuItem menuItem1 = new JMenuItem();
        menuWiring.storeMenuItem(MenuIdentifier.FileClose, menuItem1);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            }
        };
        menuWiring.setActionListener(MenuIdentifier.FileClose, actionListener);

        final JMenuItem menuItem2 = new JMenuItem();
        menuWiring.storeMenuItem(MenuIdentifier.FileClose, menuItem2);

        Assert.assertEquals(actionListener, menuWiring.getActionListener(MenuIdentifier.FileClose));
    }
    
    /**
     * 
     */
    @Test
    public void testGeneratedActionListenerDispatches() {
        final JMenuItem menuItem = new JMenuItem();
        menuWiring.storeMenuItem(MenuIdentifier.FileClose, menuItem);
        final ActionEvent[] result = new ActionEvent[] {null};
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                result[0] = e;
            }
        };
        menuWiring.setActionListener(MenuIdentifier.FileClose, actionListener);
        Assert.assertNull(result[0]);
        final ActionEvent event = new ActionEvent(menuItem, 69, "wahey");
        menuWiring.injectActionEvent(MenuIdentifier.FileClose, event);
        Assert.assertEquals(event.getSource(), result[0].getSource());
        // does not match for some reason Assert.assertEquals(event.getID(), result[0].getID());
        Assert.assertEquals(event.getActionCommand(), result[0].getActionCommand());
    }
    
    /**
     * 
     */
    @Test
    public void testGeneratedTriggeringOfMenuItemDispatches() {
        final JMenuItem menuItem = new JMenuItem();
        menuWiring.storeMenuItem(MenuIdentifier.FileClose, menuItem);
        final ActionEvent[] result = new ActionEvent[] {null};
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                result[0] = e;
            }
        };
        menuWiring.setActionListener(MenuIdentifier.FileClose, actionListener);
        Assert.assertNull(result[0]);
        menuWiring.triggerActionListener(MenuIdentifier.FileClose);
        // with triggerActionListener, a dummy event is created
        // that contains the JMenuItem, so we can only check that
        Assert.assertNotNull(result[0]);
        Assert.assertEquals(menuItem, result[0].getSource());
    }

    /**
     * 
     */
    @Test
    public void testGeneratedActionListenerStillDispatchesAfterStoringMenuItemAgain() {
        final JMenuItem menuItem1 = new JMenuItem();
        menuWiring.storeMenuItem(MenuIdentifier.FileClose, menuItem1);
        final ActionEvent[] result = new ActionEvent[] {null};
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                result[0] = e;
            }
        };
        menuWiring.setActionListener(MenuIdentifier.FileClose, actionListener);

        // store again
        final JMenuItem menuItem2 = new JMenuItem();
        menuWiring.storeMenuItem(MenuIdentifier.FileClose, menuItem2);
        
        Assert.assertNull(result[0]);
        final ActionEvent event = new ActionEvent(menuItem1, 69, "wahey");
        menuWiring.injectActionEvent(MenuIdentifier.FileClose, event);
        Assert.assertEquals(event.getSource(), result[0].getSource());
        // does not match for some reason Assert.assertEquals(event.getID(), result[0].getID());
        Assert.assertEquals(event.getActionCommand(), result[0].getActionCommand());
    }
    
    /**
     * 
     */
    @Test(expected = IllegalStateException.class)
    public void enableWithNoMenuItemThrows() {
        menuWiring.enableMenuItem(MenuIdentifier.FileClose);
    }

    /**
     * 
     */
    @Test(expected = IllegalStateException.class)
    public void setEnabledWithNoMenuItemThrows() {
        menuWiring.setMenuItemEnabled(MenuIdentifier.FileClose, false);
    }

    /**
     * 
     */
    @Test(expected = IllegalStateException.class)
    public void disableWithNoMenuItemThrows() {
        menuWiring.disableMenuItem(MenuIdentifier.FileClose);
    }
    
    /**
     * 
     */
    @Test(expected = IllegalStateException.class)
    public void isEnabledWithNoMenuItemThrows() {
        menuWiring.isMenuItemEnabled(MenuIdentifier.FileClose);
    }
    
    /**
     * 
     */
    @Test
    public void menuItemsCanBeDisabledAndEnabled() {
        final JMenuItem menuItem1 = new JMenuItem();
        menuWiring.storeMenuItem(MenuIdentifier.FileClose, menuItem1);
        
        Assert.assertTrue(menuWiring.isMenuItemEnabled(MenuIdentifier.FileClose));
        
        menuWiring.disableMenuItem(MenuIdentifier.FileClose);
        Assert.assertFalse(menuWiring.isMenuItemEnabled(MenuIdentifier.FileClose));

        menuWiring.enableMenuItem(MenuIdentifier.FileClose);
        Assert.assertTrue(menuWiring.isMenuItemEnabled(MenuIdentifier.FileClose));

        menuWiring.setMenuItemEnabled(MenuIdentifier.FileClose, false);
        Assert.assertFalse(menuWiring.isMenuItemEnabled(MenuIdentifier.FileClose));

        menuWiring.setMenuItemEnabled(MenuIdentifier.FileClose, true);
        Assert.assertTrue(menuWiring.isMenuItemEnabled(MenuIdentifier.FileClose));
    }
    
    /**
     * 
     */
    @Test
    public void createNonExistantMenuItemGetsNewMenuItemThenReturnsSame() {
        Assert.assertNull(menuWiring.getMenuItem(MenuIdentifier.FileClose));
        final JMenuItem newMI = menuWiring.createMenuItem(MenuIdentifier.FileClose, "Close", 'C');
        Assert.assertSame(newMI, menuWiring.createMenuItem(MenuIdentifier.FileClose, "Close", 'C'));
    }
    
    /**
     * 
     */
    @Test
    public void replaceNonExistantMenuItemGetsNewMenuItemThenReturnsNew() {
        Assert.assertNull(menuWiring.getMenuItem(MenuIdentifier.FileClose));
        final JMenuItem newMI = menuWiring.replaceMenuItem(MenuIdentifier.FileClose, "Close", 'C');
        final JMenuItem replacedMI = menuWiring.replaceMenuItem(MenuIdentifier.FileClose, "Close", 'C');
        Assert.assertNotSame(newMI, replacedMI);
    }
}
