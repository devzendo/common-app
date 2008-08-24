package uk.me.gumbley.minimiser.springbeanlistloader;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import uk.me.gumbley.minimiser.springloader.ApplicationContext;
import uk.me.gumbley.minimiser.springloader.SpringLoaderUnittestCase;


/**
 * Tests for lifecycle management.
 * 
 * @author matt
 *
 */
@ApplicationContext("uk/me/gumbley/minimiser/springbeanlistloader/ThingySpringBeanListLoaderTestCase.xml")
public final class TestThingySpringBeanListLoader extends SpringLoaderUnittestCase {
    
    private ThingySpringBeanListLoader beanListLoader;

    /**
     * 
     */
    public void getSimpleTestPrerequisites() {
        beanListLoader = getSpringLoader().getBean("beanListLoader", ThingySpringBeanListLoader.class);
    }
    
    /**
     * 
     */
    @Test
    public void haveBeanListLoader() {
        getSimpleTestPrerequisites();
        
        Assert.assertNotNull(beanListLoader);
    }
    
    /**
     * 
     */
    @Test
    public void verifyTestResourcesGetWired() {
        getSimpleTestPrerequisites();
        
        final Thingy one = getSpringLoader().getBean("one", Thingy.class);
        Assert.assertNotNull(one);
        Assert.assertTrue(one instanceof OneThingy);
        
        final Thingy two = getSpringLoader().getBean("two", Thingy.class);
        Assert.assertNotNull(two);
        Assert.assertTrue(two instanceof TwoThingy);
    }
    
    /**
     * 
     */
    @Test
    public void springBeanListLoaderGetsRightConfig() {
        getSimpleTestPrerequisites();
        
        final List<String> beanNames = beanListLoader.getBeanNames();
        Assert.assertNotNull(beanNames);
        Assert.assertEquals(2, beanNames.size());
        Assert.assertEquals("one", beanNames.get(0));
        Assert.assertEquals("two", beanNames.get(1));
        
        final Thingy one = beanListLoader.getBean("one");
        Assert.assertTrue(one instanceof OneThingy);

        final Thingy two = beanListLoader.getBean("two");
        Assert.assertTrue(two instanceof TwoThingy);

    }
    
    /**
     * 
     */
    @Test
    public void instantiationOfLifecycles() {
        getSimpleTestPrerequisites();
        
        final Thingy two = getSpringLoader().getBean("two", Thingy.class);
        Assert.assertSame(two, beanListLoader.getBean("two"));
    }
    
    /**
     * this lifecycle manager has a bad bean - nonexistant - but the others
     * load OK
     */
    @Test
    public void dontStartupBadBeans() {
        beanListLoader = getSpringLoader().getBean("badLoadLifecycleManager", ThingySpringBeanListLoader.class);
        Assert.assertNotNull(beanListLoader);
        Assert.assertEquals(2, beanListLoader.getBeanNames().size());

        final List<Thingy> list = beanListLoader.getBeans();
        Assert.assertEquals(2, list.size());
        Assert.assertTrue(list.get(0) instanceof OneThingy);
        Assert.assertTrue(list.get(1) instanceof TwoThingy);
    }
    
}
