package com.laodyu.web.controller.tendering;

import com.laodyu.commons.constant.ENApproveState;
import com.laodyu.commons.constant.ENCancelState;
import com.laodyu.commons.constant.ENProcessKey;
import com.laodyu.commons.exception.BizException;
import com.laodyu.commons.resp.RespBean;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.entity.*;
import com.laodyu.service.*;
import com.laodyu.service.impl.UserFileServiceImpl;
import com.laodyu.web.activiti.service.ActivitiService;
import com.laodyu.web.util.ActivitiUtils;
import com.laodyu.web.util.SessionUtil;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/8
 * @Version 1.0
 **/
@RestController
@RequestMapping("/tendering")
public class TenderingApplyProjectController {
    @Autowired
    UserFileService userFileService;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProductService productService;

    @Autowired
    ActivitiService activitiService;

    @Autowired
    BidService bidService;

    @Autowired
    ExpertService expertService;

    @Autowired
    UserService userService;

    /***
     * 新增招标项目
     */
    @PostMapping("/addProject/")
    public RespBean addProject(@RequestBody SysProject project) {
        //数据校验
        if (StringUtils.isEmpty(project.getProjectcode())) {
            throw new BizException("项目编号不能为空！");
        }
        if (StringUtils.isEmpty(project.getProjectname())) {
            throw new BizException("项目名称不能为空！");
        }
        if (StringUtils.isEmpty(project.getTenderingid())) {
            throw new BizException("编制人不能为空！");
        }
        if (project.getProductList() != null && project.getProductList().size() > 0) {
            for (Product product : project.getProductList()) {
                int index = project.getProductList().indexOf(product);
                if (StringUtils.isEmpty(product.getName())) {
                    index = index + 1;
                    throw new BizException("第 " + index + "行中的采购明细中的物品名称不能为空！");
                }
                if (StringUtils.isEmpty(product.getType())) {
                    throw new BizException("第 " + index + "行中的采购明细中的规格型号不能为空！");
                }
                if (StringUtils.isEmpty(product.getUnit())) {
                    throw new BizException("第 " + index + "行中的采购明细中的计量单位不能为空！");
                }
            }
        }
        User currentUser = SessionUtil.getSession();
        //保存项目
        projectService.AddTenderingProject(project, currentUser);
        if (project.getProductList() != null && project.getProductList().size() > 0) {
            //获取项目URID
            List<Product> productList = project.getProductList();
            SysProject newProject = projectService.getByProjectcode(project.getProjectcode());
            int projectId = newProject.getUrid();
            for (Product product : productList) {
                product.setProjectid(projectId);
                productService.addProduce(product, currentUser);
            }
        }
        return RespBean.ok("新增项目成功");
    }

    @GetMapping("/getAllProjectByCode")
    public RespPage getAllProjectByCode(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        User user = SessionUtil.getSession();
        page = page - 1;
        RespPage respPage = projectService.getAllProjectByCode(page, size, user.getCode());
        return respPage;
    }

    @GetMapping("/getAllApprovedProject")
    public RespPage getAllApprovedProject(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        User user = SessionUtil.getSession();
        page = page - 1;
        RespPage respPage = projectService.getAllApprovedProject(page, size, user.getCode());
        return respPage;
    }

    @GetMapping("/approveProject")
    public RespBean approveProject(String projectcode) {
        User currentUser = SessionUtil.getSession();
        //启动工作流
        String processInstanceId = activitiService.stratProcess(ENProcessKey.TenderingApplyProject.getValue(), currentUser.getCode());
        //更新状态
        projectService.updateProject(projectcode, ENApproveState.APPROVING.getValue(), ENCancelState.NOCANCEL.getValue(), processInstanceId, currentUser);
        return RespBean.ok("送审成功！");
    }

    /***
     * 获取所有供应商申请购买招标文件列表
     */
    @GetMapping("/getApplyForm")
    public RespPage getApplyForm(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        page = page - 1;
        User user = SessionUtil.getSession();
        String code = user.getCode();
        RespPage respPage = bidService.getApplyFormListByPrincipal(page, size, code);
        return respPage;
    }

    /***
     * 修改购买文件列表的审核状态
     */
    @GetMapping("changeApplyFormStatus")
    public RespBean changeApplyFormStatus(String approvestate, int applyFormid) {
        bidService.updateApplyFormApprovestate(applyFormid, approvestate, SessionUtil.getSession());
        return RespBean.ok("操作成功！");
    }

    /***
     * 抽取专家
     */
    @PostMapping("/buildExpertGroup")
    public List<Expert> buildExpertGroup(@RequestParam Map<String, String> params) {
        return expertService.buildExperGroup(params);
    }

    /***
     * 保存专家组
     */
    @PostMapping(value = "/saveExpertGroup")
    public RespBean saveExpertGroup(String groupName, int projectId, @RequestBody List<Expert> expertList) {
        //检查项目是否已存在专家组
        Grouplist grouplist = expertService.findGroupList(projectId);
        if (grouplist != null) {
            throw new BizException("该项目已存在专家组，不允许重复组建！");
        }
        User currentUser = SessionUtil.getSession();
        expertService.saveExpertGroup(groupName, projectId, expertList, currentUser);
        return RespBean.ok("保存成功！");
    }

    /***
     * 获取专家组列表
     */
    @GetMapping("/getExpertGroupList")
    public RespPage getExpertGroupList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        page = page - 1;
        User currentUser = SessionUtil.getSession();
        return expertService.getExpertGroupList(page, size, currentUser.getCode());
    }

    /***
     * 获取所有用户
     */
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        User user = SessionUtil.getSession();
        return userService.findByStatus(user.getUrid());
    }
}
