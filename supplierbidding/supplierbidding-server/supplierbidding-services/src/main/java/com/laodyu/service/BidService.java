package com.laodyu.service;

import com.laodyu.commons.resp.RespPage;
import com.laodyu.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/31
 * @Version 1.0
 **/
public interface BidService {
    void confirm(int urid, User currentUser);

    RespPage getConfirmSupplier(int page , int size ,int projectid);

    RespPage getConfirmByTenderId(int page, int size, String code);

    Supplierlist checkConfirm(int projectid, int supplierid);

    void updateSupplierlist(int urid, String approvetste,User currentUser);

    RespPage getSupplierBySupplierId(int page, int size, int urid);

    void applyBuyTenderBook(Map<String, String> params, User user,int supplierid);

    Applyform checkApplyForm(int supplierid, int projectid);

    RespPage getApplyFormListBySupplierid(int page , int size ,int supplierUrid);

    RespPage getApplyFormListByPrincipal(int page, int size, String code);

    void updateApplyFormApprovestate(int applyFormid, String approvestate, User session);

    List<SysProject> getAllBuyProject(int supplierid);

    List<Supplier> getSupplierIdByProjectId(int urid);
}
