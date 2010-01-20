package org.devzendo.minimiser.gui;

import java.awt.Frame;
import java.io.IOException;

import org.devzendo.minimiser.springloader.ApplicationContext;
import org.devzendo.minimiser.springloader.SpringLoaderUnittestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test the MainFrame FactoryBean
 * @author matt
 *
 */
@ApplicationContext("org/devzendo/minimiser/gui/MainFrameFactoryTestCase.xml")
public final class TestMainFrameFactory extends SpringLoaderUnittestCase {

    /**
     *
     */
    @Test
    public void testStoreMainFrame() {
        final Frame testFrame = new Frame();

        final MainFrameFactory mainFrameFactory = getMainFrameFactory();
        Assert.assertNotNull(mainFrameFactory);
        mainFrameFactory.setMainFrame(testFrame);

        final Frame mainFrame = getMainFrame();
        Assert.assertNotNull(mainFrame);

        Assert.assertEquals(testFrame, mainFrame);
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
