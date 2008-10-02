package uk.me.gumbley.minimiser.gui.dialog.snaildialog;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import uk.me.gumbley.commoncode.gui.SwingWorker;
import uk.me.gumbley.minimiser.gui.CursorManager;

/**
 * A SnailDialog that initialises a worker that records the threads it has been
 * initialised on.
 * 
 * @author matt
 *
 */
public final class StubRecordingSnailDialog extends AbstractSnailDialog {
    private static final long serialVersionUID = -5310917886433456342L;
    private static final Logger LOGGER = Logger
            .getLogger(StubRecordingSnailDialog.class);
    private volatile boolean isInitialised = false;
    private volatile boolean isConstructedOnNonEventThread = false;
    private volatile boolean isFinishedOnEventThread = false;
    
    /**
     * Construct a recording snail
     * @param parentFrame the parent
     * @param manager the cursor manager
     */
    public StubRecordingSnailDialog(final Frame parentFrame, final CursorManager manager) {
        super(parentFrame, manager, "Stub");
        LOGGER.debug("Constructed");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Container getMainComponent() {
        LOGGER.debug("getMainComponent called");
        final JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(200, 200));
        return panel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialise() {
        LOGGER.debug("initialise called");
        isInitialised = true;
        final SwingWorker worker = new SwingWorker() {
            @Override
            public Object construct() {
                LOGGER.debug("StubRecordingSnailDialog SwingWorker - construct called");
                isConstructedOnNonEventThread = !EventQueue.isDispatchThread();
                return new Object();
            }
            
            public void finished() {
                LOGGER.debug("StubRecordingSnailDialog SwingWorker - finished called");
                isFinishedOnEventThread = EventQueue.isDispatchThread();
            }
        };
        addSwingWorker(worker);
        LOGGER.debug("Out of initialise");
    }

    /**
     * Has the initialise method been called?
     * @return true iff it has
     */
    public boolean isInitialised() {
        return isInitialised;
    }

    /**
     * Has the construct method of the SwingWorker been called on a non EDT?
     * @return true iff called on a non EDT
     */
    public boolean isConstructedOnNonEventThread() {
        return isConstructedOnNonEventThread;
    }

    /**
     * Has the finished method of the SwingWorker been called on an EDT?
     * @return true iff called on an EDT
     */
    public boolean isFinishedOnEventThread() {
        return isFinishedOnEventThread;
    }
}
