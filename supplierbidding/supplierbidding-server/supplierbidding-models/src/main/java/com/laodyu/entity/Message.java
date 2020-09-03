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
@Table(name = "t_sys_message", schema = "supplierbidding", catalog = "")
public class Message {
    private int urid;
    private Integer tenderingid;
    private Integer supplierid;
    private Integer projectid;
    private Timestamp sendtime;
    private Integer lastmessageid;
    private String content;
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
    @Column(name = "tenderingid", nullable = true)
    public Integer getTenderingid() {
        return tenderingid;
    }

    public void setTenderingid(Integer tenderingid) {
        this.tenderingid = tenderingid;
    }

    @Basic
    @Column(name = "supplierid", nullable = true)
    public Integer getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(Integer supplierid) {
        this.supplierid = supplierid;
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
    @Column(name = "sendtime", nullable = true)
    public Timestamp getSendtime() {
        return sendtime;
    }

    public void setSendtime(Timestamp sendtime) {
        this.sendtime = sendtime;
    }

    @Basic
    @Column(name = "lastmessageid", nullable = true)
    public Integer getLastmessageid() {
        return lastmessageid;
    }

    public void setLastmessageid(Integer lastmessageid) {
        this.lastmessageid = lastmessageid;
    }

    @Basic
    @Column(name = "content", nullable = true, length = 4000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        Message message = (Message) o;
        return urid == message.urid &&
                reversion == message.reversion &&
                Objects.equals(tenderingid, message.tenderingid) &&
                Objects.equals(supplierid, message.supplierid) &&
                Objects.equals(projectid, message.projectid) &&
                Objects.equals(sendtime, message.sendtime) &&
                Objects.equals(lastmessageid, message.lastmessageid) &&
                Objects.equals(content, message.content) &&
                Objects.equals(createdby, message.createdby) &&
                Objects.equals(createdon, message.createdon) &&
                Objects.equals(updatedby, message.updatedby) &&
                Objects.equals(updatedon, message.updatedon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urid, tenderingid, supplierid, projectid, sendtime, lastmessageid, content, createdby, createdon, updatedby, updatedon, reversion);
    }
}
