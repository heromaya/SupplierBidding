package com.laodyu.web.controller.sys;

import com.laodyu.commons.resp.RespPage;
import com.laodyu.dao.StudentDao;
import com.laodyu.entity.Student;
import com.laodyu.service.StudentService;
import com.laodyu.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/9
 * @Version 1.0
 **/
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @GetMapping("/getall")
    public RespPage findAllStudent(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "10" )Integer size,String name,String code){
        if(page!=null && size != null){
            page = (page-1) * size;
        }
        RespPage respPage = studentService.getAll(name, code, page, size);
        return respPage;
    }
}
