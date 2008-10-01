package uk.me.gumbley.minimiser.gui.dialog.snaildialog;

import java.util.concurrent.CountDownLatch;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import uk.me.gumbley.commoncode.gui.GUIUtils;
import uk.me.gumbley.minimiser.gui.CursorManager;
import uk.me.gumbley.minimiser.logging.LoggingTestCase;


/**
 * Tests the SnailDialog's threading and delayed initialisation mechanism.
 * 
 * @author matt
 *
 */
public final class TestSnailDialog extends LoggingTestCase {
    private static final Logger LOGGER = Logger
            .getLogger(TestSnailDialog.class);
    private Object lock = new Object();
    private StubRecordingSnailDialog snailDialog;

    
    /**
     * @throws InterruptedException on latch failure
     * 
     */
    @Test(timeout = 2000)
    @Ignore
    public void testIt() throws InterruptedException {
        LOGGER.debug("starting test");
        final CountDownLatch latch = new CountDownLatch(1);
        GUIUtils.runOnEventThread(new Runnable() {
            public void run() {
                synchronized (lock) {
                    snailDialog = new StubRecordingSnailDialog(null, new CursorManager());
                    latch.countDown();
                }
            }
        });
        latch.await();
        synchronized (lock) {
            Assert.assertFalse(snailDialog.isInitialised());
            Assert.assertFalse(snailDialog.isConstructedOnNonEventThread());
            Assert.assertFalse(snailDialog.isFinishedOnEventThread());
        }

        LOGGER.debug("makin it visible");
        final CountDownLatch visibleLatch = new CountDownLatch(1);
        try {
            GUIUtils.runOnEventThread(new Runnable() {
                public void run() {
                    LOGGER.debug("making visible on EDT");
                    synchronized (lock) {
                        snailDialog.show(); //setVisible(true);
                    }
                    LOGGER.debug("counting down visibleLatch");
                    visibleLatch.countDown();
                    LOGGER.debug("Counted down visibleLatch");
                }
            });
            LOGGER.debug("made it visible; waiting for visibleLatch");
            visibleLatch.await();
            LOGGER.debug("visible lLatch counted down");

            synchronized (lock) {
                Assert.assertTrue(snailDialog.isInitialised());
                Assert.assertTrue(snailDialog.isConstructedOnNonEventThread());
                Assert.assertTrue(snailDialog.isFinishedOnEventThread());
            }
            LOGGER.debug("ending test in finally block");
        } finally {
            GUIUtils.runOnEventThread(new Runnable() {
                public void run() {
                    LOGGER.debug("clearing");
                    snailDialog.clearAndHide();
                    LOGGER.debug("cleared");
                }
            });
        }
    }
}
