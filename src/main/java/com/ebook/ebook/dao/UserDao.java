package com.ebook.ebook.dao;

import com.ebook.ebook.entity.User;

import java.util.List;

public interface UserDao {
    User findByUserName(String userName);
    User getOne(Integer userId);
    Integer changeUserState(Integer userId,Boolean forbidden);
    List<User> findAll();
    Integer checkUserName(String userName);
    void addUser(User user);
}
