package com.laodyu.commons.constant;

import java.util.Map;

/**
 * @InterfaceName需要持久化的enum类，都需要实现的接口
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/29
 * @Version 1.0
 **/
public interface PersistEnum<E extends Enum<?>> {
    /**
     * 获取被持久化字段的值
     *
     * @return 被持久化字段的值
     */
    String getPersistedValue();

    /**
     * 由被持久化的字段的值获取枚举类型
     *
     * @param persistedValue
     * @return
     */
    E returnEnum(String persistedValue);

    /**
     * 获取枚举的所有枚举项
     *
     * @return map
     */
    Map<String, E> getAllValueMap();

}
