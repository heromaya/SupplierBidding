package com.laodyu.service.impl;

import com.laodyu.dao.RoleMenuDao;
import com.laodyu.entity.RoleMenu;
import com.laodyu.entity.User;
import com.laodyu.service.RoleMenuService;
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
public class RoleMenuServiceImpl implements RoleMenuService {
    @Autowired
    RoleMenuDao roleMenuDao;

    @Override
    public List<RoleMenu> findAllByRoleid(int roleid) {
        return roleMenuDao.findAllByRoleid(roleid);
    }

    @Override
    public List<RoleMenu> findAllByRoleids(List<Integer> roleids) {
        return roleMenuDao.findAllByRoleidIn(roleids);
    }

    @Override
    public List<RoleMenu> findAllByMenuid(int menuid) {
        return roleMenuDao.findAllByMenuid(menuid);
    }

    @Override
    public List<Integer> findMenuIdByRoleId(int roleid) {
        List<RoleMenu> roleMenuList = this.findAllByRoleid(roleid);
        List<Integer> menuIdList = new ArrayList<>();
        for (RoleMenu roleMenu : roleMenuList) {
            menuIdList.add(roleMenu.getMenuid());
        }
        return menuIdList;
    }

    /***
     * 更新t_sys_role_menu表
     * 1.先将roleid 的 记录中的 menuid 设为 null
     * 2.将 menuids 的值重新赋值上去
     * @param roleid
     * @param menuids
     */
    @Override
    public void updateRoleMenu(Integer roleid, Integer[] menuids, User user) {
        //先设置为 null
        roleMenuDao.deleteByRoleid(roleid);
        if (menuids != null && menuids.length > 0) {
            //设置新的菜单
            for (int menuid : menuids) {
                RoleMenu roleMenu = getNewRoleMenu(roleid, menuid, user);
                roleMenuDao.save(roleMenu);
            }
        }
    }

    /***
     * 创建新的RoleMenu
     * @param roleid
     * @param menuid
     * @param user
     * @return
     */
    private RoleMenu getNewRoleMenu(int roleid, int menuid, User user) {
        RoleMenu roleMenu = new RoleMenu();
        String createdby = user.getCode();
        String updatedby = user.getCode();
        Timestamp createdon = new Timestamp(System.currentTimeMillis());
        Timestamp updatedon = new Timestamp(System.currentTimeMillis());
        roleMenu.setRoleid(roleid);
        roleMenu.setMenuid(menuid);
        roleMenu.setCreatedby(createdby);
        roleMenu.setCreatedon(createdon);
        roleMenu.setUpdatedby(updatedby);
        roleMenu.setUpdatedon(updatedon);
        roleMenu.setReversion(1);
        return roleMenu;
    }


}
