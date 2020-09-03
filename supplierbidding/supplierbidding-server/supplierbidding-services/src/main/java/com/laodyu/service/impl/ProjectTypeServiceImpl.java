package com.laodyu.service.impl;

import com.laodyu.dao.ProjectTypeDao;
import com.laodyu.entity.ProjectType;
import com.laodyu.service.ProjectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/14
 * @Version 1.0
 **/
@Service
public class ProjectTypeServiceImpl implements ProjectTypeService {


    @Autowired
    ProjectTypeDao projectTypeDao;
    @Override
    public List<ProjectType> getAllProjectType() {
        return projectTypeDao.findAll();
    }

    @Override
    public ProjectType getByTypeid(int id) {
        return  projectTypeDao.findByUrid(id);
    }
}
