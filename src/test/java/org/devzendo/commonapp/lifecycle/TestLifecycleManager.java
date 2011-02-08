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

import org.devzendo.commonapp.spring.springloader.ApplicationContext;
import org.devzendo.commonapp.spring.springloader.SpringLoaderUnittestCase;
import org.devzendo.commonapp.util.OrderMonitor;
import org.junit.Assert;
import org.junit.Test;



/**
 * Tests for lifecycle management.
 *
 * @author matt
 *
 */
@ApplicationContext("org/devzendo/commonapp/lifecycle/LifecycleTestCase.xml")
public final class TestLifecycleManager extends SpringLoaderUnittestCase {

    private LifecycleManager lifecycleManager;

    /**
     *
     */
    public void getSimpleTestPrerequisites() {
        lifecycleManager = getSpringLoader().getBean("lifecycleManager", LifecycleManager.class);
    }

    /**
     *
     */
    @Test
    public void haveLivecycleManager() {
        getSimpleTestPrerequisites();

        Assert.assertNotNull(lifecycleManager);
    }

    /**
     *
     */
    @Test
    public void verifyTestResourcesGetWired() {
        getSimpleTestPrerequisites();

        final Lifecycle one = getSpringLoader().getBean("one", Lifecycle.class);
        Assert.assertNotNull(one);
        Assert.assertTrue(one instanceof OneLifecycle);
        final OneLifecycle oneLifecycle = (OneLifecycle) one;
        Assert.assertEquals("ctor", oneLifecycle.getState());

        // Two gets given a one
        final Lifecycle two = getSpringLoader().getBean("two", Lifecycle.class);
        Assert.assertNotNull(two);
        Assert.assertTrue(two instanceof TwoLifecycle);
        final TwoLifecycle twoLifecycle = (TwoLifecycle) two;
        Assert.assertEquals("ctor", twoLifecycle.getState());
        Assert.assertSame(one, twoLifecycle.getOne());
    }

    /**
     *
     */
    @Test
    public void lifeCycleManagerGetsRightConfig() {
        getSimpleTestPrerequisites();

        final List<String> beanNames = lifecycleManager.getBeanNames();
        Assert.assertNotNull(beanNames);
        Assert.assertEquals(1, beanNames.size());
        Assert.assertEquals("two", beanNames.get(0));
    }

    /**
     *
     */
    @Test
    public void instantiationOfLifecycles() {
        getSimpleTestPrerequisites();

        final Lifecycle two = getSpringLoader().getBean("two", Lifecycle.class);
        Assert.assertSame(two, lifecycleManager.getBean("two"));
    }

    /**
     *
     */
    @Test
    public void startupStartsUpAndShutdownShutsDown() {
        getSimpleTestPrerequisites();

        final Lifecycle two = getSpringLoader().getBean("two", Lifecycle.class);
        final TwoLifecycle twoLifecycle = (TwoLifecycle) two;
        Assert.assertEquals("ctor", twoLifecycle.getState());

        lifecycleManager.startup();

        Assert.assertEquals("started", twoLifecycle.getState());

        lifecycleManager.shutdown();

        Assert.assertEquals("shut down", twoLifecycle.getState());
    }

    /**
     * this lifecycle manager has a bad bean - nonexistant
     */
    @Test
    public void dontStartupBadBeans() {
        lifecycleManager = getSpringLoader().getBean("badLoadLifecycleManager", LifecycleManager.class);
        Assert.assertNotNull(lifecycleManager);
        Assert.assertEquals(0, lifecycleManager.getBeanNames().size());
    }

    /**
     * Tests for correct sequencing of startup and shutdown (forward and
     * reverse.
     */
    @Test
    public void startupAndShutdownSequence() {
        lifecycleManager = getSpringLoader().getBean("orderingLifecycleManager", LifecycleManager.class);
        Assert.assertNotNull(lifecycleManager);

        final OrderMonitor orderMonitor = getSpringLoader().getBean("orderMonitor", OrderMonitor.class);
        Assert.assertNotNull(orderMonitor);

        final List<String> initialOrdering = orderMonitor.getOrdering();
        Assert.assertEquals(0, initialOrdering.size());

        lifecycleManager.startup();

        final List<String> startupOrdering = orderMonitor.getOrdering();
        Assert.assertEquals(3, startupOrdering.size());
        Assert.assertEquals("a startup", startupOrdering.get(0));
        Assert.assertEquals("b startup", startupOrdering.get(1));
        Assert.assertEquals("c startup", startupOrdering.get(2));

        orderMonitor.reset();

        lifecycleManager.shutdown();

        final List<String> shutdownOrdering = orderMonitor.getOrdering();
        Assert.assertEquals(3, shutdownOrdering.size());
        Assert.assertEquals("c shutdown", shutdownOrdering.get(0));
        Assert.assertEquals("b shutdown", shutdownOrdering.get(1));
        Assert.assertEquals("a shutdown", shutdownOrdering.get(2));
    }
}
