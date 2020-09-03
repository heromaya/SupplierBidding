package com.laodyu.commons.constant;

import java.util.Map;
import java.util.TreeMap;

public enum ENCancelState implements PersistEnum<ENCancelState> {

    /**
     * 未作废
     */
    NOCANCEL("1"),

    /**
     * 作废中
     */
    CANCELING("2"),
    /**
     * 已作废
     */
    CANCELED("3");




    private String value;

    ENCancelState(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    private static final Map<String, ENCancelState> map = new TreeMap<String, ENCancelState>();

    static {
        map.put(NOCANCEL.getValue(), NOCANCEL);
        map.put(CANCELED.getValue(), CANCELED);
        map.put(CANCELING.getValue(), CANCELED);
    }

    @Override
    public String getPersistedValue() {
        return getValue();
    }

    @Override
    public ENCancelState returnEnum(String persistedValue) {
        return map.get(persistedValue);
    }

    @Override
    public Map<String, ENCancelState> getAllValueMap() {
        return map;
    }
}

