package uk.me.gumbley.minimiser.springloader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;

import uk.me.gumbley.minimiser.logging.LoggingTestCase;

/**
 * A base class for Junit 4 test cases that specify a set of Spring application
 * context XML files to be used by the ClassPathApplicationContextLoader using
 * the
 * 
 * @ApplicationContext annotation.
 * @author matt
 */
public abstract class SpringLoaderTestCase extends LoggingTestCase {
    private SpringLoader springLoader;

    /**
     * Set up the SpringLoader with all application context files given in any
     * 
     * @ApplicatonContext annotations on this test class, and any in its
     *                    inheritance hierarchy.
     */
    @Before
    public void initApplicationContexts() {
        List<String> contextList = new ArrayList<String>();
        Class<? extends Object> clazz = this.getClass();
        // scan up to root of object hierarchy finding our annotation
        while (clazz != null) {
            ApplicationContext ac = clazz.getAnnotation(ApplicationContext.class);
            if (ac != null) {
                contextList.addAll(Arrays.asList(ac.value()));
            }
            clazz = clazz.getSuperclass();
        }
        springLoader = SpringLoaderFactory.initialise(contextList);
    }
    
    /**
     * Cleans up the SpringLoader
     */
    @After
    public void closeSpringLoader() {
        if (springLoader != null) {
            springLoader.close();
        }
    }

    /**
     * @return the SpringLoader to use in all subclasses of this.
     */
    public SpringLoader getSpringLoader() {
        return springLoader;
    }
}
