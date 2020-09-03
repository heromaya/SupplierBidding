package com.laodyu.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/6/3
 * @Version 1.0
 **/
@Entity
@Table(name = "t_sys_grouplist", schema = "supplierbidding", catalog = "")
public class Grouplist {
    private int urid;
    private Integer projectid;
    private String name;
    private String createdby;
    private Timestamp createdon;
    private String updatedby;
    private Timestamp updatedon;
    private int reversion;
    private Integer count;
    private String code;
    private SysProject project;

    @Transient
    public SysProject getProject() {
        return project;
    }

    public void setProject(SysProject project) {
        this.project = project;
    }

    @Id
    @Column(name = "urid", nullable = false)
    public int getUrid() {
        return urid;
    }

    public void setUrid(int urid) {
        this.urid = urid;
    }


    @Basic
    @Column(name = "projectid", nullable = true)
    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "createdby", nullable = false, length = 255)
    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    @Basic
    @Column(name = "createdon", nullable = false)
    public Timestamp getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Timestamp createdon) {
        this.createdon = createdon;
    }

    @Basic
    @Column(name = "updatedby", nullable = false, length = 255)
    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    @Basic
    @Column(name = "updatedon", nullable = false)
    public Timestamp getUpdatedon() {
        return updatedon;
    }

    public void setUpdatedon(Timestamp updatedon) {
        this.updatedon = updatedon;
    }

    @Basic
    @Column(name = "reversion", nullable = false)
    public int getReversion() {
        return reversion;
    }

    public void setReversion(int reversion) {
        this.reversion = reversion;
    }

    @Basic
    @Column(name = "count", nullable = true)
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Basic
    @Column(name = "code", nullable = true, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Grouplist grouplist = (Grouplist) o;
        return urid == grouplist.urid &&
                reversion == grouplist.reversion &&
                Objects.equals(projectid, grouplist.projectid) &&
                Objects.equals(name, grouplist.name) &&
                Objects.equals(createdby, grouplist.createdby) &&
                Objects.equals(createdon, grouplist.createdon) &&
                Objects.equals(updatedby, grouplist.updatedby) &&
                Objects.equals(updatedon, grouplist.updatedon) &&
                Objects.equals(count, grouplist.count) &&
                Objects.equals(code, grouplist.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urid,  projectid, name, createdby, createdon, updatedby, updatedon, reversion, count, code);
    }
}
