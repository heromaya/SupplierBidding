package com.laodyu.service;

import com.laodyu.entity.RoleMenu;
import com.laodyu.entity.User;

import java.util.List;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/29
 * @Version 1.0
 **/
public interface RoleMenuService {


    /***
     * 根据角色id查询所有菜单
     */
    List<RoleMenu> findAllByRoleid(int roleid);

    List<RoleMenu> findAllByRoleids(List<Integer> roleids);

    List<RoleMenu> findAllByMenuid(int menuid);

    List<Integer> findMenuIdByRoleId(int roleid);

    void updateRoleMenu(Integer roleid, Integer[] menuids, User user);
}
