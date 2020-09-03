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
@Table(name = "t_sys_expertgroup", schema = "supplierbidding", catalog = "")
public class Expertgroup {
    private int urid;
    private String createdby;
    private Timestamp createdon;
    private String updatedby;
    private Timestamp updatedon;
    private int reversion;
    private Integer expertid;
    private String score;
    private Integer projectid;
    private Integer grouplistid;
    private String status;
    private Grouplist grouplist;
    private SysProject project;

    @Transient
    public SysProject getProject() {
        return project;
    }

    public void setProject(SysProject project) {
        this.project = project;
    }

    @Transient
    public Grouplist getGrouplist() {
        return grouplist;
    }

    public void setGrouplist(Grouplist grouplist) {
        this.grouplist = grouplist;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name = "urid", nullable = false)
    public int getUrid() {
        return urid;
    }

    public void setUrid(int urid) {
        this.urid = urid;
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
    @Column(name = "expertid", nullable = true)
    public Integer getExpertid() {
        return expertid;
    }

    public void setExpertid(Integer expertid) {
        this.expertid = expertid;
    }

    @Basic
    @Column(name = "score", nullable = true, length = 255)
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
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
    @Column(name = "grouplistid", nullable = true)
    public Integer getGrouplistid() {
        return grouplistid;
    }

    public void setGrouplistid(Integer grouplistid) {
        this.grouplistid = grouplistid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expertgroup that = (Expertgroup) o;
        return urid == that.urid &&
                reversion == that.reversion &&
                Objects.equals(createdby, that.createdby) &&
                Objects.equals(createdon, that.createdon) &&
                Objects.equals(updatedby, that.updatedby) &&
                Objects.equals(updatedon, that.updatedon) &&
                Objects.equals(expertid, that.expertid) &&
                Objects.equals(score, that.score) &&
                Objects.equals(projectid, that.projectid) &&
                Objects.equals(grouplistid, that.grouplistid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urid, createdby, createdon, updatedby, updatedon, reversion, expertid, score, projectid, grouplistid);
    }
}
