package com.laodyu.dao;

import com.laodyu.entity.Applyform;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/6/1
 * @Version 1.0
 **/
public interface ApplyFormDao extends JpaRepository<Applyform, Integer>, JpaSpecificationExecutor<Applyform> {
    Applyform findByProjectidAndSupplierid(int projectid, int supplierid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update  t_sys_applyform  set approvestate=:approvestate , updatedby=:updatedBy,updatedon=:updatedon,reversion=reversion+1 where urid=:urid", nativeQuery = true)
    void updateApprovestate(int urid, String approvestate, String updatedBy, Timestamp updatedon);

    List<Applyform> findBySupplieridAndApprovestate(int supplierid, String approvestate);
}
