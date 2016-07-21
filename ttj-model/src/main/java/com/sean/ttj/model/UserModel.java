package com.sean.ttj.model;

import java.util.Date;

/**
 * Created by sean on 2016/7/12.
 */
public class UserModel {

    private Long    id;
    private String  name;
    private String  pwd;
    private String  email;
    private String  phone;
    private Date createdt;
    private Date modifydt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreatedt() {
        return createdt;
    }

    public void setCreatedt(Date createdt) {
        this.createdt = createdt;
    }

    public Date getModifydt() {
        return modifydt;
    }

    public void setModifydt(Date modifydt) {
        this.modifydt = modifydt;
    }
}
