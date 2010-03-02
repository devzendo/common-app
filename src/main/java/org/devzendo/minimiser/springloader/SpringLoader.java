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

/**
 * An abstraction around the Spring DI container
 * 
 * @author matt
 *
 */
public interface SpringLoader {
    /**
     * @param <T> the type of object to return 
     * @param beanId the bean id from the application context file(s)
     * @param beanType the type of the object to return
     * @return an instantiated object
     */
    <T> T getBean(String beanId, Class<T> beanType);
    
    /**
     * Add an array of other application context files to the
     * SpringLoader, e.g. by the plugin manager. 
     * @param contextFiles an array of application context
     * files, as resource paths.
     */
    void addApplicationContext(final String ... contextFiles);
    
    /**
     * Close the SpringLoader; free resources 
     */
    void close();
}
