package uk.me.gumbley.minimiser.gui.dialog.snaildialog;

import java.awt.FlowLayout;
import java.util.concurrent.CountDownLatch;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import uk.me.gumbley.commoncode.concurrency.ThreadUtils;
import uk.me.gumbley.commoncode.gui.GUIUtils;
import uk.me.gumbley.minimiser.logging.LoggingTestCase;

/**
 * Tests the SnailDialog's threading and delayed initialisation mechanism.
 * 
 * @author matt
 */
public final class TestSnailDialog extends LoggingTestCase {
    private static final Logger LOGGER = Logger
            .getLogger(TestSnailDialog.class);

    private Object lock = new Object();

    //private volatile StubRecordingSnailDialog snailDialog;

    private volatile JDialog testDialog;
    /**
     * @throws InterruptedException
     *         on latch failure
     */
    @Test(timeout = 8000)
    @Ignore
    public void testIt() throws InterruptedException {
        LOGGER.debug("starting test");
        final CountDownLatch creationLatch = new CountDownLatch(1);
        LOGGER.debug("running creation on edt");
        GUIUtils.runOnEventThread(new Runnable() {
            public void run() {
                LOGGER.debug("Creating main frame");
                final JFrame mainFrame = new JFrame("main frame");
                LOGGER.debug("Setting main frame visible");
                mainFrame.setVisible(true);
                LOGGER.debug("Creating dialog");
                /*snailDialog = new StubRecordingSnailDialog(mainFrame,
                        new CursorManager());
                snailDialog.pack();*/
                testDialog = new JDialog(mainFrame, "dialog", true);
                testDialog.setLayout(new FlowLayout());
                testDialog.add(new JLabel("tets dialog"));
                testDialog.pack();
                
                LOGGER.debug("Created dialog; counting down");
                creationLatch.countDown();
                LOGGER.debug("finished creation");
            }
        });
        LOGGER.debug("waiting for creation");
        creationLatch.await();
        LOGGER.debug("Created");
//        Assert.assertFalse(snailDialog.isInitialised());
//        Assert.assertFalse(snailDialog.isConstructedOnNonEventThread());
//        Assert.assertFalse(snailDialog.isFinishedOnEventThread());
        LOGGER.debug("making it visible");

//        LOGGER.debug("making visible on EDT");
        
        //snailDialog.setVisible(true); // hangs here
        testDialog.setVisible(true);
        
        
        final CountDownLatch visibleLatch = new CountDownLatch(1);
        try {
            GUIUtils.runOnEventThread(new Runnable() {
                public void run() {
                    try {
                        LOGGER.debug("counting down visibleLatch");
                        visibleLatch.countDown();
                        LOGGER.debug("Counted down visibleLatch");
                    } catch (Throwable t) {
                        LOGGER.error("Caught unexpected "
                                + t.getClass().getSimpleName(), t);
                    }
                }
            });
            ThreadUtils.waitNoInterruption(250);
            LOGGER.debug("made it visible; waiting for visibleLatch");
            visibleLatch.await();
            LOGGER.debug("visible lLatch counted down");
//            Assert.assertTrue(snailDialog.isInitialised());
//            Assert.assertTrue(snailDialog.isConstructedOnNonEventThread());
//            Assert.assertTrue(snailDialog.isFinishedOnEventThread());
            LOGGER.debug("ending test in finally block");
        } finally {
            GUIUtils.runOnEventThread(new Runnable() {
                public void run() {
                    LOGGER.debug("clearing");
//                    snailDialog.clearAndHide();
                    testDialog.dispose();
                    LOGGER.debug("cleared");
                }
            });
        }
    }
}
