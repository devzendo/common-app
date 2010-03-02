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

package org.devzendo.minimiser.springloader;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test that the SpringLoader, once loaded per test, doesn't cache other
 * test's bean definition's beans.
 * Hopefully the order of test execution wouldn't invalidate this test?
 * @author matt
 *
 */
public final class TestSpringLoaderDoesntLoadOtherAppContextsTestBeans extends
        SpringLoaderUnittestCase {
    private static final Logger LOGGER = Logger
            .getLogger(TestSpringLoaderDoesntLoadOtherAppContextsTestBeans.class);
    /**
     * 
     */
    @Test
    public void testSpringLoaderDoesntLoadOthersTestBeans() {
        final SpringLoader sl = getSpringLoader();
        Assert.assertNotNull(sl);
        try {
            final SpringLoadedBean o1 = sl.getBean("testBean",
                SpringLoadedBean.class);
            LOGGER.info("We created a " + o1);
            Assert.fail("Should not have been able to load up a bean defined"
                    + " in a bean definition file that isn't annotated against"
                    + " this test or its superclasses");
        } catch (final Throwable t) {
            LOGGER.info("Correctly caught exception", t);
        }
    }
}
