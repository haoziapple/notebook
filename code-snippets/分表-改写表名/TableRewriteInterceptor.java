package com.xgxx.common.isolation;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.alibaba.druid.util.JdbcConstants;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.google.common.collect.Sets;
import com.xgxx.common.support.Environment;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 多租户支持
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class TableRewriteInterceptor implements Interceptor {
    private String dbType = null;//数据库类型
    private Set<String> include = null;//需要重写的表
    private Set<String> exclude = null;//不需要重写的表

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler;
        if (invocation.getTarget() instanceof RoutingStatementHandler) {
            MetaObject statementMetaObject = SystemMetaObject.forObject(invocation.getTarget());
            statementHandler = (StatementHandler) statementMetaObject.getValue("delegate");
        } else {
            statementHandler = (StatementHandler) invocation.getTarget();
        }
        MetaObject statementMetaObject = SystemMetaObject.forObject(statementHandler);
        BoundSql boundSql = (BoundSql) statementMetaObject.getValue("boundSql");
        MetaObject boundSqlMetaObject = SystemMetaObject.forObject(boundSql);

        Transaction t = Cat.newTransaction("TenantTableRewrite", "sqlRewrite");
        try {
            List<SQLStatement> statements = SQLUtils.parseStatements(boundSql.getSql(), dbType);

            StringBuilder stringBuilder = new StringBuilder();
            SQLASTOutputVisitor visitor = new RewriteTableNameVisitor(stringBuilder, include, exclude);
            visitor.setPrettyFormat(false);
            statements.forEach(stmt -> stmt.accept(visitor));

            boundSqlMetaObject.setValue("sql", stringBuilder.toString());

            t.setStatus(Transaction.SUCCESS);
        } catch (Throwable throwable) {
            RuntimeException runtimeException = new RuntimeException(String.format("SQL:[%s] table name rewrite error.", boundSql.getSql()), throwable);
            t.setStatus(runtimeException);
            throw runtimeException;
        } finally {
            t.complete();
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        dbType = properties.getProperty("dbType");
        if (StringUtils.isEmpty(dbType)) {
            dbType = JdbcConstants.ORACLE;
        }
        String includeTables = Environment.getProperty("tenant.tables.include");
        if (StringUtils.isNotBlank(includeTables)) {
            StringTokenizer stringTokenizer = new StringTokenizer(includeTables, " ,;.|");
            this.include = Sets.newHashSet();
            while (stringTokenizer.hasMoreTokens()) {
                include.add(stringTokenizer.nextToken());
            }
        }
        String excludeTables = Environment.getProperty("tenant.tables.exclude");
        if (StringUtils.isNotBlank(excludeTables)) {
            StringTokenizer stringTokenizer = new StringTokenizer(excludeTables, " ,;.|");
            this.exclude = Sets.newHashSet();
            while (stringTokenizer.hasMoreTokens()) {
                exclude.add(stringTokenizer.nextToken());
            }
        }
    }
}
