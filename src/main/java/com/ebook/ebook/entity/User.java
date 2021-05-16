package com.ebook.ebook.entity;

public class User  {
    private Integer user_id;
    private String name;
    private String password;

    public Integer getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public User(Integer user_id,String user_name,String password)
    {
        this.user_id=user_id;
        this.name=user_name;
        this.password=password;
    }
}
