package com.ebook.ebook.dao;

import com.ebook.ebook.entity.User;

import java.util.List;

public interface UserDao {
    User findByUserName(String userName);
}
