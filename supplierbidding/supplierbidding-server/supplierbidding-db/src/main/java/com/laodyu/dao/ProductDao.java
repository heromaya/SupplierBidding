package com.laodyu.dao;

import com.laodyu.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/12
 * @Version 1.0
 **/
public interface ProductDao extends JpaRepository<Product,Integer> {
    List<Product> findByProjectid(int projectid);
}
