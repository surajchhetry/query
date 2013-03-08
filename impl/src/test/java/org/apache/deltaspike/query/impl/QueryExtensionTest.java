/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.deltaspike.query.impl;

import static org.junit.Assert.assertNotNull;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.deltaspike.query.test.domain.Simple;
import org.apache.deltaspike.query.test.service.DaoInterface;
import org.apache.deltaspike.query.test.service.ExtendedDaoInterface;
import org.apache.deltaspike.query.test.service.SimpleDao;
import org.apache.deltaspike.query.test.util.TestDeployments;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class QueryExtensionTest
{

    @Deployment(order = 2)
    public static Archive<?> deployment()
    {
        return TestDeployments.initDeployment()
                .addPackage(DaoInterface.class.getPackage())
                .addPackages(true, Simple.class.getPackage());
    }

    @Inject
    Instance<DaoInterface> dao;

    @Inject
    Instance<ExtendedDaoInterface> extendedDao;

    @Inject
    Instance<SimpleDao> extendedClassDao;

    @Test
    public void should_inject()
    {
        assertNotNull(dao.get());
        assertNotNull(extendedDao.get());
        assertNotNull(extendedClassDao.get());
    }

}
