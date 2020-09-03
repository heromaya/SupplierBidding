package com.laodyu.dao;

import com.laodyu.entity.SysProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/12
 * @Version 1.0
 **/
public interface ProjectDao extends JpaRepository<SysProject,Integer>, JpaSpecificationExecutor<SysProject> {
    SysProject findByProjectcode(String code);
    SysProject findByProjectname(String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update  t_sys_project set approvestate=:approveState,cancelstate=:cancelState,processinstanceid=:processInstanceId,updatedby=:updatedby," +
            "updatedon=:updatedon,reversion=reversion+1 where projectcode=:projectCode",nativeQuery = true)
    void updateProject(String projectCode, String approveState, String cancelState, String processInstanceId, String updatedby, Timestamp updatedon);

    SysProject findByUrid(int projectid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_sys_project set code=:code where projectcode=:projectCode",nativeQuery = true)
    void updateCode(String code,String projectCode);
}
