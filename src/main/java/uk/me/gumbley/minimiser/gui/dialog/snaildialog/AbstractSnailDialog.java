package uk.me.gumbley.minimiser.gui.dialog.snaildialog;

import java.awt.AWTEvent;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import org.apache.log4j.Logger;
import uk.me.gumbley.commoncode.gui.SwingWorker;
import uk.me.gumbley.minimiser.gui.CursorManager;

/**
 * An AbstractSnailDialog is a modal JDialog that takes a long time to
 * initialise to its final state.
 * <p>
 * Examples might be the Tools->Options dialog (which loads several beans, one
 * for each tab), and the About dialog (which loads several pages of text).
 * <p>
 * While being constructed, the hourglass cursor will be shown, and once
 * construction is finished, it will be set back to normal. 
 * <p>
 * During initialisation, several SwingWorkers can be constructed and added
 * to a list. Once the dialog is visible, these will be executed (then the
 * cursor is set back normal).
 * @author matt
 *
 */
public abstract class AbstractSnailDialog extends JDialog {
    private static final Logger LOGGER = Logger
            .getLogger(AbstractSnailDialog.class);
    private final CursorManager cursorManager;
    private List<SwingWorker> workers;
    private Object lock;
    private AWTEventListener awtEventListener;

    /**
     * Construct the AbstractSnailDialog
     * @param parentFrame the parent frame; this is a modal dialog
     * @param cursor the cursor manager
     * @param title the title for this frame, which could be left empty and set
     * via one of the workers initialised by the initialise method.
     */
    public AbstractSnailDialog(final Frame parentFrame, final CursorManager cursor, final String title) {
        super(parentFrame, true);
        cursorManager = cursor;
        setTitle(title);
        lock = new Object();
        workers = new ArrayList<SwingWorker>();
        
        /*cursorManager.hourglassViaEventThread();
        setContentPane(getMainComponent());
        initialise();
        addCursorNormalWorker();
        
        // Load and display the about texts after the window has been
        // made visible. Performance legerdemain...
        awtEventListener = new AWTEventListener() {
                            public void eventDispatched(final AWTEvent event) {
                                if (event.getID() == WindowEvent.WINDOW_OPENED && event.getSource() == AbstractSnailDialog.this) {
                                    synchronized (lock) {
                                        final Thread swingWorkerExecutorThread = new Thread(new Runnable() {
                                            public void run() {
                                                LOGGER.debug("executing all swing workers");
                                                for (SwingWorker worker : workers) {
                                                    LOGGER.debug("executing swing worker");
                                                    worker.start();
                                                    LOGGER.debug("executed swing worker");
                                                }
                                                LOGGER.debug("executed all swing workers");
                                            }
                                        });
                                        swingWorkerExecutorThread.setName("SwingWorkerExecutor");
                                        swingWorkerExecutorThread.start();
                                    }
                                }
                            }
                            };
        Toolkit.getDefaultToolkit().addAWTEventListener(awtEventListener, AWTEvent.WINDOW_EVENT_MASK);
        */
    }
    
    /**
     * Create and return the dialog's main component; might be a JPanel or
     * JOptionPane into which other items might be dynamically loaded during
     * initialise.
     * @return the dialog's main component
     */
    protected abstract Container getMainComponent();

    private void addCursorNormalWorker() {
        addSwingWorker(new SwingWorker() {

            @Override
            public Object construct() {
                LOGGER.debug("Normal Cursor SwingWorker - construct");
                return null;
            }
            
            public void finished() {
                LOGGER.debug("Normal Cursor SwingWorker - finished");
                cursorManager.normal();
            }
        });
    }

    /**
     * Called by subclasses' initialise method, add a SwingWorker to the list
     * of workers that will be executed when the window is visible.
     * @param worker the worker to execute upon visibility.
     */
    protected final void addSwingWorker(final SwingWorker worker) {
        synchronized (lock) {
            workers.add(worker);
        }
    }
    
    /**
     * Initialise the dialog by adding SwingWorkers to the worker list.
     */
    protected abstract void initialise();


    /**
     * This method clears the dialog and hides it.
     */
    public final void clearAndHide() {
        if (awtEventListener != null) {
            Toolkit.getDefaultToolkit().removeAWTEventListener(awtEventListener);
        }
        setVisible(false);
        cursorManager.normal();
        dispose();
        
    }
}
