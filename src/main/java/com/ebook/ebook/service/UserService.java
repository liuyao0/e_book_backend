package com.ebook.ebook.service;

import com.ebook.ebook.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface UserService {
    User findByUserName(String userName);
    String getAllUser();
    Integer changeUserState(Integer userId,Boolean forbidden);
    String getConsumeRankings(Timestamp beginTime, Timestamp endTime);
    String getConsumeBooks(Timestamp beginTime, Timestamp endTime,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse);
    Integer checkUserName(String userName);
    void addUser(User user);
    String login(String username, String password, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
    String getLoggedUsernameAndUserType(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse);
}
