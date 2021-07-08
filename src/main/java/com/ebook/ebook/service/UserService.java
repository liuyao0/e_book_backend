package com.ebook.ebook.service;

import com.ebook.ebook.entity.User;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface UserService {
    User findByUserName(String userName);
    String getAllUser();
    Integer changeUserState(Integer userId,Boolean forbidden);
    String getConsumeRankings(Timestamp beginTime, Timestamp endTime);
    String getConsumeBooks(Timestamp beginTime, Timestamp endTime,Integer userId);
    Integer checkUserName(String userName);
    void addUser(User user);
}
