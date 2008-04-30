package uk.me.gumbley.minimiser.springloader;

import org.apache.log4j.Logger;

/**
 * A simple bean loaded form the SpringLoader, for testing purposes.
 * @author matt
 *
 */
public class SpringLoaderTestBean {
    private static final Logger LOGGER = Logger.getLogger(SpringLoaderTestBean.class);
    /**
     * Just say hello
     */
    public SpringLoaderTestBean() {
        LOGGER.info("Hello from SpringLoaderTestBean");
    }
}
