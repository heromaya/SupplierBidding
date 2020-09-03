package com.laodyu.dao;

import com.laodyu.entity.Supplierlist;
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
 * @Date 2020/5/31
 * @Version 1.0
 **/
public interface SupplierlistDao extends JpaRepository<Supplierlist,Integer>, JpaSpecificationExecutor<Supplierlist> {
    Supplierlist findByProjectidAndSupplierid(int projectid , int supplierid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_sys_supplierlist set approvestate=:approvestate , updatedby=:updatedBy,updatedon=:updatedon,reversion=reversion+1 where urid=:urid",nativeQuery = true)
    void updateApprovestate(int urid, String approvestate, String updatedBy, Timestamp updatedon);

    List<Supplierlist> findByProjectidAndApprovestate(int projectid,String approvestate);
}
