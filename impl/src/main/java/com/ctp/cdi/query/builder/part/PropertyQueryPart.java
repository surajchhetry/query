package com.ctp.cdi.query.builder.part;

import static com.ctp.cdi.query.util.QueryUtils.uncapitalize;

import com.ctp.cdi.query.builder.QueryBuilderContext;
import com.ctp.cdi.query.builder.QueryBuilder;
import com.ctp.cdi.query.builder.QueryComparator;
import java.text.MessageFormat;

/**
 *
 * @author thomashug
 */
class PropertyQueryPart extends QueryPart {
    
    private String name;
    private QueryComparator comparator;

    @Override
    protected QueryPart build(String queryPart) {
        comparator = QueryComparator.Equal;
        name = uncapitalize(queryPart);
        for (QueryComparator comp : QueryComparator.values()) {
            if (queryPart.endsWith(comp.getExpression())) {
                comparator = comp;
                name = uncapitalize(queryPart.substring(0, queryPart.indexOf(comp.getExpression())));
                break;
            }
        }
        return this;
    }

    @Override
    protected QueryPart buildQuery(QueryBuilderContext ctx) {
        String[] args = new String[comparator.getParamNum() + 1];
        args[0] = QueryBuilder.ENTITY_NAME + "." + name;
        for (int i = 1; i < args.length; i++) {
            args[i] = "?" + String.valueOf(ctx.increment());
        }
        ctx.append(MessageFormat.format(comparator.getJpql(), (Object[]) args));
        return this;
    }
    
}