/**
 * Copyright (C) 2008-2012 Matt Gumbley, DevZendo.org <http://devzendo.org>
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.devzendo.commonapp.service;

import org.apache.log4j.Logger;
import org.devzendo.commonapp.spring.springbeanlistloader.AbstractSpringBeanListLoaderImpl;
import org.devzendo.commonapp.spring.springloader.SpringLoader;

import java.util.List;

public class DefaultServiceManager extends AbstractSpringBeanListLoaderImpl<Service> implements ServiceManager {
    private static final Logger LOGGER = Logger.getLogger(DefaultServiceManager.class);

    /**
     * @param springLoader the Spring loader
     * @param serviceBeanNames the list of Service beans to manage.
     */
    public DefaultServiceManager(final SpringLoader springLoader, final List<String> serviceBeanNames) {
        super(springLoader, serviceBeanNames);
    }

}
