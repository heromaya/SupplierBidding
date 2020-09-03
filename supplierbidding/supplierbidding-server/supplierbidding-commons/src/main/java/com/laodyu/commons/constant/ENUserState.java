package com.laodyu.commons.constant;

import java.util.Map;
import java.util.TreeMap;
        /**
        * 系统用户状态
        */
public enum ENUserState implements PersistEnum<ENUserState> {
    /**
     *  未激活：1
     */
    DISACTIVE("1"),
    /**
     * 正常：2
     */
    NORMAL("2"),
    /**
     * 禁用：3
     */
    FORBIDDEN("3");
    private String acountstate;

    private ENUserState(String acountState) {
        this.acountstate = acountState;
    }

    public String getValue() {
        return this.acountstate;
    }


    private static final Map<String, ENUserState> map = new TreeMap<String, ENUserState>();

    static {
        map.put(NORMAL.getValue(), NORMAL);
        map.put(DISACTIVE.getValue(), DISACTIVE);
        map.put(FORBIDDEN.getValue(), FORBIDDEN);
    }
    @Override
    public String getPersistedValue() {
        return getValue();
    }

    @Override
    public ENUserState returnEnum(String persistedValue) {
        return map.get(persistedValue);
    }

    @Override
    public Map<String, ENUserState> getAllValueMap() {
        return map;
    }
}

