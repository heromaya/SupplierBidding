package com.laodyu.service;

import com.laodyu.commons.resp.RespBean;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.entity.SysProject;
import com.laodyu.entity.User;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/12
 * @Version 1.0
 **/
public interface ProjectService {

    RespBean AddTenderingProject(SysProject project , User currentUser);

    SysProject getByProjectName(String projectname);

    SysProject getByProjectcode(String projectcode);

    RespPage getAllProjectByCode(Integer page, Integer size,String code);

    void updateProject(String projectCode, String approveState, String cancelState, String processInstanceId ,User currentUser);

    RespPage getAllProject(Integer page, Integer size);

    RespPage findProject(Integer page, Integer size, String approvestate, String cancelstate);

    RespPage getAllApprovedProject(Integer page, Integer size, String code);

    SysProject getProjectByUrid(int projectid);

    void updateCode(String code,String projectCode);

    SysProject fingByUrid(int urid);
}
