package com.ebook.ebook.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
@Entity
@Table(name="user")
@JsonIgnoreProperties(value={"handler","hibernateLazyInitializer","filedHandler"})
@JsonIdentityInfo(
        generator =ObjectIdGenerators.PropertyGenerator.class,
        property = "userId"
)

public class User  {
    private Integer userId;
    private String name;
    private String password;
    private  Integer type;
    private String email;
    private Boolean forbidden;
    public User() {
    }
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = IDENTITY)
    public Integer getUserId() {return userId;}
    public void setUserId(Integer userId) {this.userId = userId;}

    @Basic
    @Column(name="user_name")
    public String getName() {return name;}
    public void setName(String name){this.name = name;}

    @Basic
    @Column(name="password")
    public String getPassword() {return password; }
    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name="user_type")
    public Integer getType() {return type;}
    public void setType(Integer type) {this.type = type;}

    public User(String name, String password, String email) {
        this.userId = 0;
        this.type=1;
        this.forbidden=false;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User(Integer userId, String name, String password, Integer type, String email, Boolean forbidden) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.type = type;
        this.email = email;
        this.forbidden = forbidden;
    }

    @Basic
    @Column(name="email")
    public String getEmail(){return email;}
    public void setEmail(String email) { this.email = email; }

    @Basic
    @Column(name="user_forbidden")
    public Boolean getForbidden() { return forbidden; }
    public void setForbidden(Boolean forbidden) { this.forbidden = forbidden; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
