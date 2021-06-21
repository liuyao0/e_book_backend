package com.ebook.ebook.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ebook.ebook.entity.Book;
import com.ebook.ebook.entity.User;
import com.ebook.ebook.service.UserService;
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
    private UserService userService;
    @RequestMapping("/login")
    public String Login(@RequestParam(value="username") String username,@RequestParam(value="password") String password)
    {
        User user=userService.findByUserName(username);
        if(user==null)
            return "-1,-1";
        if(!user.getPassword().equals(password))
            return "-1,-1";
        if(user.getForbidden())
            return "-1,-2";
        return user.getUserId().toString()+','+user.getType();
    }

    @RequestMapping("/allUser")
    public String getAllUser()
    {
        return userService.getAllUser();
    }

    @RequestMapping("/setUserState")
    public Integer changeUserState(@RequestParam(value="user_id") Integer userId,@RequestParam(value="state") Integer state){
        return userService.changeUserState(userId,state==1);
    }

}
