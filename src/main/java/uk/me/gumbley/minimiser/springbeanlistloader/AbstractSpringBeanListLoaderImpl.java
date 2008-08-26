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
 * @param <B> the bean type
 * 
 * @author matt
 *
 */
public abstract class AbstractSpringBeanListLoaderImpl<B> implements SpringBeanListLoader<B> {
    private static final Logger LOGGER = Logger
            .getLogger(AbstractSpringBeanListLoaderImpl.class);
    
    private final List<String> beanNames;
    private final Map<String, B> beanMap;
    private final SpringLoader loader;
    
    /**
     * @param springLoader the Spring loader
     * @param lifecycleBeanNames the list of Lifecycle beans to manage.
     */
    public AbstractSpringBeanListLoaderImpl(final SpringLoader springLoader, final List<String> lifecycleBeanNames) {
        this.loader = springLoader;
        beanMap = new HashMap<String, B>();
        this.beanNames = instantiateBeans(lifecycleBeanNames);
    }
    
    private List<String> instantiateBeans(final List<String> originalBeanNames) {
        final List<String> loadedNames = new ArrayList<String>(); 
        LOGGER.info("Loading beans");
        for (final String beanName : originalBeanNames) {
            LOGGER.info("Obtaining bean '" + beanName + "'");
            try {
                final B bean = loader.getBean(beanName, null);
                beanMap.put(beanName, bean);
                loadedNames.add(beanName);
            } catch (final RuntimeException re) {
                LOGGER.warn("Could not load '" + beanName + ": " + re.getMessage(), re);
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
    public final B getBean(final String beanName) {
        return beanMap.get(beanName);
    }
    
    /**
     * {@inheritDoc}
     */
    public final List<B> getBeans() {
        final List<B> list = new ArrayList<B>();
        for (String beanName : beanNames) {
            list.add(beanMap.get(beanName));
        }
        return list;
    }
}
