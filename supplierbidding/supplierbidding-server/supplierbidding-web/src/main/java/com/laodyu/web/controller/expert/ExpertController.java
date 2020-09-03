package com.laodyu.web.controller.expert;

import com.laodyu.commons.exception.BizException;
import com.laodyu.commons.resp.RespBean;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.entity.*;
import com.laodyu.service.ExpertService;
import com.laodyu.service.ExpertTypeService;
import com.laodyu.web.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/14
 * @Version 1.0
 **/
@RestController
@RequestMapping("/expert")
public class ExpertController {
    @Autowired
    ExpertService expertService;
    @Autowired
    ExpertTypeService expertTypeService;

    @GetMapping("/getAllExpert")
    public RespPage getAllExpert(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        page = page - 1;
        RespPage respPage = expertService.getAllExpert(page, size);
        return respPage;
    }

    @PutMapping("/updateExpert")
    public RespBean updateExpert(@RequestBody Expert expert) {
        User currentUser = SessionUtil.getSession();
        expertService.updateExpert(expert);
        return RespBean.ok("操作成功！");
    }

    /***
     * 获取专家所在的评标专家组
     */
    @GetMapping("/getAllExpertProject")
    public RespPage getAllExpertProject(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue =
            "10") int size) {
        page = page - 1;
        User session = SessionUtil.getSession();
        String code = session.getCode();
        Expert expert = expertService.findByCode(code);
        return expertService.getAllExpertProject(page, size, expert.getUrid());
    }

    /***
     *根据专家id获取所有待评标的项目
     */
    @GetMapping("/getProject")
    public List<SysProject> getProject(){
        User user = SessionUtil.getSession();
        String code = user.getCode();
        Expert expert = expertService.findByCode(code);
        int urid = expert.getUrid();
        return expertService.getProject(urid);
    }

    /***
     * 保存评标书
     */
    @PostMapping("/saveCommontBook")
    public RespBean saveCommontBook(@RequestBody Commontbook commontbook){
        //检查是否重复提交
        User currentUser = SessionUtil.getSession();
        String updatedby = currentUser.getCode();
        Commontbook commontbook1 = expertService.checkCommonBook(commontbook.getProjectid(),commontbook.getSupplierid(),updatedby);
        if(commontbook1 != null){
            throw  new BizException("该招标项目下您选择的供应商已经评审了！");
        }
        Expert expert = expertService.findByCode(updatedby);
        expertService.saveCommontBook(commontbook,currentUser);
        //修改评标状态
        expertService.updateExpertGroupStstus(commontbook.getProjectid(),expert.getUrid(),updatedby);
        return RespBean.ok("保存成功！");
    }
}
