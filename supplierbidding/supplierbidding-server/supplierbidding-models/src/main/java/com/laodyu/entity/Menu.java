package com.laodyu.entity;

import javax.persistence.*;
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
@Table(name = "t_sys_menu", schema = "supplierbidding", catalog = "")
public class Menu {
    private int urid;
    private String url;
    private String path;
    private String component;
    private String name;
    private String icon;
    private Integer parentid;
    private String enabled;
    private String createdby;
    private String updatedby;
    private Timestamp createdon;
    private Timestamp updatedon;
    private int reversion;
    private List<com.laodyu.entity.Menu> children;
    private List<com.laodyu.entity.Role> roles;

    @Transient
    public List<com.laodyu.entity.Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Transient
    public List<com.laodyu.entity.Menu> getChildren() {
        return children;
    }

    public void setChildren(List<com.laodyu.entity.Menu> children) {
        this.children = children;
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
    @Column(name = "url", nullable = false, length = 64)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "path", nullable = true, length = 64)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "component", nullable = true, length = 64)
    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "icon", nullable = true, length = 64)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "parentid", nullable = true)
    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    @Basic
    @Column(name = "enabled", nullable = true, length = 1)
    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
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
    @Column(name = "updatedby", nullable = false, length = 255)
    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
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
        Menu menu = (Menu) o;
        return urid == menu.urid &&
                reversion == menu.reversion &&
                Objects.equals(url, menu.url) &&
                Objects.equals(path, menu.path) &&
                Objects.equals(component, menu.component) &&
                Objects.equals(name, menu.name) &&
                Objects.equals(icon, menu.icon) &&
                Objects.equals(parentid, menu.parentid) &&
                Objects.equals(enabled, menu.enabled) &&
                Objects.equals(createdby, menu.createdby) &&
                Objects.equals(updatedby, menu.updatedby) &&
                Objects.equals(createdon, menu.createdon) &&
                Objects.equals(updatedon, menu.updatedon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urid, url, path, component, name, icon, parentid, enabled, createdby, updatedby, createdon, updatedon, reversion);
    }
}
