package com.bkrwin.elevator.datarealm;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author wanghao
 * @Description 数据权限mybatis自定义拦截器
 * 1. 使用时，先在切面执行UserContextHolder.setDataField方法，决定对原sql哪个字段进行筛选
 * 2. 原sql必须有“1=1”的语句，MysqlModifier将替换为对应的like语句，进行权限查询
 * @date 2019-04-17 11:31
 * @see UserContextHolder
 * @see MysqlModifier
 */
@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class DataAuthMybatisInterceptor implements Interceptor {
    private static int MAPPED_STATEMENT_INDEX = 0;
    private static int PARAMETER_INDEX = 1;
    private SqlModifier sqlModifier = new MysqlModifier();
    private static final Logger logger = LoggerFactory.getLogger(DataAuthMybatisInterceptor.class);


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (UserContextHolder.get() == null
                || StringUtils.isBlank(UserContextHolder.get().getExtMap())
                || StringUtils.isBlank(UserContextHolder.getDataField())) {
            // 返回，继续执行
            return invocation.proceed();
        }
        String userId = UserContextHolder.get().getUserId();
        String extMap = UserContextHolder.get().getExtMap();
        String dataField = UserContextHolder.getDataField();
        // 获取sql
        String sql = getSqlByInvocation(invocation);
        logger.info("start change SQL by data realm, userId: {}, extMap: {}, dataField: {}, originSql: {}", userId, extMap, dataField, sql);

        if (StringUtils.isBlank(sql)) {
            return invocation.proceed();
        }

        String changeSql = sqlModifier.changeSql(sql, extMap, dataField);
        logger.info("changed sql: {}" + changeSql);
        // 包装sql后，重置到invocation中
        resetSql2Invocation(invocation, changeSql);

        // 返回，继续执行
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 获取sql语句
     *
     * @param invocation
     * @return
     */
    private String getSqlByInvocation(Invocation invocation) {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[MAPPED_STATEMENT_INDEX];
        Object parameterObject = args[PARAMETER_INDEX];
        BoundSql boundSql = ms.getBoundSql(parameterObject);
        return boundSql.getSql();
    }

    /**
     * 包装sql后，重置到invocation中
     *
     * @param invocation
     * @param sql
     */
    private void resetSql2Invocation(Invocation invocation, String sql) {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[MAPPED_STATEMENT_INDEX];
        Object parameterObject = args[PARAMETER_INDEX];

        // 设置新的SqlSource，改写SQL
        SqlSource sqlSource = ms.getSqlSource();
        SqlSource newSqlSource = new StaticSqlSource(ms.getConfiguration(), sql, sqlSource.getBoundSql(parameterObject).getParameterMappings());
        // 设置新的MappedStatement
        MappedStatement newStatement = newMappedStatement(ms, newSqlSource);
        args[MAPPED_STATEMENT_INDEX] = newStatement;
    }

    private MappedStatement newMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder =
                new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }
}
