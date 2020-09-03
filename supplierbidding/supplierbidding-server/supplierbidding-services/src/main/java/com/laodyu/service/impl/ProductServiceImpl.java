package com.laodyu.service.impl;

import com.laodyu.commons.resp.RespBean;
import com.laodyu.dao.ProductDao;
import com.laodyu.entity.Product;
import com.laodyu.entity.User;
import com.laodyu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/12
 * @Version 1.0
 **/
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;
    @Override
    public void addProduce(Product product, User currentUser) {
        Product newProduct = addNewProduct(product, currentUser);
        productDao.save(newProduct);
    }

    @Override
    public List<Product> getProductsByProjectid(int projectid) {
        List<Product> productList = productDao.findByProjectid(projectid);
        return productList;
    }

    private Product addNewProduct(Product product, User currentUser) {
        String createdby = currentUser.getCode();
        String updatedby = currentUser.getCode();
        Timestamp createdon = new Timestamp(System.currentTimeMillis());
        Timestamp updatedon = new Timestamp(System.currentTimeMillis());
        product.setCreatedby(createdby);
        product.setCreatedon(createdon);
        product.setUpdatedby(updatedby);
        product.setUpdatedon(updatedon);
        return product;
    }

}
