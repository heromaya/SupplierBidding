package com.laodyu.dao;

import com.laodyu.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/9
 * @Version 1.0
 **/
@Repository
public interface StudentDao extends JpaRepository<Student,Integer>, JpaSpecificationExecutor<Student> {

}
