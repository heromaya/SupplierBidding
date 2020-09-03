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
@Table(name = "t_sys_applyform", schema = "supplierbidding", catalog = "")
public class Applyform {
    private int urid;
    private String code;
    private String approvestate;
    private int supplierid;
    private int projectid;
    private String content;
    private String principal;
    private String createdby;
    private Timestamp createdon;
    private String updatedby;
    private Timestamp updatedon;
    private int reversion;
    private String cancelstate;
    private SysProject project;
    private Supplier supplier;

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
    @Column(name = "approvestate", nullable = false, length = 1)
    public String getApprovestate() {
        return approvestate;
    }

    public void setApprovestate(String approvestate) {
        this.approvestate = approvestate;
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
    @Column(name = "projectid", nullable = false)
    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    @Basic
    @Column(name = "content", nullable = false, length = 4000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "principal", nullable = false, length = 255)
    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
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
    @Column(name = "cancelstate", nullable = false, length = 1)
    public String getCancelstate() {
        return cancelstate;
    }

    public void setCancelstate(String cancelstate) {
        this.cancelstate = cancelstate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Applyform applyform = (Applyform) o;
        return urid == applyform.urid &&
                supplierid == applyform.supplierid &&
                projectid == applyform.projectid &&
                reversion == applyform.reversion &&
                Objects.equals(code, applyform.code) &&
                Objects.equals(approvestate, applyform.approvestate) &&
                Objects.equals(content, applyform.content) &&
                Objects.equals(principal, applyform.principal) &&
                Objects.equals(createdby, applyform.createdby) &&
                Objects.equals(createdon, applyform.createdon) &&
                Objects.equals(updatedby, applyform.updatedby) &&
                Objects.equals(updatedon, applyform.updatedon) &&
                Objects.equals(cancelstate, applyform.cancelstate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urid, code, approvestate, supplierid, projectid, content, principal, createdby, createdon, updatedby, updatedon, reversion, cancelstate);
    }
}
