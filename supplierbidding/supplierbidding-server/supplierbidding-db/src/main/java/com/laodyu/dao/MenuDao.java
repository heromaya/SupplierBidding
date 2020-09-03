package com.laodyu.dao;



import com.laodyu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/29
 * @Version 1.0
 **/
@Repository
public interface MenuDao extends JpaRepository<Menu,Integer> {
    List<Menu> findAllByUridIn(List<Integer> urid);

    Menu findByUrid(int urid);

    List<Menu> findAllByParentid(int parentid);
}
