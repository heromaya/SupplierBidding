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
@Table(name = "t_sys_role_menu", schema = "supplierbidding", catalog = "")
public class RoleMenu {
    private int urid;
    private int roleid;
    private Integer menuid;
    private String createdby;
    private Timestamp createdon;
    private String updatedby;
    private Timestamp updatedon;
    private int reversion;

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name = "urid", nullable = false)
    public int getUrid() {
        return urid;
    }

    public void setUrid(int urid) {
        this.urid = urid;
    }

    @Basic
    @Column(name = "roleid", nullable = false)
    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    @Basic
    @Column(name = "menuid", nullable = true)
    public Integer getMenuid() {
        return menuid;
    }

    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
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
        RoleMenu roleMenu = (RoleMenu) o;
        return urid == roleMenu.urid &&
                roleid == roleMenu.roleid &&
                reversion == roleMenu.reversion &&
                Objects.equals(menuid, roleMenu.menuid) &&
                Objects.equals(createdby, roleMenu.createdby) &&
                Objects.equals(createdon, roleMenu.createdon) &&
                Objects.equals(updatedby, roleMenu.updatedby) &&
                Objects.equals(updatedon, roleMenu.updatedon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urid, roleid, menuid, createdby, createdon, updatedby, updatedon, reversion);
    }
}
