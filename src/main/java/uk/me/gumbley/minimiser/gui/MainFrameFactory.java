package uk.me.gumbley.minimiser.gui;

import java.awt.Frame;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;

/**
 * Factory bean for obtaining the singleton MainFrame - the application's main
 * window.
 * 
 * @author matt
 *
 */
public class MainFrameFactory implements FactoryBean {
    private static final Logger LOGGER = Logger
            .getLogger(MainFrameFactory.class);
    private Frame factoryMainFrame;
    
    /**
     * {@inheritDoc}
     */
    public Object getObject() throws Exception {
        LOGGER.debug("MainFrameFactory returning main frame as Frame object");
        return factoryMainFrame;
    }

    /**
     * {@inheritDoc}
     */
    public Class getObjectType() {
        return Frame.class;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isSingleton() {
        return true;
    }

    /**
     * Factory population method
     * @param frame the Frame to return as a Singleton
     */
    public void setMainFrame(final Frame frame) {
        LOGGER.debug("MainFrameFactory being populated with Frame object");
        factoryMainFrame = frame;
    }
}
