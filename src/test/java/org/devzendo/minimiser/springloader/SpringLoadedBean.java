package org.devzendo.minimiser.springloader;

import org.apache.log4j.Logger;

/**
 * A simple bean loaded form the SpringLoader, for testing purposes.
 * @author matt
 *
 */
public final class SpringLoadedBean implements AnswerProvider {
    private static final Logger LOGGER = Logger.getLogger(SpringLoadedBean.class);
    /**
     * Just say hello
     */
    public SpringLoadedBean() {
        LOGGER.info("Hello from SpringLoaderTestBean");
    }
    /**
     * {@inheritDoc}
     */
    public int getTheAnswer() {
        return 31415;
    }
}
