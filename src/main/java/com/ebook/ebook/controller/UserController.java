package com.ebook.ebook.controller;

import com.ebook.ebook.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/login")
    public Integer Login(@RequestParam(value="username") String username,@RequestParam(value="password") String password)
    {
        List<User> result=new ArrayList<User>();
        result=jdbcTemplate.query(
                "select password,user_id,user_name from user where user_name='"+username+"'",
                (rs, rowNum)->new User(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("password")
                )
        );
        Iterator<User> iter=result.iterator();
        if(iter.hasNext())
        {
            User usr= (User) iter.next();
            if(usr.getPassword().equals(password))
                return usr.getUser_id();
        }
        return -1;
    }
}
