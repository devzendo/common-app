package org.devzendo.commonapp.service;

import org.devzendo.commonapp.lifecycle.Lifecycle;
import org.devzendo.commonapp.lifecycle.TwoLifecycle;
import org.devzendo.commonapp.spring.springloader.ApplicationContext;
import org.devzendo.commonapp.spring.springloader.SpringLoaderUnittestCase;
import org.junit.Assert;
import org.junit.Test;

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

    @Test
    public void haveServiceManager() {
        getSimpleTestPrerequisites();

        Assert.assertNotNull(serviceManager);
    }

    @Test
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
}
