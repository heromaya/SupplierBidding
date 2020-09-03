package com.laodyu.dao;

import com.laodyu.entity.Expertgroup;
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
 * @Date 2020/6/3
 * @Version 1.0
 **/
public interface ExpertGroupDao extends JpaRepository<Expertgroup,Integer>, JpaSpecificationExecutor<Expertgroup> {
    List<Expertgroup> findByExpertid(int expertid);

    List<Expertgroup> findByExpertidAndStatus(int expertid , String status);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_sys_expertgroup set status=:status,updatedby=:updatedby,updatedon=:updatedon where projectid=:projectid and expertid=:expertid ",nativeQuery = true)
    void updateStatus(int projectid, int expertid, String status, String updatedby, Timestamp updatedon);
}
