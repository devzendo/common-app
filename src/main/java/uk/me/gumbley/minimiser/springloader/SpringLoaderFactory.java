package uk.me.gumbley.minimiser.springloader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Factory / helper class for obtaining a SpringLoader
 * 
 * @author matt
 *
 */
public final class SpringLoaderFactory {
    private static SpringLoader instance;
    
    /**
     * Thou shalt not instantiate
     */
    private SpringLoaderFactory() {
        // do nothing
    }

    /**
     * @return the singleton instance of the SpringLoader, instantiated
     * with no application contexts.
     * This should be the only singleton in the system.
     */
    public static synchronized SpringLoader getInstance() {
        if (instance == null) {
            initAppContext(new ArrayList <String>());
        }
        return instance;
    }

    /**
     * @param contextFiles a list of application context files with which
     * to initialise the SpringLoader
     * @return the SpringLoader
     */
    public static SpringLoader initialise(final List <String> contextFiles) {
        return initAppContext(contextFiles);
    }

    /**
     * @param contextFiles a list of application context files with which
     * to initialise the SpringLoader
     * @return the SpringLoader
     */
    public static SpringLoader initialise(final String... contextFiles) {
        return initAppContext(Arrays.asList(contextFiles));
    }

    private static synchronized SpringLoader initAppContext(final List <String> contextFiles) {
        // add ourselves in to the list of application contexts
        List <String> expandContextFiles = new ArrayList <String>(contextFiles);
        expandContextFiles.add("uk/me/gumbley/minimiser/springloader/SpringLoader.xml");
        // Can't just stash the newly created ClassPathXmlApplicationContext
        // in the SpringLoaderImpl, since this is a static method, so that's
        // why we're ApplicationContextAware.
        ApplicationContext aC = new ClassPathXmlApplicationContext(expandContextFiles.toArray(new String[0]));
        // use the application context to instantiate a SpringLoader (one of these classes)
        instance = (SpringLoader) aC.getBean("springLoader");
        return instance;
    }
}
