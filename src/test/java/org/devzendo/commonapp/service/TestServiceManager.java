package org.devzendo.commonapp.service;

import org.devzendo.commonapp.spring.springloader.ApplicationContext;
import org.devzendo.commonapp.spring.springloader.SpringLoaderUnittestCase;
import org.devzendo.commonapp.util.OrderMonitor;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

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
    @Test(timeout = 1000)
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

        final List<String> shutdownOrdering = orderMonitor.getOrdering();
        Assert.assertEquals(6, shutdownOrdering.size());
        Assert.assertEquals("c prepareForShutdown", shutdownOrdering.get(0));
        Assert.assertEquals("b prepareForShutdown", shutdownOrdering.get(1));
        Assert.assertEquals("a prepareForShutdown", shutdownOrdering.get(2));
        Assert.assertEquals("c shutdown", shutdownOrdering.get(3));
        Assert.assertEquals("b shutdown", shutdownOrdering.get(4));
        Assert.assertEquals("a shutdown", shutdownOrdering.get(5));
    }

    @Test(timeout = 1000)
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

        EasyMock.verify(listener);
    }

    @Test(timeout = 1000)
    public void faultingStartupEvent() {
        serviceManager = getSpringLoader().getBean("faultingServiceManager", ServiceManager.class);

        final ServiceListener listener = EasyMock.createStrictMock(ServiceListener.class);
        // startup events
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STARTING, "fault", "Starting")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_FAULTY, "fault", "Fault: some exception", FaultService.FAULT_EXCEPTION)));
        // shutdown events
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STOPPING, "fault", "Stopping")));
        listener.eventOccurred(EasyMock.eq(new ServiceStatus(ServiceState.SERVICE_STOPPED, "fault", "Stopped")));
        EasyMock.replay(listener);
        serviceManager.addServiceListener(listener);

        serviceManager.startup();
        serviceManager.shutdown();

        EasyMock.verify(listener);
    }

    @Test(timeout = 1000)
    public void serviceMethodsCalledByServiceControlThread() {
        serviceManager = getSpringLoader().getBean("threadingServiceManager", ServiceManager.class);

        final ThreadService threadService = getSpringLoader().getBean("thread", ThreadService.class);

        serviceManager.startup();
        serviceManager.shutdown();

        Assert.assertTrue(threadService.startupOnServiceControlThread);
        Assert.assertTrue(threadService.prepareForShutdownOnServiceControlThread);
        Assert.assertTrue(threadService.shutdownOnServiceControlThread);
    }

    @Test(timeout = 1000)
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

        EasyMock.verify(listener);
    }

    @Test(timeout = 1000)
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

        EasyMock.verify(listener);
    }

    @Test(timeout = 1000)
    public void serviceStatusesCanBeObtained() {
        serviceManager = getSpringLoader().getBean("getStatusesServiceManager", ServiceManager.class);

        final ServiceStatus initialInactiveStartupStatus = new ServiceStatus(ServiceState.SERVICE_BEFORESTARTUP, "inactiveStartup", "Before startup", null);
        final ServiceStatus initialFaultStatus = new ServiceStatus(ServiceState.SERVICE_BEFORESTARTUP, "fault", "Before startup", null);
        final ServiceStatus initialOneStatus = new ServiceStatus(ServiceState.SERVICE_BEFORESTARTUP, "one", "Before startup", null);
        final List<ServiceStatus> initialStatuses = serviceManager.getStatuses();
        Assert.assertEquals(initialInactiveStartupStatus, initialStatuses.get(0));
        Assert.assertEquals(initialFaultStatus, initialStatuses.get(1));
        Assert.assertEquals(initialOneStatus, initialStatuses.get(2));

        serviceManager.startup();

        final ServiceStatus inactiveStartupStatus = new ServiceStatus(ServiceState.SERVICE_INACTIVE, "inactiveStartup", "Waiting for Godot", null);
        final ServiceStatus faultStatus = new ServiceStatus(ServiceState.SERVICE_FAULTY, "fault", "Fault: some exception", FaultService.FAULT_EXCEPTION);
        final ServiceStatus oneStatus = new ServiceStatus(ServiceState.SERVICE_STARTED, "one", "Started", null);
        final List<ServiceStatus> statuses = serviceManager.getStatuses();
        Assert.assertEquals(inactiveStartupStatus, statuses.get(0));
        Assert.assertEquals(faultStatus, statuses.get(1));
        Assert.assertEquals(oneStatus, statuses.get(2));

        Assert.assertEquals(inactiveStartupStatus, serviceManager.getStatus("inactiveStartup"));
        Assert.assertEquals(faultStatus, serviceManager.getStatus("fault"));
        Assert.assertEquals(oneStatus, serviceManager.getStatus("one"));
        serviceManager.shutdown();
    }
}