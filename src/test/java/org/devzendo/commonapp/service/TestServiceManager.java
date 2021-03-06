package org.devzendo.commonapp.service;

import org.devzendo.commonapp.spring.springloader.ApplicationContext;
import org.devzendo.commonapp.spring.springloader.SpringLoaderUnittestCase;
import org.devzendo.commonapp.util.OrderMonitor;
import org.devzendo.commoncode.concurrency.ThreadUtils;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Copyright (C) 2008-2012 Matt Gumbley, DevZendo.org http://devzendo.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@ApplicationContext("org/devzendo/commonapp/service/ServiceTestCase.xml")
public class TestServiceManager extends SpringLoaderUnittestCase {
    private ServiceManager serviceManager;

    public void getSimpleTestPrerequisites() {
        serviceManager = getSpringLoader().getBean("serviceManager", ServiceManager.class);
    }

    @Test(timeout = 1000)
    public void haveServiceManager() {
        getSimpleTestPrerequisites();

        Assert.assertNotNull(serviceManager);
    }

    /**
     * this service manager has a bad bean - nonexistant
     */
    @Test(timeout = 1000)
    public void dontStartupBadBeans() {
        serviceManager = getSpringLoader().getBean("badLoadServiceManager", ServiceManager.class);
        Assert.assertNotNull(serviceManager);
        Assert.assertEquals(0, serviceManager.getStatuses().size());
    }

    @Test(timeout = 1000)
    public void startupStartsUpAndShutdownShutsDown() {
        getSimpleTestPrerequisites();

        final Service one = getSpringLoader().getBean("one", Service.class);
        final StubService oneService = (StubService) one;
        Assert.assertEquals("ctor", oneService.getState());

        final Service two = getSpringLoader().getBean("two", Service.class);
        final StubService twoService = (StubService) two;
        Assert.assertEquals("ctor", twoService.getState());
        Assert.assertFalse(twoService.wasPrepareShutdownCalled());

        serviceManager.startup();

        Assert.assertEquals("started", twoService.getState());
        Assert.assertFalse(twoService.wasPrepareShutdownCalled());

        serviceManager.shutdown();

        Assert.assertEquals("shut down", twoService.getState());
        Assert.assertTrue(twoService.wasPrepareShutdownCalled());
    }

    /**
     * Tests for correct sequencing of startup and shutdown (forward and
     * reverse.
     */
    @Test(timeout = 2000)
    public void startupAndShutdownSequence() {
        serviceManager = getSpringLoader().getBean("orderingServiceManager", ServiceManager.class);

        final OrderMonitor orderMonitor = getSpringLoader().getBean("orderMonitor", OrderMonitor.class);
        Assert.assertNotNull(orderMonitor);

        final List<String> initialOrdering = orderMonitor.getOrdering();
        Assert.assertEquals(0, initialOrdering.size());

        serviceManager.startup();

        final List<String> startupOrdering = orderMonitor.getOrdering();
        Assert.assertEquals(3, startupOrdering.size());
        Assert.assertEquals("a startup", startupOrdering.get(0));
        Assert.assertEquals("b startup", startupOrdering.get(1));
        Assert.assertEquals("c startup", startupOrdering.get(2));

        orderMonitor.reset();

        serviceManager.shutdown();
        ThreadUtils.waitNoInterruption(500); // give it a little time to get services into the state we expect.

        final List<String> shutdownOrdering = orderMonitor.getOrdering();
        Assert.assertEquals(6, shutdownOrdering.size());
        Assert.assertEquals("c prepareForShutdown", shutdownOrdering.get(0));
        Assert.assertEquals("b prepareForShutdown", shutdownOrdering.get(1));
        Assert.assertEquals("a prepareForShutdown", shutdownOrdering.get(2));
        Assert.assertEquals("c shutdown", shutdownOrdering.get(3));
        Assert.assertEquals("b shutdown", shutdownOrdering.get(4));
        Assert.assertEquals("a shutdown", shutdownOrdering.get(5));
    }

