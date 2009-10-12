package uk.me.gumbley.minimiser.springloader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import uk.me.gumbley.commoncode.string.StringUtils;
import uk.me.gumbley.minimiser.logging.LoggingTestCase;

/**
 * A base class for Junit 4 test cases that specify a set of Spring application
 * context XML files to be used by the ClassPathApplicationContextLoader using
 * the
 * 
 * @ApplicationContext annotation.
 * @author matt
 */
public abstract class SpringLoaderUnittestCase extends LoggingTestCase {
    private static final Logger LOGGER = Logger
            .getLogger(SpringLoaderUnittestCase.class);
    private SpringLoader springLoader;

    /**
     * Set up the SpringLoader with all application context files
     * given in any annotations.
     * 
     * @ApplicatonContext annotations on this test class, and any in its
     *                    inheritance hierarchy.
     */
    @Before
    public final void initApplicationContexts() {
        LOGGER.info(">>> initApplicationContexts");
        final List<String> contextList = new ArrayList<String>();
        // add all contexts provided by subclass methods, not
        // annotations
        contextList.addAll(getRuntimeApplicationContexts());
        // scan up to root of object hierarchy finding our annotation
        Class<? extends Object> clazz = this.getClass();
        while (clazz != null) {
            final ApplicationContext ac = clazz.getAnnotation(ApplicationContext.class);
            if (ac != null) {
                contextList.addAll(Arrays.asList(ac.value()));
            }
            clazz = clazz.getSuperclass();
        }
        LOGGER.info(String.format("Initialising SpringLoader with contexts [%s]", 
            StringUtils.join(contextList, ", ")));
        springLoader = SpringLoaderFactory.initialise(contextList);
        LOGGER.info("<<< initApplicationContexts");
    }
    
    /**
     * @return any paths to application contexts that must be
     * provided at run-time, rather than via annotations
     */
    @SuppressWarnings("unchecked")
    protected List<String> getRuntimeApplicationContexts() {
        return Collections.EMPTY_LIST;
    }

    /**
     * Cleans up the SpringLoader
     */
    @After
    public final void closeSpringLoader() {
        LOGGER.info(">>> closeSpringLoader");
        if (springLoader != null) {
            springLoader.close();
        }
        LOGGER.info("<<< closeSpringLoader");
    }

    /**
     * @return the SpringLoader to use in all subclasses of this.
     */
    public final SpringLoader getSpringLoader() {
        return springLoader;
    }
}
