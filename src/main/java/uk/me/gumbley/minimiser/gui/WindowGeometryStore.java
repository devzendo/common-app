package uk.me.gumbley.minimiser.gui;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import uk.me.gumbley.minimiser.prefs.Prefs;

public class WindowGeometryStore implements AWTEventListener {
    private static final Logger LOGGER = Logger
            .getLogger(WindowGeometryStore.class);
    private final Map<String, Window> windowStore = new HashMap<String, Window>();
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
        if (event instanceof WindowEvent && (event.getID() & WindowEvent.WINDOW_OPENED) == WindowEvent.WINDOW_OPENED) {
            final WindowEvent windowEvent = (WindowEvent) event;
            final Window window = windowEvent.getWindow();
            final String windowName = window.getName();
            synchronized (windowStore) {
                if (windowStore.containsKey(windowName)) {
                    LOGGER.debug("Setting stored geometry for window '" + windowName + "'");
                    final String geomStr = prefs.getWindowGeometry(windowName);
                    LOGGER.debug("Starting geometry is " + geomStr);
                    // x,y,width,height
                    final String[] geomNumStrs = geomStr.split(",");
                    final int[] geomNums = new int[geomNumStrs.length];
                    for (int i = 0; i < geomNumStrs.length; i++) {
                        geomNums[i] = Integer.parseInt(geomNumStrs[i]);
                    }
                    window.setLocation(geomNums[0], geomNums[1]);
                    window.setPreferredSize(new Dimension(geomNums[2], geomNums[3]));
                    LOGGER.debug("Starting geometry set for window '" + windowName + "'");
                }
            }
        }
    }

    public void watchWindow(final Window window) {
        synchronized (windowStore) {
            windowStore.put(window.getName(), window);
        }
    }
    
    private void saveGeometry(final Window window) {
        synchronized (windowStore) {
            final String windowName = window.getName();
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
