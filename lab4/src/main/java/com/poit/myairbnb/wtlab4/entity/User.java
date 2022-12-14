package com.poit.myairbnb.wtlab4.entity;

public class User {
    private long id;
    private String name;
    private String password;
    private String email;

    public User(long id, String name, String password, String email, long roleId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
    }

    private long roleId;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.roleId = 1;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(long id, String name, String email, long roleId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roleId = roleId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
