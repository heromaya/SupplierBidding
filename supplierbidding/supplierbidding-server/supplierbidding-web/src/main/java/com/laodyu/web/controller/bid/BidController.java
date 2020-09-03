package com.laodyu.web.controller.bid;

import com.laodyu.commons.constant.ENApproveState;
import com.laodyu.commons.exception.BizException;
import com.laodyu.commons.resp.RespBean;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.entity.*;
import com.laodyu.service.BidService;
import com.laodyu.service.SupplierService;
import com.laodyu.service.UserFileService;
import com.laodyu.web.util.SessionUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/31
 * @Version 1.0
 **/
@RestController
@RequestMapping("/bid")
public class BidController {
    @Autowired
    BidService bidService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    UserFileService userFileService;

    @GetMapping("/confirm")
    public RespBean confirm(int projectid) {
        //检查是否已经报名
        User currentUser = SessionUtil.getSession();
        Supplier supplier = supplierService.findByUserid(currentUser.getCode());
        Supplierlist supplierlist = bidService.checkConfirm(projectid, supplier.getUrid());
        if (supplierlist != null) {
            throw new BizException("您已经报名了，不允许重复报名!");
        }
        bidService.confirm(projectid, currentUser);
        return RespBean.ok("报名成功！");
    }

    @GetMapping("/getConfirmByProjectId")
    public RespPage getConfirmByProjectId(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, int projectid) {
        page = page - 1;
        RespPage respPage = bidService.getConfirmSupplier(page, size, projectid);
        return respPage;
    }

    @GetMapping("/getConfirmByTenderId")
    public RespPage getConfirmByTenderId(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        User currentuser = SessionUtil.getSession();
        page = page - 1;
        RespPage respPage = bidService.getConfirmByTenderId(page, size, currentuser.getCode());
        return respPage;
    }

    @GetMapping("getSupplierBySupplierId")
    public RespPage getSupplierBySupplierId(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        User currentuser = SessionUtil.getSession();
        Supplier supplier = supplierService.findByUserid(currentuser.getCode());
        page = page - 1;
        RespPage respPage = bidService.getSupplierBySupplierId(page, size, supplier.getUrid());
        return respPage;
    }

    @GetMapping("/changeApproveState")
    public RespBean changeApproveState(int urid, String approvestate) {
        User currentUser = SessionUtil.getSession();
        bidService.updateSupplierlist(urid, approvestate, currentUser);
        return RespBean.ok("操作成功！");
    }

    //获取所有招标项目信息
    @GetMapping("/getAllProjectTenderBook")
    public List<SysProject> getAllProjectTenderBook() {
        return userFileService.getAllProjectTenderBook();
    }

    @PostMapping("/applyBuyTenderBook")
    public RespBean applyBuyTenderBook(@RequestParam Map<String, String> params) {
        int projectid = Integer.valueOf(params.get("projectid"));
        User user = SessionUtil.getSession();
        String code = user.getCode();
        Supplier supplier = supplierService.findByUserid(code);
        int supplierid = supplier.getUrid();
        //检查该供应商是否报名通过
        Supplierlist supplierlist = bidService.checkConfirm(projectid, supplierid);
        if (supplierlist == null) {
            throw new BizException("你还未报名参加该招标项目，先去报名吧！");
        } else {
            if (supplierlist.getApprovestate().equals(ENApproveState.APPROVING.getValue())) {
                throw new BizException("你的报名信息还未审核，请等审核通过后再提交申请！");
            }
            if (supplierlist.getApprovestate().equals(ENApproveState.REFUSED.getValue())) {
                throw new BizException("对不起，您的报名未通过，不允许参加此次招标！");
            }
            if (supplierlist.getApprovestate().equals(ENApproveState.APPROVED.getValue())) {
                //检查是否重复提交申请
                Applyform applyform = bidService.checkApplyForm(supplierid, projectid);
                if (applyform == null) {
                    //提交申请
                    bidService.applyBuyTenderBook(params, user, supplierid);
                    return RespBean.ok("申请成功，请耐心等待招标商审核！");
                } else {
                    //审核未通过
                    if (applyform.getApprovestate().equals(ENApproveState.REFUSED.getValue())) {
                        throw new BizException("对不起，您的招标文件购买申请未通过审核！");
                    }
                    //还在审核中
                    if (applyform.getApprovestate().equals(ENApproveState.APPROVING.getValue())) {
                        throw new BizException("不允许重复提交申请，您的申请正在审核中，请耐心等候！");
                    }
                    //已审核通过
                    if (applyform.getApprovestate().equals(ENApproveState.APPROVED.getValue())) {
                        throw new BizException("不允许重复提交申请，您的申请已通过，可以去购买招标文件了！");
                    }
                }

            }
        }
        return RespBean.error("申请失败，请重试！");
    }

    /***
     * 查看申请招标文件购买列表
     */
    @GetMapping("/getApplyFormList")
    public RespPage getApplyFormList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        page = page -1;
        User user = SessionUtil.getSession();
        String code = user.getCode();
        Supplier supplier = supplierService.findByUserid(code);
        int supplierUrid = supplier.getUrid();
        RespPage respPage = bidService.getApplyFormListBySupplierid(page, size, supplierUrid);
        return respPage;
    }

    /***
     * 供应商下载文件
     */
    @GetMapping("/download")
    public void download(int projectid, HttpServletResponse response, HttpServletRequest request){
        Userfile userfile = userFileService.findByProjectIdAndType(projectid, "1");
        if(userfile == null){
            throw new BizException("招标商还没有编制招标文件！请耐心等候！");
        }
        String path = userfile.getUrl();
        String fileName = userfile.getUploadname();
        OutputStream os = null;
        InputStream is = null;
        try {
            //获取输入流
            os = response.getOutputStream();
            //清空输入流
            response.reset();
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
            File file = new File(path);
            is = new FileInputStream(file);
            if (is == null) {
                throw  new BizException("下载文件失败！");
            }
            //复制
            IOUtils.copy(is, os);
            os.flush();
            //更新下载次数
            userFileService.upsetDownloadCount(userfile.getUrid());
        } catch (IOException e) {
            throw  new BizException("下载文件失败！");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * 获取所有已购买招标文件的招标项目
     *
     */
    @GetMapping("/getAllBuyProject")
    public List<SysProject> getAllBuyProject(){
        User user = SessionUtil.getSession();
        String code = user.getCode();
        Supplier supplier = supplierService.findByUserid(code);
        return bidService.getAllBuyProject(supplier.getUrid());
    }

}
