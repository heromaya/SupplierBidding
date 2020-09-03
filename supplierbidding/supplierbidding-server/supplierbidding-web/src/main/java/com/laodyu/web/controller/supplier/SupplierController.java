package com.laodyu.web.controller.supplier;


import com.laodyu.commons.constant.ENApproveState;
import com.laodyu.commons.constant.ENRole;
import com.laodyu.commons.constant.ENUserState;
import com.laodyu.commons.resp.RespBean;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.entity.User;
import com.laodyu.entity.UserRole;
import com.laodyu.service.SupplierService;
import com.laodyu.service.UserRoleService;
import com.laodyu.service.UserService;
import com.laodyu.web.activiti.service.ActivitiService;
import com.laodyu.web.util.ActivitiUtils;
import com.laodyu.web.util.SessionUtil;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/8
 * @Version 1.0
 **/
@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    SupplierService supplierService;
    @Autowired
    ActivitiService activitiService;
    @Autowired
    ProcessEngine processEngine;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserService userService;
    @GetMapping("/getAllSupplier")
    public RespPage getSupplierList(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "10" )Integer size){
        if(page!= null && size != null){
            page = page-1;
        }
        RespPage respPage = supplierService.getAllSupplier( page, size);
        return respPage;
    }
    @GetMapping("/queryByApproveAndCancel")
    public RespPage getSupplierList(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "10" )Integer size,
                                          @RequestParam(defaultValue = "3") String approveState ,@RequestParam(defaultValue = "1") String cancelState){
        if(page!= null && size != null){
            page = page-1;
        }
        RespPage respPage = supplierService.findAllSupplier(approveState, cancelState, page, size);
        return respPage;
    }

    @GetMapping("/approve")
    public RespBean approve(@RequestParam String processInstanceId,@RequestParam String userid,@RequestParam String approvestate){
        Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstanceId).singleResult();
        String taskid = task.getId();
        activitiService.finishProcess(taskid,"approvestate",approvestate);
        //修改审批状态
        User currentuser = SessionUtil.getSession();
        supplierService.updateApprovestate(userid,approvestate,currentuser);
        //添加权限
        if(approvestate.equals(ENApproveState.APPROVED.getValue())){
            //根据账户id查找    账户信息
            User approveUser = userService.findByCode(userid);
            //审核通过，添加权限
            userRoleService.AddUserRole(approveUser.getUrid(),Integer.valueOf(ENRole.ROLE_SUPPLIER.getValue()),currentuser);
            //修改账户的status
            String code[] = {userid};
            userService.changeUserStatus(code, ENUserState.NORMAL.getValue(),currentuser);
        }
        return RespBean.ok("操作成功！");
    }

    /***
     * 获取流程图片
     * @param response
     * @param processInstanceId
     * @throws IOException
     */
    @ResponseBody
    @GetMapping(value = "/getImage",produces = MediaType.IMAGE_PNG_VALUE )
    public void getImage(HttpServletResponse response, @RequestParam String processInstanceId) throws IOException {
        BufferedImage processImage = ActivitiUtils.getProcessImage(processInstanceId);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(processImage, "png", outputStream);
        outputStream.close();
    }
}
