package com.laodyu.dao;



import com.laodyu.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/29
 * @Version 1.0
 **/
@Repository
public interface UserRoleDao extends JpaRepository<UserRole,Integer> {
    /***
     * 根据当前的用户urid获取该用户的所有角色id
     */
    List<UserRole> findAllByUserid(int userid);

    /***
     * 根据角色id 查询用户
     *
     */
    List<UserRole> findAllByRoleid(int roleid);

    UserRole findByUseridAndRoleid(int userid,int roleid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from t_sys_user_role where userid=:userid",nativeQuery = true)
     void deleteRole(Integer userid);
}
