package com.ebook.ebook.controller;

import com.ebook.ebook.entity.User;
import com.ebook.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public String Login(@RequestParam(value="username") String username, @RequestParam(value="password") String password, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        return userService.login(username,password,httpServletRequest,httpServletResponse);
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

    @RequestMapping("/getConsumeRanking")
    public String getConsumeRankings(@RequestParam(value="begin_time") Long beginTime, @RequestParam(value="end_time") Long endTime)
    {
        Timestamp beginTimestamp=new Timestamp(beginTime);
        Timestamp endTimestamp=new Timestamp(endTime);
        return userService.getConsumeRankings(beginTimestamp,endTimestamp);
    }

    @RequestMapping("/getConsumeBook")
    public String getConsumeBooks(@RequestParam(value="begin_time") Long beginTime, @RequestParam(value="end_time") Long endTime,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
    {
        Timestamp beginTimestamp=new Timestamp(beginTime);
        Timestamp endTimestamp=new Timestamp(endTime);
        return userService.getConsumeBooks(beginTimestamp,endTimestamp,httpServletRequest,httpServletResponse);
    }

    @RequestMapping("/usernameExist")
    public Integer checkUserName(@RequestParam(value = "username") String userName)
    {
        return userService.checkUserName(userName);
    }

    @RequestMapping("/addUser")
    public void addUser(@RequestParam(value = "username") String userName,@RequestParam(value = "password") String password,@RequestParam(value = "email") String email)
    {
        User user=new User(userName,password,email);
        userService.addUser(user);
    }

    @RequestMapping("/exitLogin")
    public void exitLogin(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
    {
        httpServletRequest.getSession().removeAttribute("userId");
    }

    @RequestMapping("/getLoggedUsernameAndUserType")
    public String getLoggedUsernameAndUserType(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        return userService.getLoggedUsernameAndUserType(httpServletRequest,httpServletResponse);
    }
}
