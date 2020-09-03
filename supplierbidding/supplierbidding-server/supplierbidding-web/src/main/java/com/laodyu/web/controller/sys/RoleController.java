package com.laodyu.web.controller.sys;

import com.laodyu.commons.resp.RespBean;
import com.laodyu.entity.Role;
import com.laodyu.entity.User;
import com.laodyu.service.RoleService;
import com.laodyu.web.util.SessionUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/1
 * @Version 1.0
 **/
@RestController
@RequestMapping("/system/roleinfo")
public class RoleController {
    @Autowired
    RoleService roleService;

    @GetMapping("/")
    public List<Role> getAllRole() {
        return roleService.findAll();
    }

    @PostMapping("/")
    public RespBean addRole(@RequestBody Role role) {
        User user = SessionUtil.getSession();
        String message = "";
        //数据校验
        String code = role.getCode();
        String name = role.getName();
        if (StringUtils.isEmpty(code)) {
            return RespBean.error("添加角色失败！角色代码不能为空！");
        }
        if (StringUtils.isEmpty(name)) {
            return RespBean.error("添加角色失败！角色名称不能为空！");
        }
        if(!role.getCode().startsWith("ROLE_")){
            role.setCode("ROLE_"+role.getCode());
        }
        roleService.addRole(role, user);

        return RespBean.ok("添加角色成功！");

    }

    @PutMapping("/")
    public RespBean updateRole(@RequestBody Role role) {
        User user = SessionUtil.getSession();
        String message = "";
        //数据校验
        String code = role.getCode();
        String name = role.getName();
        if (StringUtils.isEmpty(code)) {
            return RespBean.error("更新角色信息失败！角色代码不能为空！");
        }
        if (StringUtils.isEmpty(name)) {
            return RespBean.error("更新角色信息失败！角色名称不能为空！");
        }
        if(!role.getCode().startsWith("ROLE_")){
            role.setCode("ROLE_"+role.getCode());
        }
        roleService.updateRole(role, user);
        return RespBean.ok("更新角色信息成功！");

    }

    @DeleteMapping("/{urid}")
    public RespBean deleteRole(@PathVariable int urid) {
        roleService.deleteRole(urid);
        return RespBean.ok("删除成功！");
    }

    /***
     * 批量删除
     * @param urids
     * @return
     */
    @DeleteMapping("/")
    public RespBean deleteRole(Integer[] urids) {
        for(int urid : urids){
            roleService.deleteRole(urid);
        }
        return RespBean.ok("批量删除成功！");
    }
}
