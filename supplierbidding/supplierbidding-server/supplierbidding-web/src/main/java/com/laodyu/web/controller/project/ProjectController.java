package com.laodyu.web.controller.project;

import com.laodyu.commons.constant.ENApproveState;
import com.laodyu.commons.constant.ENCancelState;
import com.laodyu.commons.resp.RespBean;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.entity.ProjectType;
import com.laodyu.entity.SysProject;
import com.laodyu.entity.User;
import com.laodyu.service.ProjectService;
import com.laodyu.service.ProjectTypeService;
import com.laodyu.web.activiti.service.ActivitiService;
import com.laodyu.web.util.SessionUtil;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/13
 * @Version 1.0
 **/
@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    ProcessEngine processEngine;
    @Autowired
    ActivitiService activitiService;
    @Autowired
    ProjectTypeService projectTypeService;
    @GetMapping("/getAllProject")
    public RespPage getAllPriject(@RequestParam(defaultValue = "1")Integer page ,@RequestParam(defaultValue = "10")Integer size){
        page = page -1;
        RespPage respPage = projectService.getAllProject(page, size);
        return respPage;
    }

    @GetMapping("/approve")
    public RespBean approveProject(String projectcode,String processInstanceId,String approvestate){
        //完成流程中的任务
        Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstanceId).singleResult();
        String taskId = task.getId();
        activitiService.finishProcess(taskId,"approvestate",approvestate);

        //修改审批状态
        User currentUser = SessionUtil.getSession();
        projectService.updateProject(projectcode,approvestate, ENCancelState.NOCANCEL.getValue(),processInstanceId,currentUser);
        //如果审核通过 生成招标编号
        if(approvestate.equals(ENApproveState.APPROVED.getValue())){
            SysProject project = projectService.getByProjectcode(projectcode);
            ProjectType projectType = projectTypeService.getByTypeid(project.getTypeid());
            String projectTypeCode = projectType.getCode();
            String format = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
            StringBuilder stringBuilder = new StringBuilder();
            String code = stringBuilder.append(projectTypeCode).append(projectcode).append(format).toString();
            projectService.updateCode(code,projectcode);
        }
        return RespBean.ok("操作成功！");
    }
    @GetMapping("/findProject")
    public RespPage findProject(@RequestParam(defaultValue = "1")Integer page ,@RequestParam(defaultValue = "10")Integer size,String approvestate,String cancelstate){
        page = page -1;
        RespPage respPage = projectService.findProject(page,size,approvestate,cancelstate);
        return respPage;
    }
}
