package uk.me.gumbley.minimiser.springbeanlistloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import uk.me.gumbley.minimiser.springloader.SpringLoader;

/**
 * Abstract impl of SpringBeanListLoader. Loads beans using the
 * SpringLoader from a provided list.
 * 
 * @param <T> the bean type
 * 
 * @author matt
 *
 */
public abstract class AbstractSpringBeanListLoaderImpl<T> implements SpringBeanListLoader<T> {
    private static final Logger LOGGER = Logger
            .getLogger(AbstractSpringBeanListLoaderImpl.class);
    
    private final List<String> loadedBeanNames;
    private final Map<String, T> beanMap;
    private final SpringLoader loader;
    private volatile boolean loaded;

    private final List<String> mBeanNameList;
    
    /**
     * @param springLoader the Spring loader
     * @param beanNameList the list of beans to manage.
     */
    public AbstractSpringBeanListLoaderImpl(final SpringLoader springLoader, final List<String> beanNameList) {
        this.loader = springLoader;
        mBeanNameList = beanNameList;
        beanMap = new HashMap<String, T>();
        loadedBeanNames = new ArrayList<String>();
    }
    
    @SuppressWarnings("unchecked")
    private void lazyInitialise() {
        if (loaded) {
            return;
        }
        loaded = true;
        LOGGER.info("Loading beans");
        for (final String beanName : mBeanNameList) {
            LOGGER.info("Obtaining bean '" + beanName + "'");
            try {
                final T bean = (T) loader.getBean(beanName, null);
                beanMap.put(beanName, bean);
                loadedBeanNames.add(beanName);
            } catch (final Throwable t) {
                LOGGER.warn("Could not load '" + beanName + ": " + t.getMessage(), t);
                // TODO pass this on to an injected ProblemReporter?
            }
        }
        LOGGER.info("Beans loaded");
    }
    
    /**
     * {@inheritDoc}
     */
    public final synchronized List<String> getBeanNames() {
        lazyInitialise();
        return loadedBeanNames;
    }

    /**
     * {@inheritDoc}
     */
    public final synchronized T getBean(final String beanName) {
        lazyInitialise();
        return beanMap.get(beanName);
    }
    
    /**
     * {@inheritDoc}
     */
    public final synchronized List<T> getBeans() {
        lazyInitialise();
        final List<T> list = new ArrayList<T>();
        for (String beanName : loadedBeanNames) {
            list.add(beanMap.get(beanName));
        }
        return list;
    }
}
