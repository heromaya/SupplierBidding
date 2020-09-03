package com.laodyu.web.controller.sys;

import com.laodyu.entity.Menu;
import com.laodyu.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/6
 * @Version 1.0
 **/
@RestController
public class MenuController {
    @Autowired
    MenuService menuService;
    @GetMapping("/menu")
    public List<Menu> getMenusByUserId(){
        Subject subject = SecurityUtils.getSubject();
        String code = subject.getPrincipal().toString();
        return  menuService.getMenusByCurrentUser(code);
    }
}
