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
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

/**
 * the plugin system will need to add plugins' application contexts
 * to the SpringLoader. Test that once this has been done, a bean
 * can be loaded from both the original, and the chained
 * application contexts.
 *
 * @author matt
 */
@ApplicationContext("org/devzendo/minimiser/springloader/TestSpringLoaderLoadingTestBeans.xml")
public final class TestSpringLoaderChainingAppContexts extends SpringLoaderUnittestCase {

    /**
     * Can't load a bean from an unchained context
     */
    @Test(expected = NoSuchBeanDefinitionException.class)
    public void testSpringLoaderLoadingTestBeanFromNonChainedContext() {
        final SpringLoader sl = getSpringLoader();
        Assert.assertNotNull(sl);

        sl.getBean("testChainedBean", ChainedSpringLoadedBean.class);
    }

    /**
     * Can load beans from chained contexts
     */
    @Test
    public void testSpringLoaderLoadingChainedTestBeans() {
        final SpringLoader sl = getSpringLoader();
        Assert.assertNotNull(sl);
        final SpringLoadedBean o1 = sl.getBean("testBean",
            SpringLoadedBean.class);
        Assert.assertNotNull(o1);

        sl.addApplicationContext("org/devzendo/minimiser/springloader/TestSpringLoaderLoadingTestBeansChaining.xml");

        final ChainedSpringLoadedBean c1 = sl.getBean("testChainedBean", ChainedSpringLoadedBean.class);
        Assert.assertEquals(42, c1.getTheAnswer());
    }

    /**
     * After a context has been chained, you can still load
     * from the original context
     */
    @Test
    public void testSpringLoaderChainingAppContextDoesntRemoveOriginalContext() {
        final SpringLoader sl = getSpringLoader();
        Assert.assertNotNull(sl);

        sl.addApplicationContext("org/devzendo/minimiser/springloader/TestSpringLoaderLoadingTestBeansChaining.xml");

        final ChainedSpringLoadedBean c1 = sl.getBean("testChainedBean", ChainedSpringLoadedBean.class);
        Assert.assertEquals(42, c1.getTheAnswer());

        final SpringLoadedBean o1 = sl.getBean("testBean",
            SpringLoadedBean.class);
        Assert.assertEquals(31415, o1.getTheAnswer());
    }

    /**
     * By chaining a context, you can override beans
     */
    @Test
    public void testSpringLoaderChainingAppContextAllowsOverrides() {
        final SpringLoader sl = getSpringLoader();
        Assert.assertNotNull(sl);

        final AnswerProvider o1 = sl.getBean("testBean",
            AnswerProvider.class);
        Assert.assertEquals(31415, o1.getTheAnswer());

        sl.addApplicationContext("org/devzendo/minimiser/springloader/TestSpringLoaderLoadingTestBeansChainingOverrides.xml");

        final AnswerProvider o2 = sl.getBean("testBean",
            AnswerProvider.class);
        Assert.assertEquals(16384, o2.getTheAnswer());
    }
}
