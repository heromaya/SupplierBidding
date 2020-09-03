package com.laodyu.web.activiti.service.impl;

import com.laodyu.commons.constant.ENProcessKey;
import com.laodyu.web.activiti.service.ActivitiService;
import com.laodyu.web.util.ActivitiUtils;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/10
 * @Version 1.0
 **/
@Service
public class ActivitiServiceImpl implements ActivitiService {
    @Autowired
    ProcessEngine processEngine;
    /***
     * 启动流程
     * @param key
     * @param code
     */
    @Override
    public String stratProcess(String key,String code) {
        if(key.equals(ENProcessKey.SupplierRegister.getValue())){
            /***
             * 1.供应商注册，向Activiti表中增加用户
             */
            ActivitiUtils.addUser(code);
        }
        //2.启动流程
        ActivitiUtils.startProcess(key,code);
        //3.获取任务节点
        List<Task> task = ActivitiUtils.getTask(code);
        //4.节点任务完成
        ActivitiUtils.completeTask(task.get(0).getId(),null,null);
        return task.get(0).getProcessInstanceId();
    }

    /***
     * 流程审核操作
     * @param taskid
     * @param condition
     * @param conditionValue
     */
    @Override
    public void finishProcess(String taskid, String condition, String conditionValue) {
        //结束流程
        Map<String, Object> args = new HashMap<>();
        args.put(condition,conditionValue);
        processEngine.getTaskService().complete(taskid,args);
    }
}
