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
 * Test the SpringLoader
 * @author matt
 *
 */
public final class TestSpringLoader extends SpringLoaderUnittestCase {
    private static final Logger LOGGER = Logger.getLogger(TestSpringLoader.class);
    /**
     * 
     */
    @Test
    public void testGetSpringLoader() {
        Assert.assertNotNull(getSpringLoader());
        LOGGER.info("Spring Loader is " + getSpringLoader().hashCode());
    }
}
