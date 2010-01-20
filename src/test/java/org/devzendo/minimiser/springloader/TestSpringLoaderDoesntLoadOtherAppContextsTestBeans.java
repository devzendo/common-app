package org.devzendo.minimiser.springloader;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test that the SpringLoader, once loaded per test, doesn't cache other
 * test's bean definition's beans.
 * Hopefully the order of test execution wouldn't invalidate this test?
 * @author matt
 *
 */
public final class TestSpringLoaderDoesntLoadOtherAppContextsTestBeans extends
        SpringLoaderUnittestCase {
    private static final Logger LOGGER = Logger
            .getLogger(TestSpringLoaderDoesntLoadOtherAppContextsTestBeans.class);
    /**
     * 
     */
    @Test
    public void testSpringLoaderDoesntLoadOthersTestBeans() {
        final SpringLoader sl = getSpringLoader();
        Assert.assertNotNull(sl);
        try {
            final SpringLoadedBean o1 = sl.getBean("testBean",
                SpringLoadedBean.class);
            LOGGER.info("We created a " + o1);
            Assert.fail("Should not have been able to load up a bean defined"
                    + " in a bean definition file that isn't annotated against"
                    + " this test or its superclasses");
        } catch (final Throwable t) {
            LOGGER.info("Correctly caught exception", t);
        }
    }
}
