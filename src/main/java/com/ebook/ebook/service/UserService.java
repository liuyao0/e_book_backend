package com.ebook.ebook.service;

import com.ebook.ebook.entity.User;

import java.util.List;

public interface UserService {
    User findByUserName(String userName);
    String getAllUser();
    Integer changeUserState(Integer userId,Boolean forbidden);
}
