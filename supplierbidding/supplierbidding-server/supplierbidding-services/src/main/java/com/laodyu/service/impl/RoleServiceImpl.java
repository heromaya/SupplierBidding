package com.laodyu.service.impl;

import com.laodyu.commons.exception.BizException;
import com.laodyu.dao.RoleDao;
import com.laodyu.entity.Role;
import com.laodyu.entity.User;
import com.laodyu.entity.UserRole;
import com.laodyu.service.RoleService;
import com.laodyu.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/29
 * @Version 1.0
 **/
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;
    @Autowired
    UserRoleService userRoleService;
    @Override
    public Role findRoleByRoleid(int roleid) {
        return roleDao.findByUrid(roleid);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }


    @Override
    public void addRole(Role role, User user) {
        role = changeRoleInfo(role,user,true);
        roleDao.save(role);
    }

    @Override
    public void updateRole(Role role, User user) {
        role = changeRoleInfo(role,user,false);
        roleDao.updateRole(role.getCode(),role.getName(),role.getUpdatedby(),role.getUpdatedon(),role.getReversion(), role.getUrid());
    }

    @Override
    public void deleteRole(int urid) {
        //获取角色
        Role role = roleDao.findByUrid(urid);
        if(role.getCandelete().equals("0")){
            throw new BizException(role.getName()+"为系统内部角色，不允许删除！");
        }
        roleDao.deleteById(urid);
    }

    /***
     * 根据用户的id查找对应的角色
     * @param userid
     * @return
     */
    @Override
    public List<Role> findRoleListByUserid(int userid) {
        List<UserRole> userRoleList =  userRoleService.listAllByUserid(userid);
        List<Role> roleList = new ArrayList<>();
        for(UserRole userRole:userRoleList){
            Role role = roleDao.findByUrid(userRole.getRoleid());
            roleList.add(role);
        }
        return roleList;
    }



    public Role changeRoleInfo(Role role, User user,boolean isInsert){
        String username = user.getCode();
        if(isInsert){

         role.setCreatedon(new Timestamp(System.currentTimeMillis()));
         role.setCreatedby(username);
        }
        System.out.println(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedby(username);
        role.setUpdatedon(new Timestamp(System.currentTimeMillis()));
        role.setReversion(role.getReversion() + 1);
        role.setCandelete("1");
        return role;
    }
}
