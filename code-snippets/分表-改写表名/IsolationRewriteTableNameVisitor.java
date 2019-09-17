package com.xgxx.common.isolation;

import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.google.common.collect.Sets;
import com.xgxx.common.support.Environment;

import java.util.Set;


/**
 * 多租户支持
 */
public class IsolationRewriteTableNameVisitor extends MySqlOutputVisitor {

    private Set<String> isolationTables;


    public IsolationRewriteTableNameVisitor(Appendable appender, Set<String> exclude) {
        super(appender);
        if (exclude != null) {
            this.isolationTables = exclude;
        } else {
            this.isolationTables = Sets.newHashSet();
        }
    }

    @Override
    public boolean visit(SQLExprTableSource x) {

        SQLName name = (SQLName) x.getExpr();

        if (isolationTables.contains(name.getSimpleName())) {
            Integer tenant = Environment.getTenantId();
            if (tenant == null || tenant == 0) {
                throw new IllegalArgumentException("Can not get tenant info.");
            }
            print0(tenant + "_" + name.getSimpleName());
        } else {
            x.getExpr().accept(this);
        }

        if (x.getAlias() != null) {
            print(' ');
            print0(x.getAlias());
        }

        for (int i = 0; i < x.getHintsSize(); ++i) {
            print(' ');
            x.getHints().get(i).accept(this);
        }

        return false;
    }


}
