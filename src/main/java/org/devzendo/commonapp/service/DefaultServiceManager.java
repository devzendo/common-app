/**
 * Copyright (C) 2008-2012 Matt Gumbley, DevZendo.org <http://devzendo.org>
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.devzendo.commonapp.service;

import org.apache.log4j.Logger;
import org.devzendo.commonapp.lifecycle.Lifecycle;
import org.devzendo.commonapp.lifecycle.ShutdownPreparable;
import org.devzendo.commonapp.spring.springbeanlistloader.AbstractSpringBeanListLoaderImpl;
import org.devzendo.commonapp.spring.springloader.SpringLoader;
import org.devzendo.commoncode.patterns.observer.ObserverList;

import java.util.List;

public class DefaultServiceManager extends AbstractSpringBeanListLoaderImpl<Service> implements ServiceManager {
    private static final Logger LOGGER = Logger.getLogger(DefaultServiceManager.class);

    private ObserverList<ServiceEvent> serviceListeners = new ObserverList<ServiceEvent>();

    /**
     * @param springLoader the Spring loader
     * @param serviceBeanNames the list of Service beans to manage.
     */
    public DefaultServiceManager(final SpringLoader springLoader, final List<String> serviceBeanNames) {
        super(springLoader, serviceBeanNames);
    }

    public void startup() {
        LOGGER.info("ServiceManager starting Service beans...");
        for (final String beanName : getBeanNames()) {
            LOGGER.info("Starting Service bean '" + beanName + "'");
            try {
                final Service serviceBean = getBean(beanName);
                if (serviceBean != null) {
                    serviceListeners.eventOccurred(new ServiceEvent(ServiceEventType.SERVICE_STARTING, beanName, "Starting"));
                    serviceBean.startup();
                    serviceListeners.eventOccurred(new ServiceEvent(ServiceEventType.SERVICE_STARTED, beanName, "Started"));
                }
            } catch (final RuntimeException re) {
                LOGGER.warn("Could not start up '" + beanName + ": " + re.getMessage(), re);
            }
        }
        LOGGER.info("End of ServiceManager startup");
    }

    public void shutdown() {
        LOGGER.info("ServiceManager preparing to shut down Service beans...");
        for (int i = getBeanNames().size() - 1; i >= 0; i--) {
            final String beanName = getBeanNames().get(i);
            LOGGER.info("Preparing to shut down Service bean '" + beanName + "'");
            try {
                final Service serviceBean = getBean(beanName);
                if (serviceBean != null) {
                    serviceBean.prepareForShutdown();
                }
            } catch (final RuntimeException re) {
                LOGGER.warn("Could not prepare '" + beanName + " for shut down : " + re.getMessage(), re);
            }
        }
        LOGGER.info("ServiceManager shutting down Service beans...");
        for (int i = getBeanNames().size() - 1; i >= 0; i--) {
            final String beanName = getBeanNames().get(i);
            LOGGER.info("Shutting down Service bean '" + beanName + "'");
            try {
                final Service serviceBean = getBean(beanName);
                if (serviceBean != null) {
                    serviceBean.shutdown();
                }
            } catch (final RuntimeException re) {
                LOGGER.warn("Could not shut down '" + beanName + ": " + re.getMessage(), re);
            }
        }
        LOGGER.info("End of ServiceManager shutdown");
    }

    public void addServiceListener(final ServiceListener listener) {
        serviceListeners.addObserver(listener);
    }

    public void removeServiceListener(final ServiceListener listener) {
        serviceListeners.removeListener(listener);
    }
}
