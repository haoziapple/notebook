package com.bkrwin.elevator.datarealm;

import com.bkrwin.ufast.dto.UserDetailDTO;

/**
 * @author wanghao
 * @Description 用户线程变量
 * @date 2019-04-22 15:52
 */
public class UserContextHolder {
    private final static ThreadLocal<UserDetailDTO> userHolder = new ThreadLocal<>();

    private final static ThreadLocal<String> dataFieldHolder = new ThreadLocal<>();

    /**
     * 设置用户信息
     *
     * @param user
     */
    public static void add(UserDetailDTO user) {
        userHolder.set(user);
    }

    /**
     * 设置数据权限字段
     *
     * @param dataField
     */
    public static void setDataField(String dataField) {
        dataFieldHolder.set(dataField);
    }

    public static String getDataField() {
        return dataFieldHolder.get();
    }

    public static UserDetailDTO get() {
        return userHolder.get();
    }

    public static void remove() {
        userHolder.remove();
        dataFieldHolder.remove();
    }
}
