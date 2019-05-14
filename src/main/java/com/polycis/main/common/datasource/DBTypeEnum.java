package com.polycis.main.common.datasource;

/**
 * @Author DGD
 * @date 2018/2/7.
 */
public enum DBTypeEnum {
    db1("db1"), db2("db2"),db3("db3");;
    private String value;

    DBTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
