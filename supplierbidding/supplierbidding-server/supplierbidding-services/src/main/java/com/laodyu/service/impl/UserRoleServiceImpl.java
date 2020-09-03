package com.laodyu.service.impl;

import com.laodyu.commons.constant.ENRole;
import com.laodyu.commons.resp.RespBean;
import com.laodyu.dao.UserRoleDao;
import com.laodyu.entity.Expert;
import com.laodyu.entity.User;
import com.laodyu.entity.UserRole;
import com.laodyu.service.ExpertService;
import com.laodyu.service.UserRoleService;
import com.laodyu.service.UserService;
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
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleDao userRoleDao;
    @Autowired
    ExpertService expertService;
    @Autowired
    UserService userService;

    @Override
    public List<UserRole> listAllByUserid(int userid) {
        return userRoleDao.findAllByUserid(userid);
    }

    @Override
    public void AddUserRole(int userid, int roleid, User currentUser) {
        UserRole userRole = getNewUserRole(userid, roleid, currentUser);
        userRoleDao.save(userRole);
    }

    private UserRole getNewUserRole(int userid, int roleid, User currentUser) {
        UserRole userRole = new UserRole();
        userRole.setUserid(userid);
        userRole.setRoleid(roleid);
        String createdby = currentUser.getCode();
        String updatedby = currentUser.getCode();
        Timestamp createdon = new Timestamp(System.currentTimeMillis());
        Timestamp updatedon = new Timestamp(System.currentTimeMillis());
        userRole.setCreatedby(createdby);
        userRole.setCreatedon(createdon);
        userRole.setUpdatedby(updatedby);
        userRole.setUpdatedon(updatedon);
        return userRole;
    }

    @Override
    public List<Integer> getUserByRole(int roleid) {
        List<UserRole> userRoleList = userRoleDao.findAllByRoleid(roleid);
        List<Integer> useridList = new ArrayList<>();
        for (UserRole userRole : userRoleList) {
            useridList.add(userRole.getUserid());
        }
        return useridList;
    }

    @Override
    public UserRole getUserRoleByUseridAndRoleid(int userid, int roleid) {
        return userRoleDao.findByUseridAndRoleid(userid, roleid);
    }

    /***
     * 更新用户角色
     * @param userid
     * @param roleids
     * @return
     */
    @Override
    public RespBean upateUserRole(Integer userid, Integer[] roleids, User currentUser) {
        //1.删除用户的角色
        userRoleDao.deleteRole(userid);
        //为用户添加角色
        for (int roleid : roleids) {
            UserRole userRole = addUserRole(userid, roleid, currentUser);
            userRoleDao.save(userRole);
            //如果角色是专家
            if (String.valueOf(roleid).equals(ENRole.ROLE_EXPERT.getValue())) {
                User user = userService.findByUrid(userid);
                //查看该用户是否是专家
                if (expertService.findByCode(user.getCode()) == null) {
                    //新增专家信息
                    Expert expert = new Expert();
                    expert.setCode(user.getCode());
                    expert.setCreatedby(currentUser.getCode());
                    expert.setUpdatedby(currentUser.getCode());
                    expert.setUpdatedon(new Timestamp(System.currentTimeMillis()));
                    expert.setCreatedon(new Timestamp(System.currentTimeMillis()));
                    expertService.addExpert(expert);
                }
            }
        }
        return RespBean.ok("修改成功！");
    }

    public UserRole addUserRole(int userid, int roleid, User currentUser) {
        String createdby = currentUser.getCode();
        String updatedby = currentUser.getCode();
        Timestamp createdon = new Timestamp(System.currentTimeMillis());
        Timestamp updatedon = new Timestamp(System.currentTimeMillis());
        UserRole userRole = new UserRole();
        userRole.setUserid(userid);
        userRole.setRoleid(roleid);
        userRole.setCreatedby(createdby);
        userRole.setCreatedon(createdon);
        userRole.setUpdatedby(updatedby);
        userRole.setUpdatedon(updatedon);
        return userRole;
    }

}
