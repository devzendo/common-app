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
import org.devzendo.commonapp.spring.springbeanlistloader.AbstractSpringBeanListLoaderImpl;
import org.devzendo.commonapp.spring.springloader.SpringLoader;
import org.devzendo.commoncode.patterns.observer.ObserverList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class DefaultServiceManager extends AbstractSpringBeanListLoaderImpl<Service> implements ServiceManager {
    private static final Logger LOGGER = Logger.getLogger(DefaultServiceManager.class);

    private final ObserverList<ServiceStatus> serviceListeners = new ObserverList<ServiceStatus>();
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
    private final Thread thread;
    private volatile boolean stopThread = false;
    private final Map<String, ServiceStatus> serviceStatusMap = new HashMap<String, ServiceStatus>();

    /**
     * @param springLoader the Spring loader
     * @param serviceBeanNames the list of Service beans to manage.
     */
    public DefaultServiceManager(final SpringLoader springLoader, final List<String> serviceBeanNames) {
        super(springLoader, serviceBeanNames);
        synchronized (serviceStatusMap) {
            for (final String serviceName : serviceBeanNames) {
                serviceStatusMap.put(serviceName, new ServiceStatus(ServiceState.SERVICE_BEFORESTARTUP, serviceName, "Before startup", null));
            }
        }
        thread = new Thread(new DefaultServiceManagerRunnable());
        thread.setDaemon(true);
        thread.setName("Service Manager");
        thread.start();
    }

    private class DefaultServiceManagerRunnable implements Runnable {
        public void run() {
            LOGGER.info("Service Manager thread active");
            while (!stopThread) {
                try {
                    queue.take().run();
                } catch (final InterruptedException e) {
                    LOGGER.debug("Interrupted: " + e.getMessage(), e);
                } catch (final RuntimeException e) {
                    LOGGER.warn("Problem: " + e.getMessage(), e);
                }
            }
            LOGGER.info("Service Manager thread terminated");
        }
    }

    private class DefaultServiceManagerProxy implements ServiceManagerProxy {
        private final String serviceBeanName;

        public DefaultServiceManagerProxy(final String serviceBeanName) {
            this.serviceBeanName = serviceBeanName;
        }

        public void inactive(final String description) {
            enqueue(new Runnable() {
                public void run() {
                    emitServiceStatus(ServiceState.SERVICE_INACTIVE, serviceBeanName, description, null);
                }
            });
        }

        public void active(final String description) {
            enqueue(new Runnable() {
                public void run() {
                    emitServiceStatus(ServiceState.SERVICE_ACTIVE, serviceBeanName, description, null);
                }
            });
        }

        public void changeDescription(final String description) {
            enqueue(new Runnable() {
                public void run() {
                    emitServiceStatus(getStatus(serviceBeanName).getState(), serviceBeanName, description, null);
                }
            });
        }
    }

    private void emitServiceStatus(final ServiceState serviceState, final String serviceBeanName, final String description, final Exception fault) {
        final ServiceStatus serviceStatus = new ServiceStatus(serviceState, serviceBeanName, description, fault);
        LOGGER.info(serviceStatus);
        synchronized (serviceStatusMap) {
            serviceStatusMap.put(serviceBeanName, serviceStatus);
        }
        serviceListeners.eventOccurred(serviceStatus);
    }

    private void enqueue(final Runnable runnable) {
        try {
            queue.put(runnable);
        } catch (final InterruptedException e) {
            LOGGER.warn("Interrupted: " + e.getMessage(), e);
        }
    }

    private void sync() {
        final CountDownLatch latch = new CountDownLatch(1);
        enqueue(new Runnable() {
            public void run() {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (final InterruptedException e) {
            LOGGER.warn("Interrupted: " + e.getMessage(), e);
        }
    }

    public void startup() {
        enqueue(new Runnable() {
            public void run() {
                LOGGER.info("ServiceManager starting Service beans...");
                for (final String beanName : getBeanNames()) {
                    LOGGER.info("Starting Service bean '" + beanName + "'");
                    try {
                        final Service serviceBean = getBean(beanName);
                        if (serviceBean != null) {
                            emitServiceStatus(ServiceState.SERVICE_STARTING, beanName, "Starting", null);
                            serviceBean.startup(new DefaultServiceManagerProxy(beanName));
                            emitServiceStatus(ServiceState.SERVICE_STARTED, beanName, "Started", null);
                        }
                    } catch (final RuntimeException re) {
                        LOGGER.warn("Could not start up '" + beanName + "': " + re.getMessage(), re);
                        emitServiceStatus(ServiceState.SERVICE_FAULTY, beanName, "Fault: " + re.getMessage(), re);
                    }
                }
                LOGGER.info("End of ServiceManager startup");
            }
        });
        sync();
    }

    public void shutdown() {
        enqueue(new Runnable() {
            public void run() {
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
                        LOGGER.warn("Could not prepare '" + beanName + "' for shut down : " + re.getMessage(), re);
                    }
                }
                LOGGER.info("ServiceManager shutting down Service beans...");
                for (int i = getBeanNames().size() - 1; i >= 0; i--) {
                    final String beanName = getBeanNames().get(i);
                    LOGGER.info("Shutting down Service bean '" + beanName + "'");
                    final Service serviceBean = getBean(beanName);
                    if (serviceBean != null) {
                        try {
                            emitServiceStatus(ServiceState.SERVICE_STOPPING, beanName, "Stopping", null);
                            serviceBean.shutdown();
                        } catch (final RuntimeException re) {
                            LOGGER.warn("Could not shut down '" + beanName + "': " + re.getMessage(), re);
                        }
                        emitServiceStatus(ServiceState.SERVICE_STOPPED, beanName, "Stopped", null);
                    }
                }
                LOGGER.info("End of ServiceManager shutdown");
            }
        });
        sync();
        stopThread = true;
        thread.interrupt();
    }

    public void addServiceListener(final ServiceListener listener) {
        serviceListeners.addObserver(listener);
    }

    public void removeServiceListener(final ServiceListener listener) {
        serviceListeners.removeListener(listener);
    }

    public List<ServiceStatus> getStatuses() {
        final List<String> beanNames = getBeanNames();
        final List<ServiceStatus> serviceStatuses = new ArrayList<ServiceStatus>(beanNames.size());
        for (String beanName : beanNames) {
            serviceStatuses.add(getStatus(beanName));
        }
        return serviceStatuses;
    }

    public ServiceStatus getStatus(final String serviceName) {
        synchronized (serviceStatusMap) {
            return serviceStatusMap.get(serviceName);
        }
    }
}
