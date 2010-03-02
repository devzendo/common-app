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

import org.junit.Assert;
import org.junit.Test;

/**
 * Test that we can correctly load up a bean from this test's bean definition
 * file.
 *
 * @author matt
 */
@ApplicationContext("org/devzendo/minimiser/springloader/TestSpringLoaderLoadingTestBeans.xml")
public final class TestSpringLoaderLoadingTestBeans extends SpringLoaderUnittestCase {

    /**
     * Load up a bean twice, and test its singletonness.
     */
    @Test
    public void testSpringLoaderLoadingTestBeans() {
        final SpringLoader sl = getSpringLoader();
        Assert.assertNotNull(sl);
        final SpringLoadedBean o1 = sl.getBean("testBean",
            SpringLoadedBean.class);
        Assert.assertNotNull(o1);
        Assert.assertTrue(o1 instanceof SpringLoadedBean);
        final SpringLoadedBean o2 = sl.getBean("testBean",
            SpringLoadedBean.class);
        Assert.assertNotNull(o2);
        Assert.assertTrue(o2 instanceof SpringLoadedBean);
        Assert.assertEquals(o1, o2);
    }
}
