package uk.me.gumbley.minimiser.springloader;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple SpringLoader that just stores beans, for unit tests
 * without the overhead of the real SpringLoader. Only handles
 * singletons.
 * 
 * @author matt
 *
 */
public final class SimpleSpringLoader implements SpringLoader {
    private final Map<String, Object> mBeanStore = new HashMap<String, Object>();
    
    /**
     * {@inheritDoc}
     */
    public void addApplicationContext(final String... contextFiles) {
        throw new UnsupportedOperationException("Cannot add an application context to the SimpleSpringLoader");
    }

    /**
     * {@inheritDoc}
     */
    public void close() {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(final String beanId, final Class<T> beanType) {
        if (mBeanStore.containsKey(beanId)) {
            return (T) mBeanStore.get(beanId);
        }
        throw new IllegalStateException("SimpleSpringLoader doesn't contain a bean called '" + beanId + "'");
    }
    
    /**
     * Store an object under a given bean Id
     * @param beanId the bean Id
     * @param beanObject the object to store as this bean
     */
    public void setBean(final String beanId, final Object beanObject) {
        mBeanStore.put(beanId, beanObject);
    }
}
