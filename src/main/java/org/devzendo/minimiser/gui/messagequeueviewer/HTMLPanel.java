package org.devzendo.minimiser.gui.messagequeueviewer;

import javax.swing.JTextPane;

/**
 * A JPanel that hosts a JTextArea for rendering read-only HTML
 * content.
 * 
 * @author matt
 *
 */
@SuppressWarnings("serial")
public final class HTMLPanel extends JTextPane {

    /**
     * Create a panel with no text. Can always call setHTMLText later.
     */
    public HTMLPanel() {
        super();
        initialise("");
    }

    /**
     * Create a panel with initial text.
     * @param html the HTML to render
     */
    public HTMLPanel(final String html) {
        super();
        initialise(html);
    }

    private void initialise(final String html) {
        setContentType("text/html");
        setHTMLText(html);
        setEditable(false);
    }
    
    /**
     * Set the HTML that this panel is to render.
     * @param html the HTML
     */
    public void setHTMLText(final String html) {
        setText(html);
        setCaretPosition(0);
    }
}
