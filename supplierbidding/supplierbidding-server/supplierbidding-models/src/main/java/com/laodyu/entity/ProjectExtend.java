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
@Table(name = "t_sys_project_extend", schema = "supplierbidding", catalog = "")
public class ProjectExtend {
    private int urid;
    private int projectid;
    private String extend1;
    private String extend2;
    private String extend3;
    private String extend4;
    private String extend5;
    private String extend6;
    private String extend7;
    private String extend8;
    private String extend9;
    private String extend10;
    private String extend11;
    private String extend12;
    private String extend13;
    private String extend14;
    private String extend15;
    private String extend16;
    private String extend17;
    private String extend18;
    private String extend19;
    private String extend20;
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
    @Column(name = "projectid", nullable = false)
    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    @Basic
    @Column(name = "extend1", nullable = true, length = 255)
    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    @Basic
    @Column(name = "extend2", nullable = true, length = 255)
    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    @Basic
    @Column(name = "extend3", nullable = true, length = 255)
    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

    @Basic
    @Column(name = "extend4", nullable = true, length = 255)
    public String getExtend4() {
        return extend4;
    }

    public void setExtend4(String extend4) {
        this.extend4 = extend4;
    }

    @Basic
    @Column(name = "extend5", nullable = true, length = 255)
    public String getExtend5() {
        return extend5;
    }

    public void setExtend5(String extend5) {
        this.extend5 = extend5;
    }

    @Basic
    @Column(name = "extend6", nullable = true, length = 255)
    public String getExtend6() {
        return extend6;
    }

    public void setExtend6(String extend6) {
        this.extend6 = extend6;
    }

    @Basic
    @Column(name = "extend7", nullable = true, length = 255)
    public String getExtend7() {
        return extend7;
    }

    public void setExtend7(String extend7) {
        this.extend7 = extend7;
    }

    @Basic
    @Column(name = "extend8", nullable = true, length = 255)
    public String getExtend8() {
        return extend8;
    }

    public void setExtend8(String extend8) {
        this.extend8 = extend8;
    }

    @Basic
    @Column(name = "extend9", nullable = true, length = 255)
    public String getExtend9() {
        return extend9;
    }

    public void setExtend9(String extend9) {
        this.extend9 = extend9;
    }

    @Basic
    @Column(name = "extend10", nullable = true, length = 255)
    public String getExtend10() {
        return extend10;
    }

    public void setExtend10(String extend10) {
        this.extend10 = extend10;
    }

    @Basic
    @Column(name = "extend11", nullable = true, length = 255)
    public String getExtend11() {
        return extend11;
    }

    public void setExtend11(String extend11) {
        this.extend11 = extend11;
    }

    @Basic
    @Column(name = "extend12", nullable = true, length = 255)
    public String getExtend12() {
        return extend12;
    }

    public void setExtend12(String extend12) {
        this.extend12 = extend12;
    }

    @Basic
    @Column(name = "extend13", nullable = true, length = 255)
    public String getExtend13() {
        return extend13;
    }

    public void setExtend13(String extend13) {
        this.extend13 = extend13;
    }

    @Basic
    @Column(name = "extend14", nullable = true, length = 255)
    public String getExtend14() {
        return extend14;
    }

    public void setExtend14(String extend14) {
        this.extend14 = extend14;
    }

    @Basic
    @Column(name = "extend15", nullable = true, length = 255)
    public String getExtend15() {
        return extend15;
    }

    public void setExtend15(String extend15) {
        this.extend15 = extend15;
    }

    @Basic
    @Column(name = "extend16", nullable = true, length = 255)
    public String getExtend16() {
        return extend16;
    }

    public void setExtend16(String extend16) {
        this.extend16 = extend16;
    }

    @Basic
    @Column(name = "extend17", nullable = true, length = 255)
    public String getExtend17() {
        return extend17;
    }

    public void setExtend17(String extend17) {
        this.extend17 = extend17;
    }

    @Basic
    @Column(name = "extend18", nullable = true, length = 255)
    public String getExtend18() {
        return extend18;
    }

    public void setExtend18(String extend18) {
        this.extend18 = extend18;
    }

    @Basic
    @Column(name = "extend19", nullable = true, length = 255)
    public String getExtend19() {
        return extend19;
    }

    public void setExtend19(String extend19) {
        this.extend19 = extend19;
    }

    @Basic
    @Column(name = "extend20", nullable = true, length = 255)
    public String getExtend20() {
        return extend20;
    }

    public void setExtend20(String extend20) {
        this.extend20 = extend20;
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
        ProjectExtend that = (ProjectExtend) o;
        return urid == that.urid &&
                projectid == that.projectid &&
                reversion == that.reversion &&
                Objects.equals(extend1, that.extend1) &&
                Objects.equals(extend2, that.extend2) &&
                Objects.equals(extend3, that.extend3) &&
                Objects.equals(extend4, that.extend4) &&
                Objects.equals(extend5, that.extend5) &&
                Objects.equals(extend6, that.extend6) &&
                Objects.equals(extend7, that.extend7) &&
                Objects.equals(extend8, that.extend8) &&
                Objects.equals(extend9, that.extend9) &&
                Objects.equals(extend10, that.extend10) &&
                Objects.equals(extend11, that.extend11) &&
                Objects.equals(extend12, that.extend12) &&
                Objects.equals(extend13, that.extend13) &&
                Objects.equals(extend14, that.extend14) &&
                Objects.equals(extend15, that.extend15) &&
                Objects.equals(extend16, that.extend16) &&
                Objects.equals(extend17, that.extend17) &&
                Objects.equals(extend18, that.extend18) &&
                Objects.equals(extend19, that.extend19) &&
                Objects.equals(extend20, that.extend20) &&
                Objects.equals(createdby, that.createdby) &&
                Objects.equals(createdon, that.createdon) &&
                Objects.equals(updatedby, that.updatedby) &&
                Objects.equals(updatedon, that.updatedon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urid, projectid, extend1, extend2, extend3, extend4, extend5, extend6, extend7, extend8, extend9, extend10, extend11, extend12, extend13, extend14, extend15, extend16, extend17, extend18, extend19, extend20, createdby, createdon, updatedby, updatedon, reversion);
    }
}
