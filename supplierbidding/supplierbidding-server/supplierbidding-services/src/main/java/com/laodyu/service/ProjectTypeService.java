package com.laodyu.service;

import com.laodyu.entity.ProjectType;

import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/14
 * @Version 1.0
 **/
public interface ProjectTypeService {

    List<ProjectType> getAllProjectType();

    ProjectType getByTypeid(int typeid);
}
