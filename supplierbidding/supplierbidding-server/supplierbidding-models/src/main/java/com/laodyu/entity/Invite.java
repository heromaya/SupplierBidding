package com.laodyu.entity;

import javax.persistence.*;
import java.sql.Date;
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
@Table(name = "t_sys_invite", schema = "supplierbidding", catalog = "")
public class Invite {
    private int urid;
    private int projectid;
    private Date opentime;
    private String openposition;
    private String hit;
    private String hitscore;
    private Date hittime;
    private String createdby;
    private Timestamp createdon;
    private String updatedby;
    private Timestamp updatedon;
    private int reversion;
    private Integer supplierid;

    @Id
    @Column(name = "urid", nullable = false)
    public int getUrid() {
        return urid;
    }

    public void setUrid(int urid) {
        this.urid = urid;
    }

    @Basic
    @Column(name = "projectid", nullable = false)
    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    @Basic
    @Column(name = "opentime", nullable = true)
    public Date getOpentime() {
        return opentime;
    }

    public void setOpentime(Date opentime) {
        this.opentime = opentime;
    }

    @Basic
    @Column(name = "openposition", nullable = true, length = 255)
    public String getOpenposition() {
        return openposition;
    }

    public void setOpenposition(String openposition) {
        this.openposition = openposition;
    }

    @Basic
    @Column(name = "hit", nullable = true, length = 255)
    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    @Basic
    @Column(name = "hitscore", nullable = true, length = 255)
    public String getHitscore() {
        return hitscore;
    }

    public void setHitscore(String hitscore) {
        this.hitscore = hitscore;
    }

    @Basic
    @Column(name = "hittime", nullable = true)
    public Date getHittime() {
        return hittime;
    }

    public void setHittime(Date hittime) {
        this.hittime = hittime;
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
    @Column(name = "supplierid", nullable = true)
    public Integer getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(Integer supplierid) {
        this.supplierid = supplierid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invite invite = (Invite) o;
        return urid == invite.urid &&
                projectid == invite.projectid &&
                reversion == invite.reversion &&
                Objects.equals(opentime, invite.opentime) &&
                Objects.equals(openposition, invite.openposition) &&
                Objects.equals(hit, invite.hit) &&
                Objects.equals(hitscore, invite.hitscore) &&
                Objects.equals(hittime, invite.hittime) &&
                Objects.equals(createdby, invite.createdby) &&
                Objects.equals(createdon, invite.createdon) &&
                Objects.equals(updatedby, invite.updatedby) &&
                Objects.equals(updatedon, invite.updatedon) &&
                Objects.equals(supplierid, invite.supplierid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urid, projectid, opentime, openposition, hit, hitscore, hittime, createdby, createdon, updatedby, updatedon, reversion, supplierid);
    }
}
