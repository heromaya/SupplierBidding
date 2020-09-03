package com.laodyu.web.controller.notice;

import com.laodyu.commons.constant.ENApproveState;
import com.laodyu.commons.constant.ENProcessKey;
import com.laodyu.commons.resp.RespBean;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.entity.Notice;
import com.laodyu.entity.User;
import com.laodyu.service.NoticeService;
import com.laodyu.web.activiti.service.ActivitiService;
import com.laodyu.web.util.SessionUtil;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/8
 * @Version 1.0
 **/
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;
    @Autowired
    ActivitiService activitiService;
    @Autowired
    ProcessEngine processEngine;

    @GetMapping("/getAllNotice")
    public RespPage getAllNotice(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        RespPage resp = noticeService.getAllList(page, size);
        return resp;
    }

    @PostMapping("/addNotice")
    public RespBean addNotice(@RequestBody Notice notice) {
        User currentUser = SessionUtil.getSession();
        noticeService.addNotice(notice, currentUser);
        return RespBean.ok("新增公告成功，快去送审吧！");
    }

    @GetMapping("/getNoticeByCode")
    public RespPage getNoticeByCode(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        User user = SessionUtil.getSession();
        page = page - 1;
        RespPage respPage = noticeService.getNoticeByCode(page, size, user.getCode());
        return respPage;
    }

    /***
     * 送审
     * @param urid
     * @return
     */
    @GetMapping("/doApprove")
    public RespBean doApprove(int urid) {
        User currentUser = SessionUtil.getSession();
        //开始流程
        String processinstanceid = activitiService.stratProcess(ENProcessKey.TenderingApplyNotice.getValue(), currentUser.getCode());
        //更新状态
        noticeService.doApprove(urid, ENApproveState.APPROVING.getValue(), currentUser, processinstanceid);
        return RespBean.ok("操作成功！");
    }

    /***
     * 审批
     */
    @GetMapping("/approve")
    public RespBean approve(int urid, String approvestate, String processInstenceid) {
        User currentUser = SessionUtil.getSession();
        Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstenceid).singleResult();
        String taskId = task.getId();
        activitiService.finishProcess(taskId, "approvestate", approvestate);
        noticeService.doApprove(urid, approvestate, currentUser, processInstenceid);
        return RespBean.ok("操作成功");
    }

    /***
     * 查询公告信息
     */
    @GetMapping("/queryNoticeList")
    public RespPage queryNoticeList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                    @RequestParam(defaultValue = "3") String approvestate, @RequestParam(defaultValue = "1") String cancelstate) {
        page = page - 1;
        RespPage respPage = noticeService.queryNoticeList(page, size, approvestate, cancelstate);
        return respPage;
    }
}
