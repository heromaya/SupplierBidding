package com.laodyu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "t_sys_notice", schema = "supplierbidding", catalog = "")
public class Notice {
    private int urid;
    private String code;
    private String tendingid;
    private String position;
    private Date time;
    private int projectid;
    private Date startdate;
    private Date enddate;
    private String createdby;
    private Timestamp createdon;
    private String updatedby;
    private Timestamp updatedon;
    private int reversion;
    private String approvestate;
    private String cancelstate;
    private String content;
    private String name;
    private String processInstenceid;
    @Basic
    @Column(name = "processinstanceid", nullable = true, length = 255)
    public String getProcessInstenceid() {
        return processInstenceid;
    }

    public void setProcessInstenceid(String processInstenceid) {
        this.processInstenceid = processInstenceid;
    }
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    @Column(name = "tendingid", nullable = false)
    public String getTendingid() {
        return tendingid;
    }

    public void setTendingid(String tendingid) {
        this.tendingid = tendingid;
    }
    @Basic
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Basic
    @Column(name = "position", nullable = false, length = 255)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "time", nullable = false)
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "startdate", nullable = false)
    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    @Basic
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "enddate", nullable = false)
    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
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
    @Column(name = "content", nullable = false, length = 4000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notice notice = (Notice) o;
        return urid == notice.urid &&
                projectid == notice.projectid &&
                reversion == notice.reversion &&
                Objects.equals(tendingid, notice.tendingid) &&
                Objects.equals(code, notice.code) &&
                Objects.equals(position, notice.position) &&
                Objects.equals(time, notice.time) &&
                Objects.equals(startdate, notice.startdate) &&
                Objects.equals(enddate, notice.enddate) &&
                Objects.equals(createdby, notice.createdby) &&
                Objects.equals(createdon, notice.createdon) &&
                Objects.equals(updatedby, notice.updatedby) &&
                Objects.equals(updatedon, notice.updatedon) &&
                Objects.equals(approvestate, notice.approvestate) &&
                Objects.equals(cancelstate, notice.cancelstate) &&
                Objects.equals(content, notice.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urid, code, tendingid, position, time, projectid, startdate, enddate, createdby, createdon, updatedby, updatedon, reversion, approvestate, cancelstate, content);
    }
}
