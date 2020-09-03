package com.laodyu.web.activiti.controller;

import com.laodyu.commons.resp.RespBean;
import com.laodyu.entity.Mytask;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/9
 * @Version 1.0
 **/
@RestController
@RequestMapping("test")
public class ActivitiTest {
    @Autowired
    IdentityService identityService;
    @Autowired
    ProcessEngine processEngine;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    HistoryService historyService;

    @GetMapping("/addUser")
    public RespBean addUser() {
        //项目中每创建一个新用户，对应的要创建一个Activiti用户,两者的userId和userName一致

        //添加用户
        User user1 = identityService.newUser("approve1");
        identityService.saveUser(user1);

        User user2 = identityService.newUser("approve2");
        identityService.saveUser(user2);

        return RespBean.ok("创建成功！");
    }

    /***
     * 根据userid查询Activiti用户
     */
    @GetMapping("/getUser")
    public RespBean getUser(String userid) {
        User user = identityService.createUserQuery().userId(userid).singleResult();
        return RespBean.ok("Activiti信息用户：", user);
    }

    /***
     * 创建用户组
     */
    @GetMapping("/addUserGroup")
    public RespBean addUserGroup() {
        Group group1 = identityService.newGroup("group1");
        group1.setName("员工组");
        group1.setType("员工组");
        identityService.saveGroup(group1);
        Group group2 = identityService.newGroup("group2");
        group2.setName("总监组");
        group2.setType("总监组");
        identityService.saveGroup(group2);
        Group group3 = identityService.newGroup("group3");
        group3.setName("经理组");
        group3.setType("经理组");
        identityService.saveGroup(group3);
        Group group4 = identityService.newGroup("group4");
        group4.setName("人力资源组");
        group4.setType("人力资源组");
        identityService.saveGroup(group4);
        return RespBean.ok("创建成功！");
    }

    /***
     * 获取用户组
     * @param groupid
     * @return
     */
    @GetMapping("/getUserGroup")
    public RespBean getUserGroup(String groupid) {
        GroupQuery groupQuery = identityService.createGroupQuery();
        Group group = groupQuery.groupId(groupid).singleResult();
        return RespBean.ok("用户组信息：", group);
    }

    /***
     * 创建用户与用户组的联系
     */
    @GetMapping("/addMemberShip")
    public RespBean addMemberShip() {
        identityService.createMembership("user1", "group1");
        identityService.createMembership("user2", "group2");
        identityService.createMembership("user3", "group3");
        identityService.createMembership("user4", "group4");
        return RespBean.ok("创建成功！");
    }

    /***
     * 查询用户组的成员
     */
    @GetMapping("/getUSersByGroup")
    public RespBean getGroupMember(String groupid) {
        List<User> list = identityService.createUserQuery().memberOfGroup(groupid).list();
        return RespBean.ok("所查询的用户组的成员：" + list);
    }

    /***
     * 查询成员所属的用户组
     */
    @GetMapping("/getGroupByUser")
    public RespBean getGroupByUser(String userid) {
        List<Group> list = identityService.createGroupQuery().groupMember(userid).list();
        return RespBean.ok("所查询的用户所在的组：", list);
    }

    /***
     * 前端指定了操作用户，这里使用流程定义的Key直接启动
     */
    @GetMapping("/satrtProcess")
    public RespBean satrtProcess() {
        String key = "Test";
        //使用流程定义的key启动流程实例
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey(key);
        String message = "流程实例ID:" + processInstance.getId();
        message += " **** 流程定义ID:" + processInstance.getProcessDefinitionId();
        return RespBean.ok(message);

    }

    /***
     * 获取任务
     * @param userid
     * @return
     */
    @GetMapping("/getTask")
    public List<Mytask> getTask(String userid) {

        List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                /**查询条件（where部分）*/
                .taskAssignee(userid)//指定个人任务查询，指定办理人
//						.taskCandidateUser(candidateUser)//组任务的办理人查询
//						.processDefinitionId(processDefinitionId)//使用流程定义ID查询
//						.processInstanceId(processInstanceId)//使用流程实例ID查询
//						.executionId(executionId)//使用执行对象ID查询
                /**排序*/
                .orderByTaskCreateTime().asc()//使用创建时间的升序排列
                /**返回结果集*/
//						.singleResult()//返回惟一结果集
//						.count()//返回结果集的数量
//						.listPage(firstResult, maxResults);//分页查询
                .list();//返回列表
        List<Mytask> taskList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                Mytask mytask = new Mytask();
                mytask.setTaskid(task.getId());
                mytask.setAssignee(task.getAssignee());
                mytask.setProcessInstenceid(task.getProcessInstanceId());
                taskList.add(mytask);
                System.out.println("任务ID:" + task.getId());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务的创建时间:" + task.getCreateTime());
                System.out.println("任务的办理人:" + task.getAssignee());
                System.out.println("流程实例ID：" + task.getProcessInstanceId());
                System.out.println("执行对象ID:" + task.getExecutionId());
                System.out.println("流程定义ID:" + task.getProcessDefinitionId());
                System.out.println("########################################################");
            }
        }
        return taskList;
    }

    /***
     * 完成任务
     */
    @GetMapping("/completeTask")
    public RespBean completeTask() {
        String taskId = "75005";
        //完成任务的同时设置流程变量，根据流程变量的结果来进入哪一个节点任务
        Map<String, Object> args = new HashMap<>();
        args.put("time", "2");
        //完成任务
        processEngine.getTaskService().complete(taskId, args);
        return RespBean.ok("提交请假申请单了！");
    }

    /***
     * 查看流程图
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/queryImage",produces = MediaType.IMAGE_PNG_VALUE )
    public void image(HttpServletResponse response, @RequestParam String processInstanceId) {
        try {
            InputStream is = getDiagram(processInstanceId);
            if (is == null) {
                return;
            }
            response.setContentType("image/png");
            BufferedImage image = ImageIO.read(is);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "png", out);

            is.close();
            out.close();
        } catch (Exception ex) {
            System.out.println("查看失败");
        }

    }

    public InputStream getDiagram(String processInstanceId) {
        //获得流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = StringUtils.EMPTY;
        if (processInstance == null) {
            //查询已经结束的流程实例
            HistoricProcessInstance processInstanceHistory =
                    historyService.createHistoricProcessInstanceQuery()
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
        BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
        //获取流程实例当前的节点，需要高亮显示
        List<String> currentActs = Collections.EMPTY_LIST;
        if (processInstance != null) {
            currentActs = runtimeService.getActiveActivityIds(processInstance.getId());
        }
        return processEngine.getProcessEngineConfiguration()
                .getProcessDiagramGenerator()
                .generateDiagram(model, "png", currentActs, new ArrayList<String>(),
                        fontName, fontName, fontName, null, 1.0);
    }
    /***
     * 审批
     */
    @GetMapping("/approve")
    public RespBean approve(String userid,String taskid,String approvestate){
        //完成任务的同时设置流程变量，根据流程变量的结果来进入哪一个节点任务
        Map<String, Object> args = new HashMap<>();
        args.put("approvestate",approvestate);
        processEngine.getTaskService().complete(taskid,args);
        return RespBean.ok("ok");
    }
}


