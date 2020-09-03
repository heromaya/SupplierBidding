package com.laodyu.service.impl;

import com.laodyu.commons.constant.ENApproveState;
import com.laodyu.commons.constant.ENCancelState;
import com.laodyu.commons.resp.RespBean;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.dao.ProjectDao;
import com.laodyu.entity.Product;
import com.laodyu.entity.SysProject;
import com.laodyu.entity.User;
import com.laodyu.service.ProductService;
import com.laodyu.service.ProjectService;
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

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/12
 * @Version 1.0
 **/
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectDao projectDao;
    @Autowired
    ProductService productService;

    @Override
    public RespBean AddTenderingProject(SysProject project, User currentUser) {
        SysProject newProject = addToNewProject(project,currentUser);
        projectDao.save(newProject);
        return  RespBean.ok("保存成功！");
    }

    @Override
    public SysProject getByProjectName(String projectname) {
        SysProject project = projectDao.findByProjectname(projectname);
        return project;
    }

    @Override
    public SysProject getByProjectcode(String projectcode) {
        SysProject project = projectDao.findByProjectcode(projectcode);
        return project;
    }

    @Override
    public RespPage getAllProjectByCode(Integer page, Integer size,String code) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("updatedon").descending());
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add( criteriaBuilder.equal(root.get("createdby").as(String.class),code));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<SysProject> all = projectDao.findAll(specification,pageRequest);
        long total = all.getTotalElements();
        List<SysProject> projectList = all.getContent();
        for(SysProject project : projectList){
            List<Product> productList = productService.getProductsByProjectid(project.getUrid());
            project.setProductList(productList);
        }

        return new RespPage(total,projectList);
    }

    /***
     * 更新项目状态
     * @param projectCode
     * @param approveState
     * @param cancelState
     * @param currentUser
     */
    @Override
    public void updateProject(String projectCode, String approveState, String cancelState, String processInstanceId,User currentUser) {
        String updatedby = currentUser.getCode();
        Timestamp updatedon = currentUser.getUpdatedon();
        projectDao.updateProject(projectCode,approveState,cancelState,processInstanceId,updatedby,updatedon);
    }

    @Override
    public RespPage getAllProject(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size,Sort.by("updatedon").descending());
        Page<SysProject> all = projectDao.findAll(pageRequest);
        long total = all.getTotalElements();
        List<SysProject> projectList = all.getContent();
        for(SysProject project : projectList){
            List<Product> productList = productService.getProductsByProjectid(project.getUrid());
            project.setProductList(productList);
        }
        return new RespPage(total,projectList);
    }

    @Override
    public RespPage findProject(Integer page, Integer size, String approvestate, String cancelstate) {
        PageRequest pageRequest = PageRequest.of(page, size,Sort.by("updatedon").descending());
        Specification<SysProject> specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("approvestate").as(String.class),approvestate));
                list.add(criteriaBuilder.equal(root.get("cancelstate").as(String.class),cancelstate));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<SysProject> all = projectDao.findAll(specification, pageRequest);
        long total = all.getTotalElements();
        List<SysProject> projectList = all.getContent();
        return new RespPage(total,projectList);
    }

    @Override
    public RespPage getAllApprovedProject(Integer page, Integer size, String code) {
        PageRequest pageRequest = PageRequest.of(page, size,Sort.by("updatedon").descending());
        Specification<SysProject> specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("createdby").as(String.class),code));
                list.add(criteriaBuilder.equal(root.get("approvestate").as(String.class),ENApproveState.APPROVED.getValue()));
                list.add(criteriaBuilder.equal(root.get("cancelstate").as(String.class),ENCancelState.NOCANCEL.getValue()));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<SysProject> all = projectDao.findAll(specification, pageRequest);
        long total = all.getTotalElements();
        List<SysProject> projectList = all.getContent();
        return new RespPage(total,projectList);
    }

    @Override
    public SysProject getProjectByUrid(int projectid) {
        return projectDao.findByUrid(projectid);
    }

    @Override
    public void updateCode(String code,String projectCode) {
        projectDao.updateCode(code,projectCode);
    }

    @Override
    public SysProject fingByUrid(int urid) {
        return projectDao.findByUrid(urid);
    }

    private SysProject addToNewProject(SysProject project,User currentUser) {
        String createdby = currentUser.getCode();
        String updatedby = currentUser.getCode();
        Timestamp createdon = new Timestamp(System.currentTimeMillis());
        Timestamp updatedon = new Timestamp(System.currentTimeMillis());
        project.setApprovestate(ENApproveState.NOTAPPROVE.getValue());
        project.setCancelstate(ENCancelState.NOCANCEL.getValue());
        project.setCreatedby(createdby);
        project.setCreatedon(createdon);
        project.setUpdatedby(updatedby);
        project.setUpdatedon(updatedon);
        return  project;
    }
}
