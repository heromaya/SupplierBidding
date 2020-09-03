package com.laodyu.web.activiti.service;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/10
 * @Version 1.0
 **/
public interface ActivitiService {
    String stratProcess(String key ,String code);
    void finishProcess(String taskid,String condition,String conditionValue);
}
