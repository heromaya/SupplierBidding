package com.laodyu.service.impl;

import com.laodyu.commons.resp.RespPage;
import com.laodyu.dao.UserFileDao;
import com.laodyu.entity.Expertgroup;
import com.laodyu.entity.SysProject;
import com.laodyu.entity.User;
import com.laodyu.entity.Userfile;
import com.laodyu.service.ProjectService;
import com.laodyu.service.UserFileService;
import com.laodyu.service.UserService;
import org.apache.commons.lang.StringUtils;
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
import java.util.ArrayList;
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
@Service
public class UserFileServiceImpl implements UserFileService {
    @Autowired
    UserFileDao userFileDao;
    @Autowired
    ProjectService projectService;
    @Autowired
    UserService userService;

    @Override
    public void addFile(Userfile userfile) {
        userFileDao.save(userfile);
    }

    @Override
    public RespPage findAllByUserid(Integer page, Integer size, int urid) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("updatedon").descending());
        Specification<Userfile> specification = new Specification<Userfile>() {
            @Override
            public Predicate toPredicate(Root<Userfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("userid").as(Integer.class), urid));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<Userfile> all = userFileDao.findAll(specification, pageRequest);
        long total = all.getTotalElements();
        List<Userfile> userfileList = all.getContent();
        for (Userfile userfile : userfileList) {
            int projectid = userfile.getProjectid();
            SysProject project = projectService.getProjectByUrid(projectid);
            userfile.setProject(project);
            int userid = userfile.getUserid();
            User user = userService.findByUrid(userid);
            userfile.setUser(user);
        }
        return new RespPage(total, userfileList);
    }

    @Override
    public Userfile findByUrid(int urid) {
        return userFileDao.findByUrid(urid);
    }

    @Override
    public void deleteFile(int urid) {
        userFileDao.deleteById(urid);
    }

    @Override
    public void saveFile(Map<String, String> params, User user) {
        Userfile userfile = new Userfile();
        userfile.setUserid(user.getUrid());
        userfile.setContent(params.get("content"));
        userfile.setUploadtime(new Timestamp(System.currentTimeMillis()));
        userfile.setName(params.get("bookName"));
        userfile.setProjectid(Integer.valueOf(params.get("projectId")));
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date();
        String str1 = sdf1.format(d1);
        userfile.setCode(params.get("projectId") + str1);
        userfile.setType(params.get("type"));
        userfile.setAmount(params.get("amount"));
        userfile.setCreatedby(user.getCode());
        userfile.setCreatedon(new Timestamp(System.currentTimeMillis()));
        userfile.setUpdatedby(user.getCode());
        userfile.setUpdatedon(new Timestamp(System.currentTimeMillis()));
        userFileDao.save(userfile);
    }

    @Override
    public void updateUrl(String path, String projectId, String type,String uploadname) {
        userFileDao.updateUrlAndUploadName(path, projectId, type,uploadname);
    }

    @Override
    public Userfile findByProjectIdAndType(int projectId, String type) {
        return userFileDao.findByProjectidAndType(projectId, type);
    }

    @Override
    public List<SysProject> getAllProjectTenderBook() {
        List<Userfile> userfileList = userFileDao.findByType("1");
        List<SysProject> projectList = new ArrayList<>();
        for (Userfile userfile : userfileList) {
            int projectid = userfile.getProjectid();
            SysProject project = projectService.getProjectByUrid(projectid);
            project.setUserfile(userfile);
            projectList.add(project);
        }
        return projectList;
    }

    @Override
    public void upsetDownloadCount(int urid) {
        userFileDao.upsetDownloadCount(urid);
    }

    @Override
    public Userfile checkBidBookIsExist(int projectId, String code, String type) {
        return userFileDao.findByProjectidAndTypeAndCreatedby(projectId, type, code);
    }

    @Override
    public RespPage findByProjectId(int projectid, int page, int size) {
        SysProject project = projectService.fingByUrid(projectid);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("updatedon").descending());
        Specification<Userfile> specification = new Specification<Userfile>() {
            @Override
            public Predicate toPredicate(Root<Userfile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("projectid").as(Integer.class),projectid));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<Userfile> all = userFileDao.findAll(specification, pageRequest);
        long total = all.getTotalElements();
        List<Userfile> content = all.getContent();
        for(Userfile userfile : content){
            userfile.setProject(project);
        }
        return new RespPage(total,content);
    }


}
