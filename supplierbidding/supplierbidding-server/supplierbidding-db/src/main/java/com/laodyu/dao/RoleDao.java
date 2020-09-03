package com.laodyu.dao;


import com.laodyu.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/29
 * @Version 1.0
 **/
public interface RoleDao extends JpaRepository<Role,Integer> {
    Role findByUrid(int roleid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_sys_role set code=:code,name=:name,updatedby=:updatedby,updatedon=:updatedon,reversion=:reversion where urid=:urid" ,nativeQuery = true)
    void updateRole(String code, String name, String updatedby, Timestamp updatedon, int reversion,int urid);
}
