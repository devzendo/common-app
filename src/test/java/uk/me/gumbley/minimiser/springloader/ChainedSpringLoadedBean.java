package uk.me.gumbley.minimiser.springloader;

import org.apache.log4j.Logger;

/**
 * A simple bean loaded from a chained SpringLoader, for testing
 * purposes.
 * @author matt
 *
 */
public final class ChainedSpringLoadedBean implements AnswerProvider {
    private static final Logger LOGGER = Logger.getLogger(ChainedSpringLoadedBean.class);
    /**
     * Just say hello
     */
    public ChainedSpringLoadedBean() {
        LOGGER.info("Hello from ChainedSpringLoaderTestBean");
    }
    /**
     * @return the answer to life, the universe, and everything
     */
    public int getTheAnswer() {
        return 42;
    }
}
