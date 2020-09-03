package com.laodyu.dao;

import com.laodyu.entity.Commontbook;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/6/3
 * @Version 1.0
 **/
public interface CommontBookDao extends JpaRepository<Commontbook,Integer> {
    Commontbook findByProjectidAndSupplieridAndCreatedby(int projectid , int supplierid , String createdby);
}
