package com.laodyu.service.impl;

import com.laodyu.dao.MenuDao;
import com.laodyu.entity.*;
import com.laodyu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/29
 * @Version 1.0
 **/
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    UserService userService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RoleMenuService roleMenuService;
    @Autowired
    MenuDao menuDao;
    @Autowired
    RoleService roleService;

    /***
     * 获取当前用户的菜单
     * @param code
     * @return
     */
    @Override
    public List<Menu> getMenusByCurrentUser(String  code) {

        User user = userService.findByCode(code);

        //获得当前用户对应的所有角色的id列表
        List<Integer> roleids = userRoleService.listAllByUserid(user.getUrid()).stream().map(UserRole::getRoleid).collect(Collectors.toList());

        //查询出这些角色对应的菜单项
        List<Integer> menuids = roleMenuService.findAllByRoleids(roleids).stream().map(RoleMenu::getMenuid).collect(Collectors.toList());

        List<Menu> menuList = menuDao.findAllByUridIn(menuids).stream().distinct().collect(Collectors.toList());
        handleMenus(menuList);
        return menuList;
    }

    @Override
    public List<Menu> getMenusByParentId(int parentId) {
        return menuDao.findAllByParentid(parentId);
    }

    /***
     * 判断请求接口是否在权限列表中,是否需要过滤
     * @param requestAPI
     * @return
     */
    @Override
    public boolean needFilter(String requestAPI) {
        List<Menu> menuList = menuDao.findAll();
        for(Menu menu : menuList){
            if(menu.getUrl().indexOf(requestAPI) != -1){
                //请求存在权限列表里
                return true;
            }
        }
        return false;
    }

    /***
     * 获取用户可操作的接口
     * @param code
     * @return
     */
    @Override
    public Set<String> getUrlSet(String code) {
        User user = userService.findByCode(code);

        //获得当前用户对应的所有角色的id列表
        List<Integer> roleids = userRoleService.listAllByUserid(user.getUrid()).stream().map(UserRole::getRoleid).collect(Collectors.toList());

        //查询出这些角色对应的菜单项
        List<Integer> menuids = roleMenuService.findAllByRoleids(roleids).stream().map(RoleMenu::getMenuid).collect(Collectors.toList());

        List<Menu> menuList = menuDao.findAllByUridIn(menuids).stream().distinct().collect(Collectors.toList());
        Set<String> urlSet = new HashSet<>();
        for(Menu menu : menuList){
            urlSet.add(menu.getUrl());
        }
        return urlSet;
    }

    /***
     * 获取所有菜单及其对应的角色信息
     */
    @Override
    public List<Menu> getAllMenusWithRole(){
        List<Menu> menuList = menuDao.findAll();
        for(Menu menu : menuList){
            List<RoleMenu> roleMenuList = roleMenuService.findAllByMenuid(menu.getUrid());
            List<Role> roleList = new ArrayList<>();
            for (RoleMenu roleMenu : roleMenuList){
                int roleid = roleMenu.getRoleid();
                Role role = roleService.findRoleByRoleid(roleid);
                roleList.add(role);
            }
            menu.setRoles(roleList);
        }
        return menuList;
    }

    /***
     * 获取所有菜单的数据
     * @return
     */
    @Override
    public List<Menu> getAllMenu() {
        List<Menu> menuList = menuDao.findAll();
        handleAllMenus(menuList);
        return menuList;
    }

    /***
     * 处理成树形菜单  左边的菜单栏
     * @param  menuList
     */
    private List handleMenus(List<Menu> menuList) {
        List<Menu> sonList = new ArrayList<>();
        List<Menu> parentList = menuList;

        //获取父级菜单
        Iterator<Menu> iterator = parentList.iterator();
        while(iterator.hasNext()){
            Menu menu = iterator.next();
            if(menu.getParentid() != 1){
                sonList.add(menu);
                iterator.remove();
            }
        }
        for(Menu parentMenu :parentList){
            List<Menu> childrenList = new ArrayList<>();
            for(Menu sonMenu : sonList){
                if(sonMenu.getParentid() == parentMenu.getUrid()){
                    childrenList.add(sonMenu);
                }
            }
            parentMenu.setChildren(childrenList);
        }
        return parentList;
    }

    private void handleAllMenus(List<Menu> menuList){
        //获取父级菜单
        for(Menu menu : menuList){
            List<Menu> children = getMenusByParentId(menu.getUrid());
            menu.setChildren(children);
        }
        Iterator<Menu> iterator = menuList.iterator();
        while(iterator.hasNext()){
            Menu menu = iterator.next();
            if(menu.getParentid() != 0){
                iterator.remove();
            }
        }
    }

}
