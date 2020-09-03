package com.laodyu.web.controller.dictionary;


import com.laodyu.entity.ExpertType;
import com.laodyu.entity.ProjectType;
import com.laodyu.service.ExpertTypeService;
import com.laodyu.service.ProjectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/14
 * @Version 1.0
 **/
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
    @Autowired
    ExpertTypeService expertTypeService;
    @Autowired
    ProjectTypeService projectTypeService;

    @GetMapping("/getAllExpertType")
    public List<ExpertType> getAllExpertType(){
        return expertTypeService.getAllType();
    }

    @GetMapping("/getAllProjectType")
    public List<ProjectType> getAllPRojectType(){
        return projectTypeService.getAllProjectType();
    }


}
