package uk.me.gumbley.minimiser.springloader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;

import uk.me.gumbley.minimiser.springloader.SpringLoader;
import uk.me.gumbley.minimiser.springloader.SpringLoaderImpl;

/**
 * A base class for Junit 4 test cases that specify a set of Spring application context XML files to be used by the
 * ClassPathApplicationContextLoader using an annotation.
 * 
 * @author matt
 */
public class SpringLoaderTestCase {
    private SpringLoader springLoader;

    @Before
    public void initApplicationContexts() {
        List <String> contextList = new ArrayList <String>();
        Class <? extends Object> clazz = this.getClass();
        // scan up to root of object hierarchy finding our annotation
        while (clazz != null) {
            ApplicationContext ac = clazz.getAnnotation(ApplicationContext.class);
            if (ac != null) {
                contextList.addAll(Arrays.asList(ac.value()));
            }
            clazz = clazz.getSuperclass();
        }
        springLoader = SpringLoaderImpl.initialise(contextList);
    }

    public SpringLoader getSpringLoader() {
        return springLoader;
    }
}
