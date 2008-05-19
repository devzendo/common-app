package uk.me.gumbley.minimiser.springloader;

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
     * Close the SpringLoader; free resources 
     */
    void close();
}
