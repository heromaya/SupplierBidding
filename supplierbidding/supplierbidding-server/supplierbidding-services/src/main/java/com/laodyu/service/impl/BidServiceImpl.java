package com.laodyu.service.impl;

import com.laodyu.commons.constant.ENApproveState;
import com.laodyu.commons.constant.ENCancelState;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.dao.ApplyFormDao;
import com.laodyu.dao.SupplierlistDao;
import com.laodyu.entity.*;
import com.laodyu.service.BidService;
import com.laodyu.service.ProjectService;
import com.laodyu.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/31
 * @Version 1.0
 **/
@Service
public class BidServiceImpl implements BidService {

    @Autowired
    ProjectService projectService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    SupplierlistDao supplierlistDao;
    @Autowired
    ApplyFormDao applyFormDao;
    /***
     * 供应商报名参加项目
     * @param urid
     * @param currentUser
     */
    @Override
    public void confirm(int urid, User currentUser) {
        SysProject project = projectService.fingByUrid(urid);
        Supplier supplier = supplierService.findByUserid(currentUser.getCode());
        Supplierlist supplierlist = new Supplierlist();
        supplierlist.setCode(project.getCode());
        supplierlist.setApprovestate(ENApproveState.APPROVING.getValue());
        supplierlist.setProjectid(urid);
        supplierlist.setSupplierid(supplier.getUrid());
        supplierlist.setCreatedby(currentUser.getCode());
        supplierlist.setCreatedon(new Timestamp(System.currentTimeMillis()));
        supplierlist.setUpdatedby(currentUser.getCode());
        supplierlist.setUpdatedon(new Timestamp(System.currentTimeMillis()));
        supplierlist.setTenderid(project.getTenderingid());
        supplierlistDao.save(supplierlist);
    }

