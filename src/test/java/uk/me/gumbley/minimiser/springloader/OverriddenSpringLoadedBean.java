package uk.me.gumbley.minimiser.springloader;

import org.apache.log4j.Logger;

/**
 * A simple bean loaded form the SpringLoader, for testing purposes.
 * @author matt
 *
 */
public final class OverriddenSpringLoadedBean implements AnswerProvider {
    private static final Logger LOGGER = Logger.getLogger(OverriddenSpringLoadedBean.class);
    /**
     * Just say hello
     */
    public OverriddenSpringLoadedBean() {
        LOGGER.info("Hello from OverriddenSpringLoaderTestBean");
    }
    /**
     * {@inheritDoc}
     */
    public int getTheAnswer() {
        return 16384;
    }
}
