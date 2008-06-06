package uk.me.gumbley.minimiser.springloader;

import org.apache.log4j.Logger;

/**
 * A simple bean loaded form the SpringLoader, for testing purposes.
 * @author matt
 *
 */
public class SpringLoadedBean {
    private static final Logger LOGGER = Logger.getLogger(SpringLoadedBean.class);
    /**
     * Just say hello
     */
    public SpringLoadedBean() {
        LOGGER.info("Hello from SpringLoaderTestBean");
    }
}
