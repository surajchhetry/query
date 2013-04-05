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
package org.apache.deltaspike.data.impl.handler;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.deltaspike.data.api.EntityManagerResolver;
import org.apache.deltaspike.data.impl.meta.RepositoryComponent;

public class EntityManagerLookup
{

    @Inject
    @Any
    private Instance<EntityManager> entityManager;

    @Inject
    @Any
    private Instance<EntityManagerResolver> entityManagerResolver;

    public EntityManager lookupFor(RepositoryComponent repository)
    {
        if (repository.hasEntityManagerResolver())
        {
            EntityManagerResolver resolver = lookupResolver(repository.getEntityManagerResolverClass());
            return resolver.resolveEntityManager();
        }
        return entityManager.get();
    }

    private EntityManagerResolver lookupResolver(
            Class<? extends EntityManagerResolver> resolverClass)
    {
        return entityManagerResolver.select(resolverClass).get();
    }
}