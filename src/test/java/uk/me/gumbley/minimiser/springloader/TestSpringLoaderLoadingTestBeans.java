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
public class TestSpringLoaderLoadingTestBeans extends SpringLoaderUnittestCase {

    /**
     * Load up a bean twice, and test its singletonness.
     */
    @Test
    public void testSpringLoaderLoadingTestBeans() {
        SpringLoader sl = getSpringLoader();
        Assert.assertNotNull(sl);
        SpringLoadedBean o1 = sl.getBean("testBean",
            SpringLoadedBean.class);
        Assert.assertNotNull(o1);
        Assert.assertTrue(o1 instanceof SpringLoadedBean);
        SpringLoadedBean o2 = sl.getBean("testBean",
            SpringLoadedBean.class);
        Assert.assertNotNull(o2);
        Assert.assertTrue(o2 instanceof SpringLoadedBean);
        Assert.assertEquals(o1, o2);
    }
}
