package com.laodyu.dao;

import com.laodyu.entity.Grouplist;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/6/3
 * @Version 1.0
 **/
public interface GrouplistDao extends JpaRepository<Grouplist,Integer>, JpaSpecificationExecutor<Grouplist> {
    Grouplist findByProjectid(int projectid);

    Grouplist findByUrid(int urid);
}
