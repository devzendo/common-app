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

package org.devzendo.minimiser.springbeanlistloader;

import java.util.List;

/**
 * Loads a list of beans from the application context.
 * @param <T> the bean type
 * 
 * @author matt
 *
 */
public interface SpringBeanListLoader<T> {

    /**
     * Obtain a list of bean names
     * @return the list of bean names
     */
    List<String> getBeanNames();

    /**
     * Obtain a bean by its name
     * @param beanName the bean name
     * @return a bean
     */
    T getBean(String beanName);
    
    /**
     * Obtain the list of loaded of beans
     * @return a list of loaded beans, in the order they were loaded
     */
    List<T> getBeans();
}
