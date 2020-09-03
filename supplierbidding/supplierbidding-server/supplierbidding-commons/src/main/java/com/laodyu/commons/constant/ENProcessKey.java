package com.laodyu.commons.constant;

import java.util.Map;
import java.util.TreeMap;

public enum ENProcessKey implements PersistEnum<ENProcessKey> {
    SupplierRegister("SupplierRegister"),
    TenderingApplyProject("TenderingApplyProject"),
    TenderingApplyNotice("TenderingApplyNotice"),
    TenderingCancelProject("TenderingCancelProject"),
    TenderingCancelNotice("TenderingCancelNotice")
    ;
    private String value;

    ENProcessKey(String value) {
        this.value = value;
    }

    public  String getValue(){
        return this.value;
    }
    private static final Map<String, ENProcessKey> map = new TreeMap<>();
    static {
        map.put(SupplierRegister.getValue(),SupplierRegister);
        map.put(TenderingApplyProject.getValue(),TenderingApplyProject);
        map.put(TenderingApplyNotice.getValue(),TenderingApplyNotice);
        map.put(TenderingCancelProject.getValue(),TenderingCancelProject);
        map.put(TenderingCancelNotice.getValue(),TenderingCancelNotice);

    }
    @Override
    public String getPersistedValue() {
        return getValue();
    }

    @Override
    public ENProcessKey returnEnum(String persistedValue) {
        return map.get(persistedValue);
    }

    @Override
    public Map<String, ENProcessKey> getAllValueMap() {
        return map;
    }
}