    @Test(timeout = 2000)
    public void normalStartupAndShutdownEventsAreEmitted() {
        getSimpleTestPrerequisites();
        final ServiceListener listener = EasyMock.createStrictMock(ServiceListener.class);
        // startup events
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STARTING, "one", "Starting")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STARTED, "one", "Started")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STARTING, "two", "Starting")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STARTED, "two", "Started")));
        // shutdown events
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STOPPING, "two", "Stopping")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STOPPED, "two", "Stopped")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STOPPING, "one", "Stopping")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STOPPED, "one", "Stopped")));
        EasyMock.replay(listener);
        serviceManager.addServiceListener(listener);

        serviceManager.startup();
        serviceManager.shutdown();
        ThreadUtils.waitNoInterruption(500); // give it a little time to get services into the state we expect.

        EasyMock.verify(listener);
    }

    @Test(timeout = 2000)
    public void faultingStartupEvent() {
        serviceManager = getSpringLoader().getBean("faultingStartupServiceManager", ServiceManager.class);

        final ServiceListener listener = EasyMock.createStrictMock(ServiceListener.class);
        // startup events
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STARTING, "faultStartup", "Starting")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_FAULTY, "faultStartup", "Fault: some exception", FaultStartupService.FAULT_EXCEPTION)));
        // shutdown events
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STOPPING, "faultStartup", "Stopping")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STOPPED, "faultStartup", "Stopped")));
        EasyMock.replay(listener);
        serviceManager.addServiceListener(listener);

        serviceManager.startup();
        serviceManager.shutdown();
        ThreadUtils.waitNoInterruption(500); // give it a little time to get services into the state we expect.

        EasyMock.verify(listener);
    }

    @Test(timeout = 2000)
    public void faultingShutdownEvent() {
        serviceManager = getSpringLoader().getBean("faultingShutdownServiceManager", ServiceManager.class);

        final ServiceListener listener = EasyMock.createStrictMock(ServiceListener.class);
        // startup events
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STARTING, "faultShutdown", "Starting")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STARTED, "faultShutdown", "Started")));
        // shutdown events
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STOPPING, "faultShutdown", "Stopping")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STOPPED, "faultShutdown", "Stopped")));
        EasyMock.replay(listener);
        serviceManager.addServiceListener(listener);

        serviceManager.startup();
        serviceManager.shutdown();
        ThreadUtils.waitNoInterruption(500); // give it a little time to get services into the state we expect.

        EasyMock.verify(listener);
    }

    @Test(timeout = 2000)
    public void serviceMethodsCalledByServiceControlThread() {
        serviceManager = getSpringLoader().getBean("threadingServiceManager", ServiceManager.class);

        final ThreadService threadService = getSpringLoader().getBean("thread", ThreadService.class);

        serviceManager.startup();
        serviceManager.shutdown();
        ThreadUtils.waitNoInterruption(500); // give it a little time to get services into the state we expect.

        Assert.assertTrue(threadService.startupOnServiceControlThread);
        Assert.assertTrue(threadService.prepareForShutdownOnServiceControlThread);
        Assert.assertTrue(threadService.shutdownOnServiceControlThread);
    }

    @Test(timeout = 2000)
    public void serviceCanBecomeInactiveOnStartup() {
        serviceManager = getSpringLoader().getBean("inactiveServiceManager", ServiceManager.class);

        final ServiceListener listener = EasyMock.createStrictMock(ServiceListener.class);
        // startup events
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STARTING, "inactiveStartup", "Starting")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STARTED, "inactiveStartup", "Started")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_INACTIVE, "inactiveStartup", "Waiting for Godot")));
        // shutdown events
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STOPPING, "inactiveStartup", "Stopping")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STOPPED, "inactiveStartup", "Stopped")));
        EasyMock.replay(listener);
        serviceManager.addServiceListener(listener);

        serviceManager.startup();
        serviceManager.shutdown();
        ThreadUtils.waitNoInterruption(500); // give it a little time to get services into the state we expect.

        EasyMock.verify(listener);
    }

    @Test(timeout = 2000)
    public void serviceCanBecomeInactiveThenIndicateActiveOnStartup() {
        serviceManager = getSpringLoader().getBean("inactiveThenActiveServiceManager", ServiceManager.class);

        final InactiveThenActiveService inactiveThenActiveService = getSpringLoader().getBean("inactiveThenActive", InactiveThenActiveService.class);

        final ServiceListener listener = EasyMock.createStrictMock(ServiceListener.class);
        // startup events
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STARTING, "inactiveThenActive", "Starting")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STARTED, "inactiveThenActive", "Started")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_INACTIVE, "inactiveThenActive", "Short wait")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_ACTIVE, "inactiveThenActive", "Finally started")));
        // shutdown events
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STOPPING, "inactiveThenActive", "Stopping")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STOPPED, "inactiveThenActive", "Stopped")));
        EasyMock.replay(listener);
        serviceManager.addServiceListener(listener);

        serviceManager.startup();
        inactiveThenActiveService.waitForFinish();
        serviceManager.shutdown();
        ThreadUtils.waitNoInterruption(500); // give it a little time to get services into the state we expect.

        EasyMock.verify(listener);
    }

    @Test(timeout = 2000)
    public void serviceStatusesCanBeObtained() {
        serviceManager = getSpringLoader().getBean("getStatusesServiceManager", ServiceManager.class);

        final ServiceStatus initialInactiveStartupStatus = new ServiceStatus(ServiceState.SERVICE_BEFORESTARTUP, "inactiveStartup", "Before startup", null);
        final ServiceStatus initialFaultStatus = new ServiceStatus(ServiceState.SERVICE_BEFORESTARTUP, "faultStartup", "Before startup", null);
        final ServiceStatus initialOneStatus = new ServiceStatus(ServiceState.SERVICE_BEFORESTARTUP, "one", "Before startup", null);
        final List<ServiceStatus> initialStatuses = serviceManager.getStatuses();
        Assert.assertEquals(initialInactiveStartupStatus, initialStatuses.get(0));
        Assert.assertEquals(initialFaultStatus, initialStatuses.get(1));
        Assert.assertEquals(initialOneStatus, initialStatuses.get(2));

        serviceManager.startup();
        ThreadUtils.waitNoInterruption(500); // give it a little time to get services into the state we expect.

        final ServiceStatus inactiveStartupStatus = new ServiceStatus(ServiceState.SERVICE_INACTIVE, "inactiveStartup", "Waiting for Godot", null);
        final ServiceStatus faultStatus = new ServiceStatus(ServiceState.SERVICE_FAULTY, "faultStartup", "Fault: some exception", FaultStartupService.FAULT_EXCEPTION);
        final ServiceStatus oneStatus = new ServiceStatus(ServiceState.SERVICE_STARTED, "one", "Started", null);
        final List<ServiceStatus> statuses = serviceManager.getStatuses();
        Assert.assertEquals(inactiveStartupStatus, statuses.get(0));
        Assert.assertEquals(faultStatus, statuses.get(1));
        Assert.assertEquals(oneStatus, statuses.get(2));

        Assert.assertEquals(inactiveStartupStatus, serviceManager.getStatus("inactiveStartup"));
        Assert.assertEquals(faultStatus, serviceManager.getStatus("faultStartup"));
        Assert.assertEquals(oneStatus, serviceManager.getStatus("one"));
        serviceManager.shutdown();
    }

    @Test(timeout = 2000)
    public void serviceCanChangeDescription() {
        serviceManager = getSpringLoader().getBean("changeDescriptionServiceManager", ServiceManager.class);

        final ChangeDescriptionService changeDescriptionService = getSpringLoader().getBean("changeDescription", ChangeDescriptionService.class);

        final ServiceListener listener = EasyMock.createStrictMock(ServiceListener.class);
        // startup events
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STARTING, "changeDescription", "Starting")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STARTED, "changeDescription", "Started")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_ACTIVE, "changeDescription", "I'm alive")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_ACTIVE, "changeDescription", "Eating food")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_INACTIVE, "changeDescription", "Asleep")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_INACTIVE, "changeDescription", "Dreaming")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_INACTIVE, "changeDescription", "Still sleeping")));
        // shutdown events
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STOPPING, "changeDescription", "Stopping")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STOPPED, "changeDescription", "Stopped")));
        EasyMock.replay(listener);
        serviceManager.addServiceListener(listener);

        serviceManager.startup();
        changeDescriptionService.waitForFinish();
        serviceManager.shutdown();
        ThreadUtils.waitNoInterruption(500); // give it a little time to get services into the state we expect.

        EasyMock.verify(listener);
    }

    @Test(timeout = 1000)
    public void servicesCanBeObtainedByNameAndClass() {
        getSimpleTestPrerequisites();

        final Service one = getSpringLoader().getBean("one", Service.class);
        final StubService oneService = (StubService) one;

        serviceManager.startup();

        final StubService obtained = serviceManager.getService("one", StubService.class);
        Assert.assertSame(oneService, obtained);

        serviceManager.shutdown();
    }
}