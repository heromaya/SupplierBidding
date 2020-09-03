package com.laodyu.dao;



import com.laodyu.entity.User;
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
 * @Date 2020/4/28
 * @Version 1.0
 **/
@Repository
public interface UserDao extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {
    User findByUrid(int urid);

    User findByCode(String code);
    User findByCodeAndStatus(String code , String ststus);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_sys_user set status=:status,updatedby=:updatedby,updatedon=:updatedon,reversion=reversion+1 where code=:code",nativeQuery = true)
    void updateStatus(String code, String status, String updatedby, Timestamp updatedon);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_sys_user set salt=:salt,password=:encodedNewPassword,changedpassword='1',updatedby=:updatedby,updatedon=:updatedon,reversion=reversion+1 where code=:code",nativeQuery = true)
    void updatePassword(String code, String salt, String encodedNewPassword,String updatedby, Timestamp updatedon);

    List<User> findByStatus(String status);
}
