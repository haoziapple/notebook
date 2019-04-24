package com.bkrwin.elevator.datarealm;

/**
 * 改写SQL接口类
 */
public interface SqlModifier {
    /**
     *
     * @param sql
     * @param extMap
     * @param dataField
     * @return
     */
    String changeSql(String sql, String extMap, String dataField);
}
