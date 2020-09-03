package com.laodyu.service;

import com.laodyu.commons.resp.RespPage;
import com.laodyu.entity.Supplier;
import com.laodyu.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/28
 * @Version 1.0
 **/
public interface SupplierService {

    void AddSupplier(Map<String,String> params, String password , String salt,String processInstanceId);

    Supplier findByUserid(String userid);

    Supplier findByEmail(String email);

    Supplier findByTelephone(String telephone);

    RespPage findAllSupplier(String approveState , String cancelState ,int page , int size);

    RespPage getAllSupplier(Integer page, Integer size);

    void updateApprovestate(String userid, String approvestate, User user);

    Supplier findByUrid(int supplierid);
}
