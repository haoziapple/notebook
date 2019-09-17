package com.xgxx.common.isolation;

import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLMergeStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectTableReference;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleOutputVisitor;
import com.google.common.collect.Sets;
import com.xgxx.common.support.Environment;
import org.apache.commons.lang.StringUtils;
import java.util.Set;

/**
 * 多租户支持
 */
public class RewriteTableNameVisitor extends OracleOutputVisitor {
    private Set<String> include;
    private Set<String> exclude;

    RewriteTableNameVisitor(Appendable appender, Set<String> include, Set<String> exclude) {
        super(appender);
        if (exclude != null) {
            this.exclude = exclude;
        } else {
            this.exclude = Sets.newHashSet();
        }
        if (include != null) {
            this.include = include;
        } else {
            this.include = Sets.newHashSet();
        }
    }

    @Override
    public boolean visit(OracleSelectTableReference x) {
        if(!rewriteTableName(x.getName(), x.getAlias())){
            return super.visit(x);
        }
        return false;
    }

    @Override
    public boolean visit(SQLExprTableSource x) {
        if(!rewriteTableName(x.getName(), x.getAlias())){
            return super.visit(x);
        }
        return false;
    }

    @Override
    public boolean visit(SQLMergeStatement x) {
        SQLExprTableSource exprTableSource = (SQLExprTableSource) x.getInto();
        if(!rewriteTableName(exprTableSource.getName(), exprTableSource.getAlias())){
            return super.visit(x);
        }
        return false;
    }

    private boolean rewriteTableName(SQLName name, String alias){
        if (exclude.contains(name.getSimpleName())) {
            return false;
        }
        if (include.isEmpty() || include.contains(name.getSimpleName())){
            String tenantCode = Environment.getTenantCode();
            if (StringUtils.isEmpty(tenantCode)) {
                throw new IllegalArgumentException("Can not get tenant info.");
            }
            print0(tenantCode + "_" + name.getSimpleName());

            if (StringUtils.isNotEmpty(alias)) {
                this.print(' ');
                this.print0(alias);
            }
            return true;
        }
        return false;
    }
}
