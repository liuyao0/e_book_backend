package com.ebook.ebook.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ebook.ebook.dao.BookDao;
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
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private BookDao bookDao;

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

    @Override
    public String getConsumeBooks(Timestamp beginTime, Timestamp endTime,Integer userId)
    {
        BigDecimal total=new BigDecimal(0);
        List<Order> orderList=orderDao.findByUserId(userId);
        orderList=orderList.stream().filter(order -> {
            Timestamp timestamp=order.getOrderTime();
            if(order.getOrderTime().before(beginTime)||order.getOrderTime().after(endTime))
                return false;
            return true;
        }).collect(Collectors.toList());
        ArrayList<JSONArray> booksJson = new ArrayList<JSONArray>();

        Map<Book,Integer> map=new HashMap<>();
        for (Order order:orderList)
        {
            List<OrderDetail> orderDetailList=order.getOrderDetails();
            for(OrderDetail orderDetail:orderDetailList)
            {
                Book book=bookDao.getOne(orderDetail.getOrderDetailPK().getBookId());
                if(map.containsKey(book))
                {
                    Integer oldQuantity = map.get(book);
                    map.replace(book,oldQuantity+orderDetail.getQuantity());
                }
                else
                    map.put(book,orderDetail.getQuantity());
            }
        }
        List<Map.Entry<Book,Integer>> entryList=new ArrayList<Map.Entry<Book,Integer>>(map.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<Book, Integer>>() {
            @Override
            public int compare(Map.Entry<Book, Integer> o1, Map.Entry<Book, Integer> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }
        });

        for(Map.Entry<Book,Integer> entry:entryList)
        {
            BigDecimal bigDecimal=new BigDecimal(0);
            Book book = entry.getKey();
            Integer quantity=entry.getValue();
            ArrayList<Object> arrayList=new ArrayList<>();
            arrayList.add(book.getName());
            arrayList.add(book.getIsbn());
            arrayList.add(book.getAuthor());
            arrayList.add(book.getPress());
            arrayList.add(book.getPrice());
            arrayList.add(quantity);
            bigDecimal=book.getPrice().multiply(new BigDecimal(quantity));
            total=total.add(bigDecimal);
            arrayList.add(bigDecimal);
            booksJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        ArrayList<BigDecimal> bigDecimalArrayList=new ArrayList<>();
        bigDecimalArrayList.add(total);
        booksJson.add((JSONArray) JSONArray.toJSON(bigDecimalArrayList));
        String booksString=JSON.toJSONString(booksJson,SerializerFeature.BrowserCompatible);
        return booksString;
    }

    @Override
    public Integer checkUserName(String userName)
    {
        return userDao.checkUserName(userName);
    }

    @Override
    public void addUser(User user)
    {
        userDao.addUser(user);
    }
}
