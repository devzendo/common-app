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
    
    private final List<String> beanNames;
    private final Map<String, T> beanMap;
    private final SpringLoader loader;
    
    /**
     * @param springLoader the Spring loader
     * @param beanNameList the list of beans to manage.
     */
    public AbstractSpringBeanListLoaderImpl(final SpringLoader springLoader, final List<String> beanNameList) {
        this.loader = springLoader;
        beanMap = new HashMap<String, T>();
        this.beanNames = instantiateBeans(beanNameList);
    }
    
    @SuppressWarnings("unchecked")
    private List<String> instantiateBeans(final List<String> originalBeanNames) {
        final List<String> loadedNames = new ArrayList<String>(); 
        LOGGER.info("Loading beans");
        for (final String beanName : originalBeanNames) {
            LOGGER.info("Obtaining bean '" + beanName + "'");
            try {
                final T bean = (T) loader.getBean(beanName, null);
                beanMap.put(beanName, bean);
                loadedNames.add(beanName);
            } catch (final RuntimeException re) {
                LOGGER.warn("Could not load '" + beanName + ": " + re.getMessage(), re);
                // TODO pass this on to an injected ProblemReporter?
            }
        }
        LOGGER.info("Beans loaded");
        return loadedNames;
    }

    /**
     * {@inheritDoc}
     */
    public final List<String> getBeanNames() {
        return beanNames;
    }

    /**
     * {@inheritDoc}
     */
    public final T getBean(final String beanName) {
        return beanMap.get(beanName);
    }
    
    /**
     * {@inheritDoc}
     */
    public final List<T> getBeans() {
        final List<T> list = new ArrayList<T>();
        for (String beanName : beanNames) {
            list.add(beanMap.get(beanName));
        }
        return list;
    }
}
