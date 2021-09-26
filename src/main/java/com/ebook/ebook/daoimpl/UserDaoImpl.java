package com.ebook.ebook.daoimpl;

import com.ebook.ebook.dao.UserDao;
import com.ebook.ebook.entity.User;
import com.ebook.ebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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

    @Override
    public User getOne(Integer userId){
        return userRepository.getOne(userId);
    }

    @Override
    public List<User> findAll()
    {
        return userRepository.findAll();
    }

    @Override
    public Integer changeUserState(Integer userId,Boolean forbidden)
    {
        Optional<User> userOptional=userRepository.findById(userId);
        if(!userOptional.isPresent())
            return -1;
        User user=userOptional.get();
        user.setForbidden(forbidden);
        userRepository.save(user);
        return 0;
    }

    @Override
    public Integer checkUserName(String userName)
    {
        List<User> userList=userRepository.findByName(userName);
        if(userList.isEmpty())
            return 0;
        else
            return 1;
    }

    @Override
    public void addUser(User user)
    {
        userRepository.save(user);
    }

}
