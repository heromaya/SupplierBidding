package com.laodyu.service.impl;

import com.laodyu.commons.exception.BizException;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.dao.CommontBookDao;
import com.laodyu.dao.ExpertDao;
import com.laodyu.dao.ExpertGroupDao;
import com.laodyu.dao.GrouplistDao;
import com.laodyu.entity.*;
import com.laodyu.service.BidService;
import com.laodyu.service.ExpertService;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/14
 * @Version 1.0
 **/
@Service
public class ExpertServiceImpl implements ExpertService {
    @Autowired
    ExpertDao expertDao;
    @Autowired
    ExpertGroupDao expertGroupDao;
    @Autowired
    GrouplistDao grouplistDao;
    @Autowired
    ProjectService projectService;
    @Autowired
    BidService bidService;
    @Autowired
    CommontBookDao commontBookDao;
    @Override
    public RespPage getAllExpert(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdon").descending());
        Page<Expert> all = expertDao.findAll(pageRequest);
        long total = all.getTotalElements();
        List<Expert> expertList = all.getContent();
        return new RespPage(total, expertList);
    }

    @Override
    public Expert findByCode(String code) {
        Expert expert = expertDao.findByCode(code);
        return expert;
    }

    @Override
    public void addExpert(Expert expert) {
        expertDao.save(expert);
    }

    @Override
    public void updateExpert(Expert expert) {
        expertDao.save(expert);
    }

    @Override
    public List<Expert> buildExperGroup(Map<String, String> params) {
        int typeid = Integer.valueOf(params.get("expertType"));
        int count = Integer.valueOf(params.get("expertCount"));
        List<Expert> expertList = expertDao.findByTypeid(typeid);
        //获取随机数
        int length = expertList.size();
        if (length < count) {
            throw new BizException("该类型的专家数量不足，请减少专家组的成员重试！");
        }
        List<Expert> result = new ArrayList<>();
        Random random = new Random();
        List<Integer> list = new ArrayList();
        while (list.size() != count) {
            int num = random.nextInt(length);
            if (!list.contains(num)) {
                list.add(num);
            }
        }
        for (int num : list) {
            result.add(expertList.get(num));
        }
        return result;
    }

    @Override
    public void saveExpertGroup(String groupName, int projectId, List<Expert> expertList, User currentUser) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date();
        String str1 = sdf1.format(d1);
        Grouplist grouplist = new Grouplist();
        grouplist.setCode(projectId + str1);
        grouplist.setCount(expertList.size());
        grouplist.setName(groupName);
        grouplist.setProjectid(projectId);
        grouplist.setCreatedby(currentUser.getCode());
        grouplist.setCreatedon(new Timestamp(System.currentTimeMillis()));
        grouplist.setUpdatedby(currentUser.getCode());
        grouplist.setUpdatedon(new Timestamp(System.currentTimeMillis()));
        grouplistDao.save(grouplist);
        Grouplist newGroupList = grouplistDao.findByProjectid(projectId);
        int groupListUrid = newGroupList.getUrid();
        for (Expert expert : expertList) {
            Expertgroup expertgroup = new Expertgroup();
            expertgroup.setGrouplistid(groupListUrid);
            expertgroup.setStatus("0");
            expertgroup.setExpertid(expert.getUrid());
            expertgroup.setProjectid(projectId);
            expertgroup.setCreatedby(currentUser.getCode());
            expertgroup.setUpdatedby(currentUser.getCode());
            expertgroup.setCreatedon(new Timestamp(System.currentTimeMillis()));
            expertgroup.setUpdatedon(new Timestamp(System.currentTimeMillis()));
            expertGroupDao.save(expertgroup);
        }

    }

    @Override
    public RespPage getExpertGroupList(int page, int size, String code) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("updatedon").descending());
        Specification<Grouplist> specification = new Specification<Grouplist>() {
            @Override
            public Predicate toPredicate(Root<Grouplist> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("createdby").as(String.class),code));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<Grouplist> all = grouplistDao.findAll(specification, pageRequest);
        long total = all.getTotalElements();
        List<Grouplist> content = all.getContent();
        for(Grouplist grouplist : content){
            Integer projectid = grouplist.getProjectid();
            SysProject project = projectService.getProjectByUrid(projectid);
            grouplist.setProject(project);
        }
        return new RespPage(total,content);
    }

    @Override
    public Grouplist findGroupList(int projectId) {
        return grouplistDao.findByProjectid(projectId);
    }

    @Override
    public RespPage getAllExpertProject(int page, int size, int  expertid) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("updatedon").descending());
        Specification<Expertgroup> specification = new Specification<Expertgroup>() {
            @Override
            public Predicate toPredicate(Root<Expertgroup> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("expertid").as(Integer.class),expertid));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<Expertgroup> all = expertGroupDao.findAll(specification, pageRequest);
        long total = all.getTotalElements();
        List<Expertgroup> content = all.getContent();
        for(Expertgroup expertgroup : content){
            Integer grouplistid = expertgroup.getGrouplistid();
            Grouplist grouplist = grouplistDao.findByUrid(grouplistid);
            expertgroup.setGrouplist(grouplist);
            Integer projectid = expertgroup.getProjectid();
            SysProject project = projectService.fingByUrid(projectid);
            expertgroup.setProject(project);
        }
        return new RespPage(total,content);
    }

    @Override
    public List<SysProject> getProject(int expertid) {
        List<Expertgroup> expertgroupList = expertGroupDao.findByExpertid(expertid);
        List<Integer> projectIDList = new ArrayList<>();
        for(Expertgroup expertgroup : expertgroupList){
            projectIDList.add(expertgroup.getProjectid());
        }
        List<SysProject> projectList = new ArrayList<>();
        for (Integer projectid :projectIDList){
            SysProject project = projectService.fingByUrid(projectid);
            //获取所有审核通过的供应商
            List<Supplier> supplierList = bidService.getSupplierIdByProjectId(project.getUrid());
            project.setSupplierList(supplierList);
            projectList.add(project);
        }

        return projectList;
    }

    @Override
    public void saveCommontBook(Commontbook commontbook, User currentUser) {
        commontbook.setCreatedby(currentUser.getCode());
        commontbook.setCreatedon(new Timestamp(System.currentTimeMillis()));
        commontbook.setUpdatedby(currentUser.getCode());
        commontbook.setUpdatedon(new Timestamp(System.currentTimeMillis()));
        commontBookDao.save(commontbook);
    }

    @Override
    public void updateExpertGroupStstus(int projectid, int expertid,String updatedby) {

        expertGroupDao.updateStatus(projectid,expertid,"1",updatedby,new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public Commontbook checkCommonBook(int projectid, Integer supplierid, String createdby) {
        return commontBookDao.findByProjectidAndSupplieridAndCreatedby(projectid,supplierid,createdby);
    }
}
