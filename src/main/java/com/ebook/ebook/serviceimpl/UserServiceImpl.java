package com.ebook.ebook.serviceimpl;

import com.ebook.ebook.dao.UserDao;
import com.ebook.ebook.entity.User;
import com.ebook.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public User findByUserName(String userName){
        return userDao.findByUserName(userName);
    }
}
