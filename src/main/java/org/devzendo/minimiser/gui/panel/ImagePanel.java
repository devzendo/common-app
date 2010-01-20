package org.devzendo.minimiser.gui.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * A JPanel that shows a background graphic.
 * 
 * @author matt
 *
 */
@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
    private final Image mImage;

    /**
     * Construct an ImagePanel from a file.
     * @param fileName the name of the graphic file.
     */
    public ImagePanel(final String fileName) {
        this(new ImageIcon(fileName).getImage());
    }

    /**
     * Construct an ImagePanel from a URL, possibly via the
     * ResourceLoader?
     * @param url the URL of a graphic file
     */
    public ImagePanel(final URL url) {
        this(new ImageIcon(url).getImage());
    }
    
    /**
     * Construct an ImagePanel given an Image
     * @param image the image to show in the background
     */
    public ImagePanel(final Image image) {
        this.mImage = image;
        final Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void paintComponent(final Graphics g) {
        g.drawImage(mImage, 0, 0, null);
    }
}
