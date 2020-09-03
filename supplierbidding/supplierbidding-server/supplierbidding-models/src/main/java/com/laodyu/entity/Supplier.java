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
@Table(name = "t_sys_supplier", schema = "supplierbidding", catalog = "")
public class Supplier {
    private int urid;
    private String userid;
    private String password;
    private String salt;
    private Integer score;
    private Integer bidcount;
    private String taxid;
    private String email;
    private Integer invitedcount;
    private String name;
    private String approvestate;
    private String cancelstate;
    private String address;
    private String telephone;
    private Integer typeid;
    private String processInstenceid;
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
    @Column(name = "processinstanceid", nullable = true, length = 255)
    public String getProcessInstenceid() {
        return processInstenceid;
    }

    public void setProcessInstenceid(String processInstenceid) {
        this.processInstenceid = processInstenceid;
    }
    @Basic
    @Column(name = "userid", nullable = false, length = 32)
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "salt", nullable = false, length = 255)
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Basic
    @Column(name = "score", nullable = true)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "bidcount", nullable = true)
    public Integer getBidcount() {
        return bidcount;
    }

    public void setBidcount(Integer bidcount) {
        this.bidcount = bidcount;
    }

    @Basic
    @Column(name = "taxid", nullable = false, length = 64)
    public String getTaxid() {
        return taxid;
    }

    public void setTaxid(String taxid) {
        this.taxid = taxid;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "invitedcount", nullable = true)
    public Integer getInvitedcount() {
        return invitedcount;
    }

    public void setInvitedcount(Integer invitedcount) {
        this.invitedcount = invitedcount;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "cancelstate", nullable = false, length = 1)
    public String getCancelstate() {
        return cancelstate;
    }

    public void setCancelstate(String cancelstate) {
        this.cancelstate = cancelstate;
    }

    @Basic
    @Column(name = "address", nullable = false, length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "telephone", nullable = false, length = 255)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "typeid", nullable = true)
    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
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
        Supplier supplier = (Supplier) o;
        return urid == supplier.urid &&
                reversion == supplier.reversion &&
                Objects.equals(userid, supplier.userid) &&
                Objects.equals(password, supplier.password) &&
                Objects.equals(salt, supplier.salt) &&
                Objects.equals(score, supplier.score) &&
                Objects.equals(bidcount, supplier.bidcount) &&
                Objects.equals(taxid, supplier.taxid) &&
                Objects.equals(email, supplier.email) &&
                Objects.equals(invitedcount, supplier.invitedcount) &&
                Objects.equals(name, supplier.name) &&
                Objects.equals(approvestate, supplier.approvestate) &&
                Objects.equals(cancelstate, supplier.cancelstate) &&
                Objects.equals(address, supplier.address) &&
                Objects.equals(telephone, supplier.telephone) &&
                Objects.equals(typeid, supplier.typeid) &&
                Objects.equals(createdby, supplier.createdby) &&
                Objects.equals(createdon, supplier.createdon) &&
                Objects.equals(updatedby, supplier.updatedby) &&
                Objects.equals(updatedon, supplier.updatedon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urid, userid, password, salt, score, bidcount, taxid, email, invitedcount, name, approvestate, cancelstate, address, telephone, typeid, createdby, createdon, updatedby, updatedon, reversion);
    }
}
