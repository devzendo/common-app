package uk.me.gumbley.minimiser.prefs;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;

/**
 * A Spring FactoryBean for the Prefs Singleton
 * @author matt
 *
 */
public final class PrefsFactory implements FactoryBean {
    private static final Logger LOGGER = Logger.getLogger(PrefsFactory.class);
    private IPrefs factoryPrefs;

    /**
     * {@inheritDoc}
     */
    public Object getObject() throws Exception {
        LOGGER.debug(String.format("PrefsFactory returning %s as prefs object", factoryPrefs));
        return factoryPrefs;
    }

    /**
     * {@inheritDoc}
     */
    public Class getObjectType() {
        return Prefs.class;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isSingleton() {
        return true;
    }
    
    /**
     * Factory population method
     * @param prefsPath the path of the prefs to return as a Singleton
     */
    public void setPrefs(final String prefsPath) {
        LOGGER.debug(String.format("PrefsFactory being populated with %s as prefs object", prefsPath));
        factoryPrefs = new Prefs(prefsPath);
    }
}
