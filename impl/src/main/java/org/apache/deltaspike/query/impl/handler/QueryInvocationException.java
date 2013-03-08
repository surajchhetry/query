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
package org.apache.deltaspike.query.impl.handler;

import java.lang.reflect.Method;

public class QueryInvocationException extends RuntimeException
{

    private static final long serialVersionUID = 1L;

    public QueryInvocationException(Throwable t, CdiQueryInvocationContext context)
    {
        super(createMessage(context, t), t);
    }

    public QueryInvocationException(String message, CdiQueryInvocationContext context)
    {
        super(createMessage(context));
    }

    public QueryInvocationException(Throwable t, Class<?> proxy, Method method)
    {
        super(createMessage(proxy, method, t), t);
    }

    private static String createMessage(CdiQueryInvocationContext context)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Failed calling DAO: [");
        builder.append("DAO=").append(context.getDaoMethod().getDao().getDaoClass().getName()).append(",");
        builder.append("entity=").append(context.getEntityClass().getName()).append(",");
        builder.append("method=").append(context.getMethod().getName()).append(",");
        builder.append("query=").append(context.getQueryString()).append("],");
        return builder.toString();
    }

    private static String createMessage(CdiQueryInvocationContext context, Throwable t)
    {
        StringBuilder builder = new StringBuilder(createMessage(context));
        builder.append("exception=").append(t.getClass()).append(",message=").append(t.getMessage());
        return builder.toString();
    }

    private static String createMessage(Class<?> proxy, Method method, Throwable t)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Exception calling DAO: [");
        builder.append("DAO=").append(proxy).append(",");
        builder.append("method=").append(method.getName()).append("],");
        builder.append("exception=").append(t.getClass()).append(",message=").append(t.getMessage());
        return builder.toString();
    }

}
