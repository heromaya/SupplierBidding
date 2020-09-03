package com.laodyu.dao;

import com.laodyu.entity.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/14
 * @Version 1.0
 **/
public interface ProjectTypeDao extends JpaRepository<ProjectType,Integer> {
    ProjectType findByUrid(int urid);
}
