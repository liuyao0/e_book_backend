package com.ebook.ebook.daoimpl;

import com.ebook.ebook.dao.UserDao;
import com.ebook.ebook.entity.User;
import com.ebook.ebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUserName(String userName){
        List<User> users=userRepository.findByName(userName);
        Iterator<User> it=users.iterator();
        if(it.hasNext())
            return (User)it.next();
        return null;
    }
}
