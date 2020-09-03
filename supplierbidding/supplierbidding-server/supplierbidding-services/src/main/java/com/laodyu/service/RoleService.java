package com.laodyu.service;

import com.laodyu.entity.Role;
import com.laodyu.entity.User;

import java.util.List;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/29
 * @Version 1.0
 **/
public interface RoleService {
    Role findRoleByRoleid(int roleid);
    List<Role> findAll();
    void addRole(Role role, User user);
    void updateRole(Role role, User user);
    void deleteRole(int urid);
    List<Role> findRoleListByUserid(int userid);

}
