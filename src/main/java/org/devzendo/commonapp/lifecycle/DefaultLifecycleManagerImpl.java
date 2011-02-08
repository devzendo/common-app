/**
 * Copyright (C) 2008-2010 Matt Gumbley, DevZendo.org <http://devzendo.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.devzendo.commonapp.lifecycle;

import java.util.List;

import org.apache.log4j.Logger;
import org.devzendo.commonapp.spring.springbeanlistloader.AbstractSpringBeanListLoaderImpl;
import org.devzendo.commonapp.spring.springloader.SpringLoader;

/**
 * Default impl of LifecycleManager. Loads Lifecycle implementors using the
 * SpringLoader from a provided list.
 * 
 * @author matt
 *
 */
public final class DefaultLifecycleManagerImpl extends AbstractSpringBeanListLoaderImpl<Lifecycle> implements LifecycleManager {
    private static final Logger LOGGER = Logger
            .getLogger(DefaultLifecycleManagerImpl.class);
    
    /**
     * @param springLoader the Spring loader
     * @param lifecycleBeanNames the list of Lifecycle beans to manage.
     */
    public DefaultLifecycleManagerImpl(final SpringLoader springLoader, final List<String> lifecycleBeanNames) {
        super(springLoader, lifecycleBeanNames);
    }
    
    /**
     * {@inheritDoc}
     */
    public void startup() {
        LOGGER.info("LifecycleManager starting Lifecycle beans...");
        for (final String beanName : getBeanNames()) {
            LOGGER.info("Starting Lifecycle bean '" + beanName + "'");
            try {
                final Lifecycle lifecycleBean = getBean(beanName);
                if (lifecycleBean != null) {
                    lifecycleBean.startup();
                }
            } catch (final RuntimeException re) {
                LOGGER.warn("Could not start up '" + beanName + ": " + re.getMessage(), re);
            }
        }
        LOGGER.info("End of LifecycleManager startup");
    }

    /**
     * {@inheritDoc}
     */
    public void shutdown() {
        LOGGER.info("LifecycleManager shutting down Lifecycle beans...");
        for (int i = getBeanNames().size() - 1; i >= 0; i--) {
            final String beanName = getBeanNames().get(i);
            LOGGER.info("Shutting down Lifecycle bean '" + beanName + "'");
            try {
                final Lifecycle lifecycleBean = getBean(beanName);
                if (lifecycleBean != null) {
                    lifecycleBean.shutdown();
                }
            } catch (final RuntimeException re) {
                LOGGER.warn("Could not shut down '" + beanName + ": " + re.getMessage(), re);
            }
        }
        LOGGER.info("End of LifecycleManager shutdown");
    }
}
