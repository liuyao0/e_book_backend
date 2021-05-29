package com.ebook.ebook.service;

import com.ebook.ebook.entity.User;

public interface UserService {
    User findByUserName(String userName);
}
