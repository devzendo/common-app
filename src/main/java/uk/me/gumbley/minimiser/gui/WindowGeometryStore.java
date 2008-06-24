package uk.me.gumbley.minimiser.gui;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;
import org.apache.log4j.Logger;
import uk.me.gumbley.minimiser.prefs.Prefs;

public class WindowGeometryStore implements AWTEventListener {
    private static final Logger LOGGER = Logger
            .getLogger(WindowGeometryStore.class);
    private final Map<String, JFrame> windowStore = new HashMap<String, JFrame>();
    private final Prefs prefs;
    // WOZERE this doesn't work as I was expecting it to - consult swing hacks book again.
    public WindowGeometryStore(final Prefs preferences) {
        this.prefs = preferences;
    }
    public void startListener() {
        Toolkit.getDefaultToolkit().addAWTEventListener(this,
            AWTEvent.WINDOW_EVENT_MASK);
    }
    public void eventDispatched(final AWTEvent event) {
        if (event instanceof ComponentEvent && event.getID() == WindowEvent.WINDOW_OPENED) {
            final ComponentEvent componentEvent = (ComponentEvent) event;
            final Component component = componentEvent.getComponent();
            if (component instanceof JFrame) {
                JFrame frame = (JFrame) component;
                loadGeometry(frame);
            }
        }
    }

    public boolean hasStoredGeometry(final JFrame frame) {
        final String name = frame.getName();
        LOGGER.debug("Trying to load stored geometry for JFrame '" + name + "'");
        final String geomStr = prefs.getWindowGeometry(name);
        return (!geomStr.equals(""));
    }
    
    private void loadGeometry(final JFrame frame) {
        final String name = frame.getName();
        LOGGER.debug("Trying to load stored geometry for JFrame '" + name + "'");
        final String geomStr = prefs.getWindowGeometry(name);
        if (geomStr.equals("")) {
            LOGGER.debug("No geometry stored for JFrame '" + name + "'");
            return;
        }
        synchronized (windowStore) {
            windowStore.put(name, frame);
            LOGGER.debug("Starting geometry is " + geomStr);
            // x,y,width,height
            final String[] geomNumStrs = geomStr.split(",");
            final int[] geomNums = new int[geomNumStrs.length];
            for (int i = 0; i < geomNumStrs.length; i++) {
                geomNums[i] = Integer.parseInt(geomNumStrs[i]);
            }
            frame.setLocation(geomNums[0], geomNums[1]);
            frame.setSize(new Dimension(geomNums[2], geomNums[3]));
            frame.validate();
            LOGGER.debug("Starting geometry set for window '" + name + "'");
        }
    }

    public void watchWindow(final String name) {
        LOGGER.debug("We will store geometry for JFrame '" + name + "'");
        synchronized (windowStore) {
            windowStore.put(name, null);
        }
    }

    public void saveGeometry(final JFrame window) {
        final String windowName = window.getName();
        LOGGER.debug("should we store window " + windowName);
        synchronized (windowStore) {
            if (windowStore.containsKey(windowName)) {
                final Rectangle rect = window.getBounds();
                final String geomStr = String.format("%d,%d,%d,%d",
                    rect.x, rect.y, rect.width, rect.height);
                LOGGER.debug("Storing window '" + windowName + "' geometry " + geomStr);
                prefs.setWindowGeometry(windowName, geomStr);
            }
        }
    }
}
