package uk.me.gumbley.minimiser.springloader;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;


public class TestSpringLoader extends SpringLoaderTestCase {
    static {
        BasicConfigurator.configure();
    }
    private static final Logger LOGGER = Logger.getLogger(TestSpringLoader.class);
    @Test
    public void testGetSpringLoader() {
        Assert.assertNotNull(getSpringLoader());
        LOGGER.info("Spring Loader is " + getSpringLoader().hashCode());
    }
}
