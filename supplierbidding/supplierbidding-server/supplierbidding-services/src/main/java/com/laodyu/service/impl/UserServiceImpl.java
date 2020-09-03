package com.laodyu.service.impl;

import com.laodyu.commons.constant.ENUserState;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.dao.UserDao;
import com.laodyu.commons.resp.RespBean;
import com.laodyu.entity.Role;
import com.laodyu.entity.User;
import com.laodyu.service.RoleService;
import com.laodyu.service.UserRoleService;
import com.laodyu.service.UserService;
import org.apache.catalina.manager.util.SessionUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/28
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleService userRoleService;

    @Override
    public User findByCode(String code) {
        return userDao.findByCode(code);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void AddUser(String code, String password, String salt, String status, User currentUser) {
        User user = new User();
        user.setSalt(salt);
        user.setPassword(password);
        user.setCode(code);
        user.setCreatedby(currentUser.getCode());
        user.setUpdatedby(currentUser.getCode());
        user.setStatus(status);
        user.setChangedpassword("0");
        user.setCreatedon(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedon(new Timestamp(System.currentTimeMillis()));
        userDao.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        userDao.save(user);
    }

    /***
     * 获取所有用户
     * @return
     */
    @Override
    public RespPage findAll(int page , int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdon").descending());
        Page<User> all = userDao.findAll(pageRequest);
        long total = all.getTotalElements();
        List<User> userList = all.getContent();
        for (User user : userList) {
            //将用户的权限给查出来
            List<Role> roleList = roleService.findRoleListByUserid(user.getUrid());
            user.setRoleList(roleList);
            user.setPassword(null);
            user.setSalt(null);
        }
       return new RespPage(total,userList);
    }

    @Override
    public List<User> getUsersByRoleid(int roleid) {
        List<Integer> useridList = userRoleService.getUserByRole(roleid);
        List<User> userList = userDao.findAllById(useridList);
        return userList;
    }

    @Override
    public RespBean changeUserStatus(String[] codes, String status,User currentUser) {
        /***
         * 验证是否已经是该状态：
         */
        int successCounts = codes.length;
        for (String code : codes) {
            User user = this.findByCode(code);
            String userStatus = user.getStatus();
            if(userStatus.equals(status)){
                successCounts --;
            }else{
                String updatedby = currentUser.getCode();
                Timestamp updatedon = new Timestamp(System.currentTimeMillis());
                userDao.updateStatus(code,status,updatedby,updatedon);
            }
        }
        String message = status.equals("2")?"生效 ":"失效 ";
        StringBuilder sb = new StringBuilder("成功");
        sb.append(message).append(successCounts).append(" 条数据");
        if(successCounts != codes.length){
            sb.append(",有 ").append(codes.length-successCounts).append(" 条数据已经").append(message).append(",不可重复").append(message);
        }
        return RespBean.ok(sb.toString());
    }

    /***
     * 修改密码
     * @param salt
     * @param encodedNewPassword
     */
    @Override
    public void changePassword(String salt, String encodedNewPassword,User user) {
        String updatedby = user.getCode();
        Timestamp updatedon = new Timestamp(System.currentTimeMillis());
        userDao.updatePassword(user.getCode(),salt,encodedNewPassword,updatedby,updatedon);
    }

    @Override
    public User findByUrid(int urid) {
        User user = userDao.findByUrid(urid);
        return user;
    }

    @Override
    public RespPage findByRoleId(Integer roleid) {
        List<Integer> userid = userRoleService.getUserByRole(roleid);
        userDao.findAllById(userid);
        return null;
    }

    @Override
    public List<User> findByStatus(int urid) {
        List<User> userList = userDao.findByStatus(ENUserState.NORMAL.getValue());
        List<User> newUserList = new ArrayList<>();
        for(User user : userList){
            if(user.getUrid() != urid){
                user.setPassword("");
                user.setSalt("");
                newUserList.add(user);
            }
        }
        return newUserList;
    }


}
