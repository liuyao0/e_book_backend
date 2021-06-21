package com.ebook.ebook.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ebook.ebook.dao.UserDao;
import com.ebook.ebook.entity.User;
import com.ebook.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowWebServer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public User findByUserName(String userName){
        return userDao.findByUserName(userName);
    }


    @Override
    public String getAllUser(){
        List<User> users=userDao.findAll();
        ArrayList<JSONArray> usersJson = new ArrayList<JSONArray>();
        Iterator<User> iter = users.iterator();
        while(iter.hasNext())
        {
            User user=(User) iter.next();
            ArrayList<Object> arrayList = new ArrayList<Object>();
            arrayList.add(user.getUserId());
            arrayList.add(user.getName());
            arrayList.add(user.getEmail());
            arrayList.add(user.getType());
            arrayList.add(user.getForbidden());
            usersJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        String usersString = JSON.toJSONString(usersJson, SerializerFeature.BrowserCompatible);
        return usersString;
    }

    @Override
    public Integer changeUserState(Integer userId,Boolean forbidden)
    {
        return userDao.changeUserState(userId,forbidden);
    }

}
