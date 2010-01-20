package org.devzendo.minimiser.springbeanlistloader;

import java.util.List;

import org.devzendo.minimiser.springloader.SpringLoader;

/**
 * @author matt
 *
 */
public class ThingySpringBeanListLoader extends AbstractSpringBeanListLoaderImpl<Thingy> {
    /**
     * @param springLoader the spring loader
     * @param beanNames the bean names
     */
    public ThingySpringBeanListLoader(final SpringLoader springLoader, final List<String> beanNames) {
        super(springLoader, beanNames);
    }
}
