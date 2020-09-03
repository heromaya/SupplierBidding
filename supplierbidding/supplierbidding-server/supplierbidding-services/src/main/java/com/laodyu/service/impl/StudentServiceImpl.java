package com.laodyu.service.impl;

import com.laodyu.commons.resp.RespBean;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.dao.StudentDao;
import com.laodyu.service.StudentService;
import com.laodyu.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/9
 * @Version 1.0
 **/
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDao studentDao;
    @Override
    public RespPage getAll(String name ,String code ,int page , int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Specification<Student> studentSpecification = new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("name").as(String.class),name));
                list.add(criteriaBuilder.equal(root.get("code").as(String.class),code));

                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };

        Page<Student> all = studentDao.findAll(studentSpecification,pageRequest);
        long total = all.getTotalElements();
        List<Student> studentList = all.getContent();
        return new RespPage(total,studentList);
    }
}
