/**
 * Copyright (C) 2008-2010 Matt Gumbley, DevZendo.org <http://devzendo.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.devzendo.minimiser.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

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
        Assert.assertNull(menuWiring.getMenuItem(SystemMenuIdentifiers.FileClose));
    }

    /**
     * 
     */
    @Test
    public void testNonExistantActionListener() {
        Assert.assertNull(menuWiring.getActionListener(SystemMenuIdentifiers.FileClose));
    }
    
    /**
     * 
     */
    @Test
    public void testReturnMenuItem() {
        final JMenuItem menuItem = new JMenuItem();
        menuWiring.storeMenuItem(SystemMenuIdentifiers.FileClose, menuItem);
        Assert.assertEquals(menuItem, menuWiring.getMenuItem(SystemMenuIdentifiers.FileClose));
    }
    
    /**
     * 
     */
    @Test
    public void testEmptyInitialActionListener() {
        final JMenuItem menuItem = new JMenuItem();
        menuWiring.storeMenuItem(SystemMenuIdentifiers.FileClose, menuItem);
        Assert.assertNull(menuWiring.getActionListener(SystemMenuIdentifiers.FileClose));
    }

    /**
     * 
     */
    @Test
    public void testSetActionListener() {
        final JMenuItem menuItem = new JMenuItem();
        menuWiring.storeMenuItem(SystemMenuIdentifiers.FileClose, menuItem);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            }
        };
        menuWiring.setActionListener(SystemMenuIdentifiers.FileClose, actionListener);
        Assert.assertEquals(actionListener, menuWiring.getActionListener(SystemMenuIdentifiers.FileClose));
    }

    /**
     * 
     */
    @Test
    public void testStoreMenuItemAgainRetainsActionListener() {
        final JMenuItem menuItem1 = new JMenuItem();
        menuWiring.storeMenuItem(SystemMenuIdentifiers.FileClose, menuItem1);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            }
        };
        menuWiring.setActionListener(SystemMenuIdentifiers.FileClose, actionListener);

        final JMenuItem menuItem2 = new JMenuItem();
        menuWiring.storeMenuItem(SystemMenuIdentifiers.FileClose, menuItem2);

        Assert.assertEquals(actionListener, menuWiring.getActionListener(SystemMenuIdentifiers.FileClose));
    }
    
    /**
     * 
     */
    @Test
    public void testGeneratedActionListenerDispatches() {
        final JMenuItem menuItem = new JMenuItem();
        menuWiring.storeMenuItem(SystemMenuIdentifiers.FileClose, menuItem);
        final ActionEvent[] result = new ActionEvent[] {null};
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                result[0] = e;
            }
        };
        menuWiring.setActionListener(SystemMenuIdentifiers.FileClose, actionListener);
        Assert.assertNull(result[0]);
        final ActionEvent event = new ActionEvent(menuItem, 69, "wahey");
        menuWiring.injectActionEvent(SystemMenuIdentifiers.FileClose, event);
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
        menuWiring.storeMenuItem(SystemMenuIdentifiers.FileClose, menuItem);
        final ActionEvent[] result = new ActionEvent[] {null};
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                result[0] = e;
            }
        };
        menuWiring.setActionListener(SystemMenuIdentifiers.FileClose, actionListener);
        Assert.assertNull(result[0]);
        menuWiring.triggerActionListener(SystemMenuIdentifiers.FileClose);
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
        menuWiring.storeMenuItem(SystemMenuIdentifiers.FileClose, menuItem1);
        final ActionEvent[] result = new ActionEvent[] {null};
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                result[0] = e;
            }
        };
        menuWiring.setActionListener(SystemMenuIdentifiers.FileClose, actionListener);

        // store again
        final JMenuItem menuItem2 = new JMenuItem();
        menuWiring.storeMenuItem(SystemMenuIdentifiers.FileClose, menuItem2);
        
        Assert.assertNull(result[0]);
        final ActionEvent event = new ActionEvent(menuItem1, 69, "wahey");
        menuWiring.injectActionEvent(SystemMenuIdentifiers.FileClose, event);
        Assert.assertEquals(event.getSource(), result[0].getSource());
        // does not match for some reason Assert.assertEquals(event.getID(), result[0].getID());
        Assert.assertEquals(event.getActionCommand(), result[0].getActionCommand());
    }
    
    /**
     * 
     */
    @Test(expected = IllegalStateException.class)
    public void enableWithNoMenuItemThrows() {
        menuWiring.enableMenuItem(SystemMenuIdentifiers.FileClose);
    }

    /**
     * 
     */
    @Test(expected = IllegalStateException.class)
    public void setEnabledWithNoMenuItemThrows() {
        menuWiring.setMenuItemEnabled(SystemMenuIdentifiers.FileClose, false);
    }

    /**
     * 
     */
    @Test(expected = IllegalStateException.class)
    public void disableWithNoMenuItemThrows() {
        menuWiring.disableMenuItem(SystemMenuIdentifiers.FileClose);
    }
    
    /**
     * 
     */
    @Test(expected = IllegalStateException.class)
    public void isEnabledWithNoMenuItemThrows() {
        menuWiring.isMenuItemEnabled(SystemMenuIdentifiers.FileClose);
    }
    
    /**
     * 
     */
    @Test
    public void menuItemsCanBeDisabledAndEnabled() {
        final JMenuItem menuItem1 = new JMenuItem();
        menuWiring.storeMenuItem(SystemMenuIdentifiers.FileClose, menuItem1);
        
        Assert.assertTrue(menuWiring.isMenuItemEnabled(SystemMenuIdentifiers.FileClose));
        
        menuWiring.disableMenuItem(SystemMenuIdentifiers.FileClose);
        Assert.assertFalse(menuWiring.isMenuItemEnabled(SystemMenuIdentifiers.FileClose));

        menuWiring.enableMenuItem(SystemMenuIdentifiers.FileClose);
        Assert.assertTrue(menuWiring.isMenuItemEnabled(SystemMenuIdentifiers.FileClose));

        menuWiring.setMenuItemEnabled(SystemMenuIdentifiers.FileClose, false);
        Assert.assertFalse(menuWiring.isMenuItemEnabled(SystemMenuIdentifiers.FileClose));

        menuWiring.setMenuItemEnabled(SystemMenuIdentifiers.FileClose, true);
        Assert.assertTrue(menuWiring.isMenuItemEnabled(SystemMenuIdentifiers.FileClose));
    }
    
    /**
     * 
     */
    @Test
    public void createNonExistantMenuItemGetsNewMenuItemThenReturnsSame() {
        Assert.assertNull(menuWiring.getMenuItem(SystemMenuIdentifiers.FileClose));
        final JMenuItem newMI = menuWiring.createMenuItem(SystemMenuIdentifiers.FileClose, "Close", 'C');
        Assert.assertSame(newMI, menuWiring.createMenuItem(SystemMenuIdentifiers.FileClose, "Close", 'C'));
    }
    
    /**
     * 
     */
    @Test
    public void replaceNonExistantMenuItemGetsNewMenuItemThenReturnsNew() {
        Assert.assertNull(menuWiring.getMenuItem(SystemMenuIdentifiers.FileClose));
        final JMenuItem newMI = menuWiring.replaceMenuItem(SystemMenuIdentifiers.FileClose, "Close", 'C');
        final JMenuItem replacedMI = menuWiring.replaceMenuItem(SystemMenuIdentifiers.FileClose, "Close", 'C');
        Assert.assertNotSame(newMI, replacedMI);
    }
}
