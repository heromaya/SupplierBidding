package com.laodyu.web.controller.sys;

import com.laodyu.entity.Menu;
import com.laodyu.commons.resp.RespBean;
import com.laodyu.entity.User;
import com.laodyu.service.MenuService;
import com.laodyu.service.RoleMenuService;
import com.laodyu.web.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/2
 * @Version 1.0
 **/
@RestController
@RequestMapping("system/permissinfo")
public class PermissController {
    @Autowired
    MenuService menuService;

    @Autowired
    RoleMenuService roleMenuService;

    @GetMapping("/")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenu();
    }

    /***
     * 根据角色id获取 menuid
     * @param roleid
     * @return
     */
    @GetMapping("/{roleid}")
    public List<Integer> getMenuidByRoleid(@PathVariable Integer roleid) {
        return roleMenuService.findMenuIdByRoleId(roleid);
    }

    /***
     * 更新角色的菜单
     * 1.删除当前所有角色的菜单
     * 2.添加上新的菜单项
     * @param roleid
     * @param menuids
     * @return
     */
    @PutMapping("/")
    public RespBean updateRoleMenu(Integer roleid , Integer[] menuids ){
        User user = SessionUtil.getSession();
        roleMenuService.updateRoleMenu(roleid,menuids,user);
        return RespBean.ok("操作成功!");
    }
}
