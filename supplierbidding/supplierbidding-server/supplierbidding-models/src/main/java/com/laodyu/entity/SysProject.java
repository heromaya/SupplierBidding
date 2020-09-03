package com.laodyu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/8
 * @Version 1.0
 **/
@Entity
@Table(name = "t_sys_project", schema = "supplierbidding", catalog = "")
public class SysProject {
    private int urid;
    private String code;
    private String tenderingid;
    private String projectcode;
    private String projectname;
    private String approvestate;
    private String cancelstate;
    private String projectcontent;
    private String invitationtype;
    private int typeid;
    private Date applydate;
    private Date finishdate;
    private String createdby;
    private Timestamp createdon;
    private String updatedby;
    private Timestamp updatedon;
    private int reversion;
    private String status;
    private String processInstenceid;
    private List<Product> productList;
    private Userfile userfile;
    private List<Supplier> supplierList;

    @Transient
    public List<Supplier> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<Supplier> supplierList) {
        this.supplierList = supplierList;
    }

    @Transient
    public Userfile getUserfile() {
        return userfile;
    }

    public void setUserfile(Userfile userfile) {
        this.userfile = userfile;
    }

    @Transient
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Basic
    @Column(name = "processinstanceid" , length = 255)
    public String getProcessInstenceid() {
        return processInstenceid;
    }

    public void setProcessInstenceid(String processInstenceid) {
        this.processInstenceid = processInstenceid;
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
    @Column(name = "code",  length = 255)
    public String getCode(){
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @Basic
    @Column(name = "typeid", length = 255)
    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }
    @Basic
    @Column(name = "tenderingid" )
    public String getTenderingid() {
        return tenderingid;
    }

    public void setTenderingid(String tenderingid) {
        this.tenderingid = tenderingid;
    }

    @Basic
    @Column(name = "projectcode", nullable = false, length = 32)
    public String getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode;
    }

    @Basic
    @Column(name = "projectname", nullable = false, length = 255)
    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    @Basic
    @Column(name = "approvestate",  length = 1)
    public String getApprovestate() {
        return approvestate;
    }

    public void setApprovestate(String approvestate) {
        this.approvestate = approvestate;
    }

    @Basic
    @Column(name = "cancelstate",  length = 1)
    public String getCancelstate() {
        return cancelstate;
    }

    public void setCancelstate(String cancelstate) {
        this.cancelstate = cancelstate;
    }

    @Basic
    @Column(name = "projectcontent", length = 4000)
    public String getProjectcontent() {
        return projectcontent;
    }

    public void setProjectcontent(String projectcontent) {
        this.projectcontent = projectcontent;
    }

    @Basic
    @Column(name = "invitationtype" , length = 1)
    public String getInvitationtype() {
        return invitationtype;
    }

    public void setInvitationtype(String invitationtype) {
        this.invitationtype = invitationtype;
    }

    @Basic
    @Column(name = "applydate" )
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getApplydate() {
        return applydate;
    }

    public void setApplydate(Date applydate) {
        this.applydate = applydate;
    }

    @Basic
    @Column(name = "finishdate" )
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getFinishdate() {
        return finishdate;
    }

    public void setFinishdate(Date finishdate) {
        this.finishdate = finishdate;
    }

    @Basic
    @Column(name = "createdby", length = 255)
    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    @Basic
    @Column(name = "createdon")
    public Timestamp getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Timestamp createdon) {
        this.createdon = createdon;
    }

    @Basic
    @Column(name = "updatedby", length = 255)
    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    @Basic
    @Column(name = "updatedon")
    public Timestamp getUpdatedon() {
        return updatedon;
    }

    public void setUpdatedon(Timestamp updatedon) {
        this.updatedon = updatedon;
    }

    @Basic
    @Column(name = "reversion")
    public int getReversion() {
        return reversion;
    }

    public void setReversion(int reversion) {
        this.reversion = reversion;
    }

    @Basic
    @Column(name = "status",  length = 1)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysProject that = (SysProject) o;
        return urid == that.urid &&
                reversion == that.reversion &&
                Objects.equals(code, that.code) &&
                Objects.equals(tenderingid, that.tenderingid) &&
                Objects.equals(projectcode, that.projectcode) &&
                Objects.equals(projectname, that.projectname) &&
                Objects.equals(approvestate, that.approvestate) &&
                Objects.equals(cancelstate, that.cancelstate) &&
                Objects.equals(projectcontent, that.projectcontent) &&
                Objects.equals(invitationtype, that.invitationtype) &&
                Objects.equals(applydate, that.applydate) &&
                Objects.equals(finishdate, that.finishdate) &&
                Objects.equals(createdby, that.createdby) &&
                Objects.equals(createdon, that.createdon) &&
                Objects.equals(updatedby, that.updatedby) &&
                Objects.equals(updatedon, that.updatedon) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urid, code, tenderingid, projectcode, projectname, approvestate, cancelstate, projectcontent, invitationtype, applydate, finishdate, createdby, createdon, updatedby, updatedon, reversion, status);
    }
}
