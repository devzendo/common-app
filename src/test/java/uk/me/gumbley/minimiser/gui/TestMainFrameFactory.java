package uk.me.gumbley.minimiser.gui;

import java.awt.Frame;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import uk.me.gumbley.minimiser.springloader.ApplicationContext;
import uk.me.gumbley.minimiser.springloader.SpringLoaderUnittestCase;

/**
 * Test the MainFrame FactoryBean
 * @author matt
 *
 */
@ApplicationContext("uk/me/gumbley/minimiser/gui/MainFrameFactoryTestCase.xml")
public final class TestMainFrameFactory extends SpringLoaderUnittestCase {
    
    /**
     * 
     */
    @Test
    public void testStoreMainFrame() {
        final Frame testFrame = new Frame(); 

        final MainFrameFactory prefsFactory = getMainFrameFactory();
        Assert.assertNotNull(prefsFactory);
        prefsFactory.setMainFrame(testFrame);
        
        final Frame prefs = getMainFrame();
        Assert.assertNotNull(prefs);
        
        Assert.assertEquals(testFrame, prefs);
    }
    
    /**
     * @throws IOException on failure
     */
    @Test
    public void itsASingleton() throws IOException {
        final Frame testFrame = new Frame(); 
        getMainFrameFactory().setMainFrame(testFrame);
        
        final Frame frame1 = getMainFrame();
        final Frame frame2 = getMainFrame();
        Assert.assertSame(frame1, frame2);
    }

    private Frame getMainFrame() {
        return getSpringLoader().getBean("mainFrame", Frame.class);
    }

    private MainFrameFactory getMainFrameFactory() {
        return getSpringLoader().getBean("&mainFrame", MainFrameFactory.class);
    }
}
