package com.laodyu.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/9
 * @Version 1.0
 **/
@Entity
@Table(name = "t_sys_student", schema = "supplierbidding", catalog = "")
public class Student {
    private int urid;
    private String name;
    private String code;

    @Id
    @Column(name = "urid", nullable = false)
    public int getUrid() {
        return urid;
    }

    public void setUrid(int urid) {
        this.urid = urid;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "code", nullable = true, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return urid == student.urid &&
                Objects.equals(name, student.name) &&
                Objects.equals(code, student.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urid, name, code);
    }
}
