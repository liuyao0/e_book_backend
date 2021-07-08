package com.ebook.ebook.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ebook.ebook.dao.OrderDao;
import com.ebook.ebook.dao.UserDao;
import com.ebook.ebook.entity.Book;
import com.ebook.ebook.entity.Order;
import com.ebook.ebook.entity.OrderDetail;
import com.ebook.ebook.entity.User;
import com.ebook.ebook.service.UserService;
import javafx.util.Pair;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.undertow.UndertowWebServer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

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

    @Override
    public String getConsumeRankings(Timestamp beginTime,Timestamp endTime)
    {
        List<User> users=userDao.findAll();
        Map<User,BigDecimal> map=new HashMap<>();

        Iterator<User> iterator=users.iterator();
        while(iterator.hasNext())
        {
            BigDecimal total=new BigDecimal(0);
            User user=(User) iterator.next();
            List<Order> orderList=orderDao.findByUserId(user.getUserId());
            Iterator<Order> orderIterator=orderList.iterator();
            while(orderIterator.hasNext())
            {
                BigDecimal priceOfAnItem=new BigDecimal(0);
                Order order=(Order) orderIterator.next();
                if(order.getOrderTime().after(endTime)||order.getOrderTime().before(beginTime))
                    continue;
                List<OrderDetail> orderDetailList=order.getOrderDetails();
                for(OrderDetail orderDetail:orderDetailList)
                    priceOfAnItem=priceOfAnItem.add(orderDetail.getRealprice().multiply(new BigDecimal(orderDetail.getQuantity())));
                total=total.add(priceOfAnItem);
            }
            map.put(user,total);
        }

        ArrayList<JSONArray> usersJson = new ArrayList<JSONArray>();
        List<Map.Entry<User,BigDecimal>> entryList=new ArrayList<Map.Entry<User,BigDecimal>>(map.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<User,BigDecimal>>() {
            @Override
            public int compare(Map.Entry<User, BigDecimal> o1, Map.Entry<User, BigDecimal> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }
        });

        for(Map.Entry<User,BigDecimal> entry:entryList)
        {
            User user=(User)entry.getKey();
            BigDecimal total=(BigDecimal)entry.getValue();
            ArrayList<Object> arrayList=new ArrayList<>();
            arrayList.add(user.getUserId());
            arrayList.add(user.getName());
            arrayList.add(total);
            usersJson.add((JSONArray)JSONArray.toJSON(arrayList));
        }
        String usersString = JSON.toJSONString(usersJson, SerializerFeature.BrowserCompatible);
        return usersString;
    }
}
