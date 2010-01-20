package org.devzendo.minimiser.springloader;

/**
 * An abstraction around the Spring DI container
 * 
 * @author matt
 *
 */
public interface SpringLoader {
    /**
     * @param <T> the type of object to return 
     * @param beanId the bean id from the application context file(s)
     * @param beanType the type of the object to return
     * @return an instantiated object
     */
    <T> T getBean(String beanId, Class<T> beanType);
    
    /**
     * Add an array of other application context files to the
     * SpringLoader, e.g. by the plugin manager. 
     * @param contextFiles an array of application context
     * files, as resource paths.
     */
    void addApplicationContext(final String ... contextFiles);
    
    /**
     * Close the SpringLoader; free resources 
     */
    void close();
}
