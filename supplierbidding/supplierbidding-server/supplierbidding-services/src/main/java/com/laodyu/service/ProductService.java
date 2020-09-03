package com.laodyu.service;

import com.laodyu.entity.Product;
import com.laodyu.entity.User;

import java.util.List;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/12
 * @Version 1.0
 **/
public interface ProductService {
     void addProduce(Product product, User currentUser);

     List<Product> getProductsByProjectid(int projectid);
}
