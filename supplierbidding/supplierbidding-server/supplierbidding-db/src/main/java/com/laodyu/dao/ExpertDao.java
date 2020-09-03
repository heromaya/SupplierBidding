package com.laodyu.dao;

import com.laodyu.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/14
 * @Version 1.0
 **/

public interface ExpertDao extends JpaRepository<Expert,Integer>, JpaSpecificationExecutor<Expert> {
    Expert findByCode(String code);

    List<Expert> findByTypeid(int typeid);
}
