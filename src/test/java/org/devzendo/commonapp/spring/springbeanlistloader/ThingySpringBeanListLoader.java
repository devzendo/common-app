/**
 * Copyright (C) 2008-2010 Matt Gumbley, DevZendo.org http://devzendo.org
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

package org.devzendo.commonapp.spring.springbeanlistloader;

import java.util.List;

import org.devzendo.commonapp.spring.springloader.SpringLoader;

/**
 * @author matt
 *
 */
public class ThingySpringBeanListLoader extends AbstractSpringBeanListLoaderImpl<Thingy> {
    /**
     * @param springLoader the spring loader
     * @param beanNames the bean names
     */
    public ThingySpringBeanListLoader(final SpringLoader springLoader, final List<String> beanNames) {
        super(springLoader, beanNames);
    }
}
