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
package org.apache.deltaspike.query.impl.builder.part;

import org.apache.deltaspike.query.impl.builder.MethodExpressionException;
import org.apache.deltaspike.query.impl.meta.DaoComponent;
import org.apache.deltaspike.query.impl.property.Property;
import org.apache.deltaspike.query.impl.property.query.NamedPropertyCriteria;
import org.apache.deltaspike.query.impl.property.query.PropertyQueries;
import org.apache.deltaspike.query.impl.property.query.PropertyQuery;

abstract class BasePropertyQueryPart extends QueryPart
{

    static final String SEPARATOR = "_";

    void validate(String name, String method, DaoComponent dao)
    {
        Class<?> current = dao.getEntityClass();
        if (name == null)
        {
            throw new MethodExpressionException(null, dao.getDaoClass(), method);
        }
        for (String property : name.split(SEPARATOR))
        {
            PropertyQuery<?> query = PropertyQueries.createQuery(current)
                    .addCriteria(new NamedPropertyCriteria(property));
            Property<?> result = query.getFirstResult();
            if (result == null)
            {
                throw new MethodExpressionException(property, dao.getDaoClass(), method);
            }
            current = result.getJavaClass();
        }
    }

    String rewriteSeparator(String name)
    {
        if (name.contains("_"))
        {
            return name.replaceAll(SEPARATOR, ".");
        }
        return name;
    }

}
