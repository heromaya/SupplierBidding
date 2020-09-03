package com.laodyu.service.impl;

import com.laodyu.dao.ExpertTypeDao;
import com.laodyu.entity.Expert;
import com.laodyu.entity.ExpertType;
import com.laodyu.service.ExpertTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/14
 * @Version 1.0
 **/
@Service
public class ExpertTypeImpl implements ExpertTypeService {
    @Autowired
    ExpertTypeDao expertTypeDao;
    @Override
    public List<ExpertType> getAllType() {
        return expertTypeDao.findAll();
    }
}
