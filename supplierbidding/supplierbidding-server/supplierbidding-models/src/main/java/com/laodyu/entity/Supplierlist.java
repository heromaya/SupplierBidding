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
@Table(name = "t_sys_supplierlist", schema = "supplierbidding", catalog = "")
public class Supplierlist {
    private int urid;
    private String code;
    private int projectid;
    private int supplierid;
    private String createdby;
    private Timestamp createdon;
    private String updatedby;
    private Timestamp updatedon;
    private int reversion;
    private String approvestate;
    private SysProject project;
    private Supplier supplier;
    private String tenderid;

    @Column(name = "tenderid")
    public String getTenderid() {
        return tenderid;
    }

    public void setTenderid(String tenderid) {
        this.tenderid = tenderid;
    }

    @Transient
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Transient
    public SysProject getProject() {
        return project;
    }

    public void setProject(SysProject project) {
        this.project = project;
    }

    @Basic
    @Column(name = "approvestate")
    public String getApprovestate() {
        return approvestate;
    }

    public void setApprovestate(String approvestate) {
        this.approvestate = approvestate;
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
    @Column(name = "code", nullable = false, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
    @Column(name = "supplierid", nullable = false)
    public int getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(int supplierid) {
        this.supplierid = supplierid;
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
        Supplierlist that = (Supplierlist) o;
        return urid == that.urid &&
                projectid == that.projectid &&
                supplierid == that.supplierid &&
                reversion == that.reversion &&
                Objects.equals(code, that.code) &&
                Objects.equals(createdby, that.createdby) &&
                Objects.equals(createdon, that.createdon) &&
                Objects.equals(updatedby, that.updatedby) &&
                Objects.equals(updatedon, that.updatedon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urid, code, projectid, supplierid, createdby, createdon, updatedby, updatedon, reversion);
    }
}
