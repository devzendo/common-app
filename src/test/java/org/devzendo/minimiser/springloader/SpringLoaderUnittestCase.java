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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.devzendo.commoncode.string.StringUtils;
import org.devzendo.minimiser.logging.LoggingTestCase;
import org.junit.After;
import org.junit.Before;


/**
 * A base class for Junit 4 test cases that specify a set of Spring application
 * context XML files to be used by the ClassPathApplicationContextLoader using
 * the
 * 
 * @ApplicationContext annotation.
 * @author matt
 */
public abstract class SpringLoaderUnittestCase extends LoggingTestCase {
    private static final Logger LOGGER = Logger
            .getLogger(SpringLoaderUnittestCase.class);
    private SpringLoader springLoader;

    /**
     * Set up the SpringLoader with all application context files
     * given in any annotations.
     * 
     * @ApplicatonContext annotations on this test class, and any in its
     *                    inheritance hierarchy.
     */
    @Before
    public final void initApplicationContexts() {
        LOGGER.info(">>> initApplicationContexts");
        final List<String> contextList = new ArrayList<String>();
        // add all contexts provided by subclass methods, not
        // annotations
        contextList.addAll(getRuntimeApplicationContexts());
        // scan up to root of object hierarchy finding our annotation
        Class<? extends Object> clazz = this.getClass();
        while (clazz != null) {
            final ApplicationContext ac = clazz.getAnnotation(ApplicationContext.class);
            if (ac != null) {
                contextList.addAll(Arrays.asList(ac.value()));
            }
            clazz = clazz.getSuperclass();
        }
        LOGGER.info(String.format("Initialising SpringLoader with contexts [%s]", 
            StringUtils.join(contextList, ", ")));
        springLoader = SpringLoaderFactory.initialise(contextList);
        LOGGER.info("<<< initApplicationContexts");
    }
    
    /**
     * @return any paths to application contexts that must be
     * provided at run-time, rather than via annotations
     */
    @SuppressWarnings("unchecked")
    protected List<String> getRuntimeApplicationContexts() {
        return Collections.EMPTY_LIST;
    }

    /**
     * Cleans up the SpringLoader
     */
    @After
    public final void closeSpringLoader() {
        LOGGER.info(">>> closeSpringLoader");
        if (springLoader != null) {
            springLoader.close();
        }
        LOGGER.info("<<< closeSpringLoader");
    }

    /**
     * @return the SpringLoader to use in all subclasses of this.
     */
    public final SpringLoader getSpringLoader() {
        return springLoader;
    }
}
