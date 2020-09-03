package com.laodyu.commons.constant;

import java.util.Map;
import java.util.TreeMap;

public enum ENApproveState  implements PersistEnum<ENApproveState> {
    /**
     * 未审批
     */
    NOTAPPROVE("1"),

    /**
     * 审批中
     */
    APPROVING("2"),
    /**
     * 已审批
     */
    APPROVED("3"),

    /**
     * 已拒绝
     */
    REFUSED("4");


    private String value;

    private ENApproveState(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
    private static final Map<String, ENApproveState> map = new TreeMap<String, ENApproveState>();
    private static final Map<String, String> mapLabel = new TreeMap<String, String>();
    static {
        map.put(NOTAPPROVE.getValue(), NOTAPPROVE);
        map.put(APPROVED.getValue(), APPROVED);
        map.put(REFUSED.getValue(), REFUSED);
        map.put(APPROVING.getValue(), APPROVING);

        mapLabel.put(NOTAPPROVE.getValue(), "未审批");
        mapLabel.put(APPROVED.getValue(), "已审批");
        mapLabel.put(REFUSED.getValue(), "已拒绝");
        mapLabel.put(APPROVING.getValue(), "审批中");
    }
    @Override
    public String getPersistedValue() {
        return getValue();
    }

    @Override
    public ENApproveState returnEnum(String persistedValue) {
        return map.get(persistedValue);
    }

    @Override
    public Map<String, ENApproveState> getAllValueMap() {
        return map;
    }

    /**
     * 获取枚举中文名称
     * @return
     */
    /**
     * 不允许使用中文判断 ，请使用 具体枚举值判断 ，否则英文状态下将异常
     * @return
     */
    @Deprecated
    public String getLabel()
    {
        return mapLabel.get(this.value);
    }
}

