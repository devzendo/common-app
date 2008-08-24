package uk.me.gumbley.minimiser.springbeanlistloader;

import java.util.List;
import uk.me.gumbley.minimiser.springloader.SpringLoader;

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
