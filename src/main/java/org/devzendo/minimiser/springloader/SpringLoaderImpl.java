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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * An implementation of the SpringLoader that uses Spring, obviously!
 * Whether we'd ever have other impls is dubious, but we need to maintain
 * interface/implementation separation.
 * 
 * @author matt
 *
 */
public final class SpringLoaderImpl implements SpringLoader, ApplicationContextAware {
    private ApplicationContext currentAppContext;

    /**
     * {@inheritDoc}
     */
    public <T> T getBean(final String beanId, final Class <T> beanType) {
        return currentAppContext.getBean(beanId, beanType);
    }
    
    /**
     * {@inheritDoc}
     */
    public void addApplicationContext(final String ... contextFiles) {
        currentAppContext = new ClassPathXmlApplicationContext(contextFiles, true, currentAppContext);
    }

    /**
     * {@inheritDoc}
     */
    public void setApplicationContext(final ApplicationContext aC) throws BeansException {
        currentAppContext = aC;
    }

    /**
     * {@inheritDoc}
     */
    public void close() {
        ((ClassPathXmlApplicationContext) currentAppContext).close();
    }
}
