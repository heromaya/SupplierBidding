package com.laodyu.service;

import com.laodyu.commons.resp.RespPage;
import com.laodyu.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/14
 * @Version 1.0
 **/
public interface ExpertService {
    RespPage getAllExpert(Integer page, Integer size);

    Expert findByCode(String code);

    void addExpert(Expert expert);

    void updateExpert(Expert expert);

    List<Expert> buildExperGroup(Map<String, String> params);

    void saveExpertGroup(String groupName ,int projectId, List<Expert> expertList, User currentUser);

    RespPage getExpertGroupList(int page , int size ,String code);

    Grouplist findGroupList(int projectId);

    RespPage getAllExpertProject(int page, int size, int  expertid);

    List<SysProject> getProject(int urid);

    void saveCommontBook(Commontbook commontbook, User currentUser);

    void updateExpertGroupStstus(int projectid, int expertid,String updatedby);

    Commontbook checkCommonBook(int projectid, Integer supplierid, String createdby);
}
