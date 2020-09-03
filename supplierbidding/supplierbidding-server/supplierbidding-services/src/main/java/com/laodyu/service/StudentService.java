package com.laodyu.service;

import com.laodyu.commons.resp.RespPage;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/9
 * @Version 1.0
 **/
public interface StudentService {
    RespPage getAll(String name , String code , int page , int size);
}
