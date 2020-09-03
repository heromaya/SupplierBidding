package com.laodyu.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/10
 * @Version 1.0
 **/
@Entity
@Table(name = "t_sys_mytask", schema = "supplierbidding", catalog = "")
public class Mytask {
    private int urid;
    private String taskid;
    private String assignee;
    private String processInstenceid;
    private String tasktype;
    private String suggest;
    private String complete;
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
    @Column(name = "taskid", nullable = true, length = 255)
    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    @Basic
    @Column(name = "tasktype", nullable = true, length = 255)
    public String getTasktype() {
        return tasktype;
    }

    public void setTasktype(String tasktype) {
        this.tasktype = tasktype;
    }

    @Basic
    @Column(name = "assignee", nullable = true, length = 255)
    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }


    @Basic
    @Column(name = "processInstenceid", nullable = true, length = 255)
    public String getProcessInstenceid() {
        return processInstenceid;
    }

    public void setProcessInstenceid(String processInstenceid) {
        this.processInstenceid = processInstenceid;
    }



    @Basic
    @Column(name = "suggest", nullable = true, length = 255)
    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    @Basic
    @Column(name = "complete", nullable = true, length = 255)
    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
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
        Mytask mytask = (Mytask) o;
        return urid == mytask.urid &&
                reversion == mytask.reversion &&
                Objects.equals(taskid, mytask.taskid) &&
                Objects.equals(tasktype, mytask.tasktype) &&
                Objects.equals(assignee, mytask.assignee) &&
                Objects.equals(processInstenceid, mytask.processInstenceid) &&
                Objects.equals(suggest, mytask.suggest) &&
                Objects.equals(complete, mytask.complete) &&
                Objects.equals(createdby, mytask.createdby) &&
                Objects.equals(createdon, mytask.createdon) &&
                Objects.equals(updatedby, mytask.updatedby) &&
                Objects.equals(updatedon, mytask.updatedon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urid, taskid, tasktype, assignee, processInstenceid, suggest, complete, createdby, createdon, updatedby, updatedon, reversion);
    }
}
