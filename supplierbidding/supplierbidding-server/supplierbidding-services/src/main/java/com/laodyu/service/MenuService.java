package com.laodyu.service;

import com.laodyu.entity.Menu;

import java.util.List;
import java.util.Set;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/29
 * @Version 1.0
 **/
public interface MenuService {
    List<Menu> getMenusByCurrentUser(String code);

    List<Menu> getMenusByParentId(int parentId);

    boolean needFilter(String requestAPI);

    Set<String> getUrlSet(String code);

    List<Menu> getAllMenusWithRole();

    List<Menu> getAllMenu();
}
