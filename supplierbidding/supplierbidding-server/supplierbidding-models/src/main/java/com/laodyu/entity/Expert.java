package com.laodyu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Table(name = "t_sys_expert", schema = "supplierbidding", catalog = "")
public class Expert {
    private int urid;
    private String code;
    private String name;
    private String sex;
    private Date birthday;
    private String education;
    private String educationbackground;
    private String specialitydomain;
    private int typeid;
    private int groupid;
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
    @Column(name = "code", nullable = false, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @Basic
    @Column(name = "typeid", nullable = false, length = 255)
    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
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
    @Column(name = "sex", nullable = false, length = 1)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday", nullable = false)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "education", nullable = false, length = 255)
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Basic
    @Column(name = "educationbackground", nullable = false, length = 255)
    public String getEducationbackground() {
        return educationbackground;
    }

    public void setEducationbackground(String educationbackground) {
        this.educationbackground = educationbackground;
    }

    @Basic
    @Column(name = "specialitydomain", nullable = false, length = 255)
    public String getSpecialitydomain() {
        return specialitydomain;
    }

    public void setSpecialitydomain(String specialitydomain) {
        this.specialitydomain = specialitydomain;
    }

    @Basic
    @Column(name = "groupid", nullable = false)
    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
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
        Expert expert = (Expert) o;
        return urid == expert.urid &&
                groupid == expert.groupid &&
                reversion == expert.reversion &&
                Objects.equals(code, expert.code) &&
                Objects.equals(name, expert.name) &&
                Objects.equals(sex, expert.sex) &&
                Objects.equals(birthday, expert.birthday) &&
                Objects.equals(education, expert.education) &&
                Objects.equals(educationbackground, expert.educationbackground) &&
                Objects.equals(specialitydomain, expert.specialitydomain) &&
                Objects.equals(createdby, expert.createdby) &&
                Objects.equals(createdon, expert.createdon) &&
                Objects.equals(updatedby, expert.updatedby) &&
                Objects.equals(updatedon, expert.updatedon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urid, code, name, sex, birthday, education, educationbackground, specialitydomain, groupid, createdby, createdon, updatedby, updatedon, reversion);
    }
}
