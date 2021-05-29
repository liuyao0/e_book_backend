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

    public User(Integer userId, String user_name, String password)
    {
        this.userId = userId;
        this.name = user_name;
        this.password = password;
    }

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
