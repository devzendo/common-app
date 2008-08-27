package uk.me.gumbley.minimiser.springbeanlistloader;

import java.util.List;

/**
 * Loads a list of beans from the application context.
 * @param <T> the bean type
 * 
 * @author matt
 *
 */
public interface SpringBeanListLoader<T> {

    /**
     * Obtain a list of bean names
     * @return the list of bean names
     */
    List<String> getBeanNames();

    /**
     * Obtain a bean by its name
     * @param beanName the bean name
     * @return a bean
     */
    T getBean(String beanName);
    
    /**
     * Obtain the list of loaded of beans
     * @return a list of loaded beans, in the order they were loaded
     */
    List<T> getBeans();
}
