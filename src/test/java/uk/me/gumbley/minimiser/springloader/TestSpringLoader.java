package uk.me.gumbley.minimiser.springloader;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;


/**
 * Test the SpringLoader
 * @author matt
 *
 */
public final class TestSpringLoader extends SpringLoaderUnittestCase {
    private static final Logger LOGGER = Logger.getLogger(TestSpringLoader.class);
    /**
     * 
     */
    @Test
    public void testGetSpringLoader() {
        Assert.assertNotNull(getSpringLoader());
        LOGGER.info("Spring Loader is " + getSpringLoader().hashCode());
    }
}
