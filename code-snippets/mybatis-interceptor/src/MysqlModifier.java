package com.bkrwin.elevator.datarealm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wanghao
 * @Description mysql语句改写类
 * @date 2019-04-23 15:45
 */
public class MysqlModifier implements SqlModifier {
    private Pattern wherePattern = Pattern.compile("1(\\s*)=(\\s*)1");

    @Override
    public String changeSql(String sql, String extMap, String dataField) {
        Matcher m = wherePattern.matcher(sql);
        if(m.find()) {
            return m.replaceFirst(dataField + " like CONCAT('" + extMap + "', '%')");
        } else {
            return sql;
        }
    }
}
