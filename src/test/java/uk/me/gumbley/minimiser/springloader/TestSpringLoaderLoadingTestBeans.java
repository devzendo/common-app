package uk.me.gumbley.minimiser.springloader;

import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.Test;

@ApplicationContext("uk/me/gumbley/minimiser/springloader/TestSpringLoaderLoadingTestBeans.xml")
public class TestSpringLoaderLoadingTestBeans extends SpringLoaderTestCase {
    static {
        BasicConfigurator.configure();
    }
    @Test
    public void testSpringLoaderLoadingTestBeans() {
        SpringLoader sl = getSpringLoader();
        Assert.assertNotNull(sl);
        SpringLoaderTestBean o1 = sl.getBean("testBean", SpringLoaderTestBean.class);
        Assert.assertNotNull(o1);
        Assert.assertTrue(o1 instanceof SpringLoaderTestBean);
        SpringLoaderTestBean o2 = sl.getBean("testBean", SpringLoaderTestBean.class);
        Assert.assertNotNull(o2);
        Assert.assertTrue(o2 instanceof SpringLoaderTestBean);
        Assert.assertEquals(o1, o2);
    }
}
