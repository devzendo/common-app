package uk.me.gumbley.minimiser.springloader;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;


public class TestSpringLoader extends SpringLoaderTestCase {
    private static final Logger LOGGER = Logger.getLogger(TestSpringLoader.class);
    @Test
    public void testGetSpringLoader() {
        Assert.assertNotNull(getSpringLoader());
        LOGGER.info("Spring Loader is " + getSpringLoader().hashCode());
    }
}
