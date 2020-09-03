package com.laodyu.dao;


import com.laodyu.entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
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
 * @Date 2020/4/29
 * @Version 1.0
 **/
@Repository
public interface RoleMenuDao extends JpaRepository<RoleMenu,Integer> {
    List<RoleMenu> findAllByRoleid(int roleid);
    List<RoleMenu> findAllByRoleidIn(List<Integer> roleid);
    List<RoleMenu> findAllByMenuid(int menuid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from t_sys_role_menu where roleid=:roleid" , nativeQuery = true)
    void deleteByRoleid(int roleid);

}
