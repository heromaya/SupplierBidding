package com.laodyu.dao;

import com.laodyu.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/8
 * @Version 1.0
 **/
@Repository
public interface NoticeDao extends JpaRepository<Notice,Integer> , JpaSpecificationExecutor<Notice> {


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_sys_notice set approvestate = :approvestate,processinstanceid=:processinstanceid,updatedon=:updatedon,updatedby=:updatedby,reversion=reversion+1 where urid=:urid",nativeQuery = true)
    void updateApproveState(int urid ,String approvestate, String updatedby, Timestamp updatedon,String processinstanceid);
}
