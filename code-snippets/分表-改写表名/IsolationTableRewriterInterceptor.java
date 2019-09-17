package com.xgxx.common.isolation;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
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
public class IsolationTableRewriterInterceptor implements Interceptor {

    private Set<String> isolationTables = null;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = null;
        if (invocation.getTarget() instanceof RoutingStatementHandler) {
            MetaObject statementMetaObject = SystemMetaObject.forObject(invocation.getTarget());
            statementHandler = (StatementHandler) statementMetaObject.getValue("delegate");
        } else {
            statementHandler = (StatementHandler) invocation.getTarget();
        }
        MetaObject statementMetaObject = SystemMetaObject.forObject(statementHandler);
        BoundSql boundSql = (BoundSql) statementMetaObject.getValue("boundSql");
        MetaObject boundSqlMetaObject = SystemMetaObject.forObject(boundSql);

        Transaction t = Cat.newTransaction("TenantTableRewriter", "sqlRewrite");
        try {
            List<SQLStatement> statements = SQLUtils.parseStatements(boundSql.getSql(), JdbcConstants.MYSQL);

            StringBuilder stringBuilder = new StringBuilder();
            MySqlOutputVisitor visitor = new IsolationRewriteTableNameVisitor(stringBuilder, isolationTables);
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
        String excludeTables = Environment.getProperty("isolation.tables");
        if (StringUtils.isNotBlank(excludeTables)) {
            StringTokenizer stringTokenizer = new StringTokenizer(excludeTables, " ,;.|");
            this.isolationTables = Sets.newHashSet();
            while (stringTokenizer.hasMoreTokens()) {
                isolationTables.add(stringTokenizer.nextToken());
            }
        }
    }


}
