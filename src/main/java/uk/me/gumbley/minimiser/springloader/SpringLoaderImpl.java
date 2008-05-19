package uk.me.gumbley.minimiser.springloader;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * An implementation of the SpringLoader that uses Spring, obviously!
 * Whether we'd ever have other impls is dubious, but we need to maintain
 * interface/implementation separation.
 * 
 * @author matt
 *
 */
public final class SpringLoaderImpl implements SpringLoader, ApplicationContextAware {
    private ApplicationContext appContext;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(final String beanId, final Class <T> beanType) {
        return (T) appContext.getBean(beanId, beanType);
    }

    /**
     * {@inheritDoc}
     */
    public void setApplicationContext(final ApplicationContext aC) throws BeansException {
        appContext = aC;
    }

    /**
     * {@inheritDoc}
     */
    public void close() {
        ((ClassPathXmlApplicationContext) appContext).close();
    }
}
