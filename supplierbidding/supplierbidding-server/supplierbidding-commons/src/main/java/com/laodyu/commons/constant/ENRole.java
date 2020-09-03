package com.laodyu.commons.constant;

import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/30
 * @Version 1.0
 **/
public enum  ENRole implements PersistEnum<ENRole>{
    ROLE_ADMIN("1"),
    ROLE_SUPPLIER("2"),
    ROLE_TENDERING("3"),
    ROLE_EXPERT("4"),
    ;
    private String value;
    ENRole(String value) {
        this.value = value;
    }

    public  String getValue(){
        return this.value;
    }

    private static final Map<String,ENRole> map = new TreeMap<>();
    static {
        map.put(ROLE_ADMIN.getValue(),ROLE_ADMIN);
        map.put(ROLE_TENDERING.getValue(),ROLE_TENDERING);
        map.put(ROLE_SUPPLIER.getValue(),ROLE_SUPPLIER);
        map.put(ROLE_EXPERT.getValue(),ROLE_EXPERT);
    }
    @Override
    public String getPersistedValue() {
        return getValue();
    }

    @Override
    public ENRole returnEnum(String persistedValue) {
        return map.get(persistedValue);
    }

    @Override
    public Map<String, ENRole> getAllValueMap() {
        return map;
    }
}
