package com.laodyu.web.util;

import com.laodyu.commons.exception.GlobalExceptionHandler;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.*;

/**
 * @ClassName
 * @Description 操作Activiti的工具类
 * @Author Joe
 * @Date 2020/5/10
 * @Version 1.0
 **/
@Component
public class ActivitiUtils {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Autowired
    private  IdentityService identityService;
    @Autowired
    private  ProcessEngine processEngine;
    @Autowired
    private  RepositoryService repositoryService;
    @Autowired
    private  RuntimeService runtimeService;
    @Autowired
    private  HistoryService historyService;

    private static  ActivitiUtils activitiUtils;
    @PostConstruct
    public void init(){
        activitiUtils = this;
    }
    /***
     * 判断act_id_user中是否已存在该用户
     */
    public  static boolean isExist(String code){
        UserQuery userQuery = activitiUtils.identityService.createUserQuery();
        User user = userQuery.userId(code).singleResult();
        return user==null? false:true;
    }
    /***
     * 往Activiti的 act_id_user表中添加用户
     * @param code
     */
    public static void addUser(String code) {
        User user = activitiUtils.identityService.newUser(code);
        activitiUtils.identityService.saveUser(user);
        logger.info("Activiti:向act_id_user表中添加用户 中添加了用户：" + code);
    }

    /***
     * 根据Key启动流程
     */
    public static void startProcess(String key,String code) {
        //使用流程定义的key启动流程实例
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid",code);
        map.put("approve","approve1");
        ProcessInstance processInstance = activitiUtils.processEngine.getRuntimeService()
                .startProcessInstanceByKey(key,map);
        logger.info("activiti 启动流程实例");
        logger.info("流程实例ID:" + processInstance.getId());
        logger.info("流程定义ID:" + processInstance.getProcessDefinitionId());
    }

    /***
     * 根据userid获取任务
     */
    public static List<Task> getTask(String userid) {
        List<Task> list = activitiUtils.processEngine.getTaskService()
                .createTaskQuery().taskAssignee(userid).orderByTaskCreateTime().desc().list();
        logger.info(userid+"获取任务"+list.toString());
        return list;
    }


    /***
     *
     * @param taskid 任务id 在act_ru_task 中
     * @param condition 完成任务设置的条件，审批通过 则 condition = 3 , 不通过 则 contion = 4 如果没有条件则可以为空
     *
     */
    public static void completeTask(String taskid , String condition,String conditionValue){
        //完成任务的同时设置流程变量，根据流程变量的结果来进入哪一个节点任务
        Map<String, Object> args = new HashMap<>();
        if(StringUtils.isNotBlank(condition)&&StringUtils.isNotBlank(conditionValue)){
            //有流程变量
            args.put(condition,conditionValue);
            activitiUtils.processEngine.getTaskService().complete(taskid,args);
        }else{
            //无流程变量
            activitiUtils.processEngine.getTaskService().complete(taskid);
        }
    }

    /***
     *
     * @param processInstanceId
     * @return
     */
    public static BufferedImage getProcessImage(String processInstanceId){
        try {
            InputStream is = getDiagram(processInstanceId);
            if (is == null) {
                return null;
            }
            BufferedImage image = ImageIO.read(is);
            is.close();
            return  image;
        } catch (Exception ex) {
           logger.error("根据："+processInstanceId+ " 获取流程图失败");
        }
        return null;
    }
    private static InputStream getDiagram(String processInstanceId){
        //获得流程实例
        ProcessInstance processInstance = activitiUtils.runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = StringUtils.EMPTY;
        if (processInstance == null) {
            //查询已经结束的流程实例
            HistoricProcessInstance processInstanceHistory =
                    activitiUtils.historyService.createHistoricProcessInstanceQuery()
                            .processInstanceId(processInstanceId).singleResult();
            if (processInstanceHistory == null) {
                return null;
            } else {
                processDefinitionId = processInstanceHistory.getProcessDefinitionId();
            }
        } else {
            processDefinitionId = processInstance.getProcessDefinitionId();
        }

        //使用宋体
        String fontName = "宋体";
        //获取BPMN模型对象
        BpmnModel model = activitiUtils.repositoryService.getBpmnModel(processDefinitionId);
        //获取流程实例当前的节点，需要高亮显示
        List<String> currentActs = Collections.EMPTY_LIST;
        if (processInstance != null) {
            currentActs = activitiUtils.runtimeService.getActiveActivityIds(processInstance.getId());
        }
        return activitiUtils.processEngine.getProcessEngineConfiguration()
                .getProcessDiagramGenerator()
                .generateDiagram(model, "png", currentActs, new ArrayList<String>(),
                        fontName, fontName, fontName, null, 1.0);
    }
}
