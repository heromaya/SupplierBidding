package com.laodyu.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/8
 * @Version 1.0
 **/
@Entity
@Table(name = "t_sys_sumup", schema = "supplierbidding", catalog = "")
public class Sumup {
    private int urid;
    private String code;
    private Integer projectid;
    private String description;
    private String hitdescription;
    private String result;
    private Timestamp opdate;
    private Integer userid;
    private String createdby;
    private Timestamp createdon;
    private String updatedby;
    private Timestamp updatedon;
    private int reversion;

    @Id
    @Column(name = "urid", nullable = false)
    public int getUrid() {
        return urid;
    }

    public void setUrid(int urid) {
        this.urid = urid;
    }

    @Basic
    @Column(name = "code", nullable = true, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
    @Column(name = "description", nullable = true, length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "hitdescription", nullable = true, length = 1024)
    public String getHitdescription() {
        return hitdescription;
    }

    public void setHitdescription(String hitdescription) {
        this.hitdescription = hitdescription;
    }

    @Basic
    @Column(name = "result", nullable = true, length = 255)
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Basic
    @Column(name = "opdate", nullable = true)
    public Timestamp getOpdate() {
        return opdate;
    }

    public void setOpdate(Timestamp opdate) {
        this.opdate = opdate;
    }

    @Basic
    @Column(name = "userid", nullable = true)
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sumup sumup = (Sumup) o;
        return urid == sumup.urid &&
                reversion == sumup.reversion &&
                Objects.equals(code, sumup.code) &&
                Objects.equals(projectid, sumup.projectid) &&
                Objects.equals(description, sumup.description) &&
                Objects.equals(hitdescription, sumup.hitdescription) &&
                Objects.equals(result, sumup.result) &&
                Objects.equals(opdate, sumup.opdate) &&
                Objects.equals(userid, sumup.userid) &&
                Objects.equals(createdby, sumup.createdby) &&
                Objects.equals(createdon, sumup.createdon) &&
                Objects.equals(updatedby, sumup.updatedby) &&
                Objects.equals(updatedon, sumup.updatedon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urid, code, projectid, description, hitdescription, result, opdate, userid, createdby, createdon, updatedby, updatedon, reversion);
    }
}