    @Override
    public RespPage getConfirmSupplier(int page , int size ,int projectid) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("updatedon").descending());
        Specification<Supplierlist> specification = new Specification<Supplierlist>() {
            @Override
            public Predicate toPredicate(Root<Supplierlist> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("projectid").as(String.class),projectid));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<Supplierlist> allPage = supplierlistDao.findAll(specification, pageRequest);
        long total = allPage.getTotalElements();
        List<Supplierlist> supplierlistList = allPage.getContent();
        for(Supplierlist supplierlist :supplierlistList){
            SysProject project = projectService.fingByUrid(projectid);
            int supplierid = supplierlist.getSupplierid();
            Supplier supplier = supplierService.findByUrid(supplierid);
            supplierlist.setProject(project);
            supplierlist.setSupplier(supplier);
        }
        return new RespPage(total,supplierlistList);
    }

    @Override
    public RespPage getConfirmByTenderId(int page, int size, String code) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("updatedon").descending());
        Specification<Supplierlist> specification = new Specification<Supplierlist>() {
            @Override
            public Predicate toPredicate(Root<Supplierlist> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("tenderid").as(String.class),code));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<Supplierlist> allPage = supplierlistDao.findAll(specification, pageRequest);
        long total = allPage.getTotalElements();
        List<Supplierlist> supplierlistList = allPage.getContent();
        for(Supplierlist supplierlist :supplierlistList){
            int projectid = supplierlist.getProjectid();
            SysProject project = projectService.fingByUrid(projectid);
            int supplierid = supplierlist.getSupplierid();
            Supplier supplier = supplierService.findByUrid(supplierid);
            supplierlist.setProject(project);
            supplierlist.setSupplier(supplier);
        }
        return new RespPage(total,supplierlistList);
    }

    @Override
    public Supplierlist checkConfirm(int projectid, int supplierid) {
        return supplierlistDao.findByProjectidAndSupplierid(projectid,supplierid);
    }

    @Override
    public void updateSupplierlist(int urid, String approvestate,User currentUser) {
        String updatedBy = currentUser.getCode();
        Timestamp updatedon = new Timestamp(System.currentTimeMillis());
        supplierlistDao.updateApprovestate(urid,approvestate,updatedBy,updatedon);
    }

    @Override
    public RespPage getSupplierBySupplierId(int page, int size, int urid) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("updatedon").descending());
        Specification<Supplierlist> specification = new Specification<Supplierlist>() {
            @Override
            public Predicate toPredicate(Root<Supplierlist> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("supplierid").as(String.class),urid));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<Supplierlist> allPage = supplierlistDao.findAll(specification, pageRequest);
        long total = allPage.getTotalElements();
        List<Supplierlist> supplierlistList = allPage.getContent();
        for(Supplierlist supplierlist :supplierlistList){
            int projectid = supplierlist.getProjectid();
            SysProject project = projectService.fingByUrid(projectid);
            int supplierid = supplierlist.getSupplierid();
            Supplier supplier = supplierService.findByUrid(supplierid);
            supplierlist.setProject(project);
            supplierlist.setSupplier(supplier);
        }
        return new RespPage(total,supplierlistList);
    }

    @Override
    public void applyBuyTenderBook(Map<String, String> params, User user,int supplierid) {
        Applyform applyform = new Applyform();
        applyform.setSupplierid(supplierid);
        int projectid = Integer.valueOf(params.get("projectid"));
        SysProject project = projectService.fingByUrid(projectid);
        applyform.setProjectid(projectid);
        applyform.setCode(project.getCode());
        applyform.setContent(params.get("content"));
        applyform.setApprovestate(ENApproveState.APPROVING.getValue());
        applyform.setCancelstate(ENCancelState.NOCANCEL.getValue());
        applyform.setPrincipal(project.getTenderingid());
        applyform.setCreatedby(user.getCode());
        applyform.setCreatedon(new Timestamp(System.currentTimeMillis()));
        applyform.setUpdatedby(user.getCode());
        applyform.setUpdatedon(new Timestamp(System.currentTimeMillis()));
        applyFormDao.save(applyform);
    }

    @Override
    public Applyform checkApplyForm(int supplierid, int projectid) {
        Applyform applyForm = applyFormDao.findByProjectidAndSupplierid(projectid, supplierid);
        return applyForm;
    }

    @Override
    public RespPage getApplyFormListBySupplierid(int page , int size ,int supplierUrid) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("updatedon").descending());
        Specification<Applyform> specification = new Specification<Applyform>() {
            @Override
            public Predicate toPredicate(Root<Applyform> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("supplierid").as(String.class),supplierUrid));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<Applyform> all = applyFormDao.findAll(specification, pageRequest);
        long total = all.getTotalElements();
        List<Applyform> applyformList = all.getContent();
        for(Applyform applyform :applyformList){
            int projectid = applyform.getProjectid();
            SysProject project = projectService.fingByUrid(projectid);
            applyform.setProject(project);
        }
        return new RespPage(total,applyformList);
    }

    @Override
    public RespPage getApplyFormListByPrincipal(int page, int size, String code) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("updatedon").descending());
        Specification<Applyform> specification = new Specification<Applyform>() {
            @Override
            public Predicate toPredicate(Root<Applyform> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("principal").as(String.class),code));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<Applyform> all = applyFormDao.findAll(specification, pageRequest);
        long total = all.getTotalElements();
        List<Applyform> applyformList = all.getContent();
        for(Applyform applyform :applyformList){
            int projectid = applyform.getProjectid();
            SysProject project = projectService.fingByUrid(projectid);
            int supplierid = applyform.getSupplierid();
            Supplier supplier = supplierService.findByUrid(supplierid);
            applyform.setProject(project);
            applyform.setSupplier(supplier);
        }
        return new RespPage(total,applyformList);
    }

    @Override
    public void updateApplyFormApprovestate(int applyFormid, String approvestate, User session) {
        applyFormDao.updateApprovestate(applyFormid,approvestate,session.getCode(),new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public List<SysProject> getAllBuyProject(int supplierid) {
        List<Applyform> applyformList = applyFormDao.findBySupplieridAndApprovestate(supplierid, ENApproveState.APPROVED.getValue());
        List<SysProject> projectList = new ArrayList<>();
        for(Applyform applyform : applyformList){
            int projectid = applyform.getProjectid();
            SysProject project = projectService.fingByUrid(projectid);
            projectList.add(project);
        }
        return projectList;
    }

    @Override
    public List<Supplier> getSupplierIdByProjectId(int projectid) {
        List<Supplierlist> supplierlistList = supplierlistDao.findByProjectidAndApprovestate(projectid, ENApproveState.APPROVED.getValue());
        List<Integer> supplieridList = new ArrayList<>();
        //获取所有供应商id
        for(Supplierlist supplierlist : supplierlistList){
            supplieridList.add(supplierlist.getSupplierid());
        }
        //根据供应商id获取供应商
        List<Supplier> supplierList = new ArrayList<>();
        for(Integer supplierid : supplieridList){
            Supplier supplier = supplierService.findByUrid(supplierid);
            supplierList.add(supplier);
        }
        return supplierList;
    }


}
