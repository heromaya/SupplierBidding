package com.laodyu.dao;

import com.laodyu.entity.Userfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/8
 * @Version 1.0
 **/
@Repository
public interface UserFileDao extends JpaRepository<Userfile,Integer> , JpaSpecificationExecutor<Userfile> {
    Userfile findByUrid(int urid);

    Userfile findByProjectidAndType(int projectid,String type);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_sys_userfile set url=:path,uploadname=:uploadname where projectid=:projectId and type=:type" ,nativeQuery = true)
    void updateUrlAndUploadName(String path, String projectId, String type,String uploadname);

    List<Userfile> findByType(String type);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update  t_sys_userfile set download=download+1 where urid = urid ",nativeQuery = true)
    void upsetDownloadCount(int urid);

    Userfile findByProjectidAndTypeAndCreatedby(int projectid,String type , String code);
}
