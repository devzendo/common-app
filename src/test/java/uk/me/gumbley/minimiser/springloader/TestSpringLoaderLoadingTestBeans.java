package uk.me.gumbley.minimiser.springloader;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test that we can correctly load up a bean from this test's bean definition
 * file.
 * 
 * @author matt
 */
@ApplicationContext("uk/me/gumbley/minimiser/springloader/TestSpringLoaderLoadingTestBeans.xml")
public class TestSpringLoaderLoadingTestBeans extends SpringLoaderTestCase {

    /**
     * Load up a bean twice, and test its singletonness.
     */
    @Test
    public void testSpringLoaderLoadingTestBeans() {
        SpringLoader sl = getSpringLoader();
        Assert.assertNotNull(sl);
        SpringLoaderTestBean o1 = sl.getBean("testBean",
            SpringLoaderTestBean.class);
        Assert.assertNotNull(o1);
        Assert.assertTrue(o1 instanceof SpringLoaderTestBean);
        SpringLoaderTestBean o2 = sl.getBean("testBean",
            SpringLoaderTestBean.class);
        Assert.assertNotNull(o2);
        Assert.assertTrue(o2 instanceof SpringLoaderTestBean);
        Assert.assertEquals(o1, o2);
    }
}
