package com.laodyu.service;

import com.laodyu.commons.resp.RespPage;
import com.laodyu.entity.SysProject;
import com.laodyu.entity.User;
import com.laodyu.entity.Userfile;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/8
 * @Version 1.0
 **/
public interface UserFileService {
    void addFile(Userfile userfile);

    RespPage findAllByUserid(Integer page, Integer size, int urid);

    Userfile findByUrid(int urid);

    void deleteFile(int urid);

    void saveFile(Map<String, String> params, User session);


    void updateUrl(String path ,String projectId, String type,String uploadname);

    Userfile findByProjectIdAndType(int projectId, String type);


    List<SysProject> getAllProjectTenderBook();

    void upsetDownloadCount(int urid);

    Userfile checkBidBookIsExist(int projectId, String code, String type);

    RespPage findByProjectId(int projectid, int page, int size);
}
