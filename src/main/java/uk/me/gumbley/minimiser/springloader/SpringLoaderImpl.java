package uk.me.gumbley.minimiser.springloader;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringLoaderImpl implements SpringLoader, ApplicationContextAware {
    private ApplicationContext appContext;

    public <T> T getBean(final String beanId, final Class <T> beanType) {
        return (T) appContext.getBean(beanId, beanType);
    }

    public void setApplicationContext(ApplicationContext aC) throws BeansException {
        appContext = aC;
    }

    public void close() {
        ((ClassPathXmlApplicationContext) appContext).close();
    }
}
