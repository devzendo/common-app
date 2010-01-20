package org.devzendo.minimiser.prefs;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;

/**
 * A Spring FactoryBean for the Prefs Singleton
 * @author matt
 *
 */
public final class PrefsFactory implements FactoryBean {
    private static final Logger LOGGER = Logger.getLogger(PrefsFactory.class);
    private Prefs factoryPrefs;

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
    public Class<?> getObjectType() {
        return DefaultPrefsImpl.class;
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
        factoryPrefs = new DefaultPrefsImpl(prefsPath);
    }
    

    /**
     * Factory population method, used by unit tests
     * @param prefs a previously instantiated Prefs object
     */
    public void setPrefs(final Prefs prefs) {
        LOGGER.debug(String.format("PrefsFactory being populated with %s as prefs object", prefs.getClass().getSimpleName()));
        factoryPrefs = prefs;
    }
}
