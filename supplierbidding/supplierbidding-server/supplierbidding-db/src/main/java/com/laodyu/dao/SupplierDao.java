package com.laodyu.dao;


import com.laodyu.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/28
 * @Version 1.0
 **/
@Repository
public interface SupplierDao extends JpaRepository<Supplier,Integer>, JpaSpecificationExecutor<Supplier> {
    Supplier findByUserid(String userid);
    Supplier findByEmail(String email);
    Supplier findByTelephone(String telephone);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_sys_supplier set approvestate=:approvestate ,updatedby=:updatedby,updatedon=:updatedon,reversion=reversion+1 where userid=:userid" ,nativeQuery = true)
    void updateApprovestate(String userid, String approvestate, String updatedby, Timestamp updatedon);

    Supplier findByUrid(int supplierid);
}
