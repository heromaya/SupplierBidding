package com.laodyu.service;


import com.laodyu.commons.resp.RespBean;
import com.laodyu.entity.User;
import com.laodyu.entity.UserRole;

import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/29
 * @Version 1.0
 **/
public interface UserRoleService {
    /***
     * 根据用户id 获取所有对应的角色id
     * @param userid
     * @return
     */
    List<UserRole> listAllByUserid(int userid);

    /***
     * 为用户添加角色
     */
    void AddUserRole(int userid, int roleid, User currentUser);

    /***
     * 根据角色id 查询用户id
     * @param roleid
     */

    List<Integer> getUserByRole(int roleid);

    UserRole getUserRoleByUseridAndRoleid(int userid, int roleid);

    //更新用户角色
    RespBean upateUserRole(Integer userid , Integer [] roleids,User currentUser);
}
