package com.laodyu.service;

import com.laodyu.commons.resp.RespBean;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.entity.User;

import java.util.List;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/28
 * @Version 1.0
 **/

public interface UserService {
    User findByCode(String code);
    void AddUser(String code , String password , String salt,String status,User currentUser);
    void updateUser(User user);
    RespPage findAll(int page , int size);

    List<User> getUsersByRoleid(int roleid);

    RespBean changeUserStatus(String[] codes, String status,User user);

    void changePassword(String salt, String encodedNewPassword,User user);

    User findByUrid(int urid);


    RespPage findByRoleId(Integer roleid);

    List<User> findByStatus(int urid);
}
