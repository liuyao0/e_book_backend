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
import com.ebook.ebook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private UserDao userDao;

    @Override
    public String getOrderByUserId(Integer userId){
        List<Order> orders=orderDao.findByUserId(userId);
        Collections.reverse(orders);
        ArrayList<JSONArray> ordersJson = new ArrayList<JSONArray>();
        Iterator<Order> iter = orders.iterator();
        while (iter.hasNext()) {
            ArrayList<Object> orderList=new ArrayList<>();
            Order order=(Order) iter.next();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            orderList.add(order.getOrderId());
            orderList.add(df.format(order.getOrderTime()));
            ArrayList<Object> orderDetailList=new ArrayList<>();
            Iterator<OrderDetail> it=order.getOrderDetails().iterator();
            while(it.hasNext()){
                ArrayList<Object> arrayList=new ArrayList<>();
                OrderDetail orderDetail=(OrderDetail) it.next();
                Book book=bookDao.getOne(orderDetail.getOrderDetailPK().getBookId());
                arrayList.add(book.getName());
                arrayList.add(book.getAuthor());
                arrayList.add(book.getPress());
                arrayList.add(book.getPrice());
                arrayList.add(orderDetail.getQuantity());
                arrayList.add(book.getPrice().multiply(new BigDecimal(orderDetail.getQuantity())));
                orderDetailList.add(arrayList);
            }
            orderList.add(orderDetailList);
            ordersJson.add((JSONArray) JSONArray.toJSON(orderList));
        }

        String ordersString = JSON.toJSONString(ordersJson, SerializerFeature.BrowserCompatible);
        return ordersString;
    }

    @Override
    public String cartToOrder(Integer userId){
        return orderDao.cartToOrder(userId);
    }

    @Override
    public String getAllOrder(){
        List<Order> orders=orderDao.findAll();
        Collections.reverse(orders);
        ArrayList<JSONArray> ordersJson = new ArrayList<JSONArray>();
        Iterator<Order> iter = orders.iterator();
        while (iter.hasNext()) {
            ArrayList<Object> orderList=new ArrayList<>();
            Order order=(Order) iter.next();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            orderList.add(order.getOrderId());
            orderList.add(df.format(order.getOrderTime()));
            ArrayList<Object> orderDetailList=new ArrayList<>();
            Iterator<OrderDetail> it=order.getOrderDetails().iterator();
            while(it.hasNext()){
                ArrayList<Object> arrayList=new ArrayList<>();
                OrderDetail orderDetail=(OrderDetail) it.next();
                Book book=bookDao.getOne(orderDetail.getOrderDetailPK().getBookId());
                arrayList.add(book.getName());
                arrayList.add(book.getAuthor());
                arrayList.add(book.getPress());
                arrayList.add(book.getPrice());
                arrayList.add(orderDetail.getQuantity());
                arrayList.add(book.getPrice().multiply(new BigDecimal(orderDetail.getQuantity())));
                orderDetailList.add(arrayList);
            }
            User user=userDao.getOne(order.getUserId());
            orderList.add(orderDetailList);
            orderList.add(user.getName());
            ordersJson.add((JSONArray) JSONArray.toJSON(orderList));
        }
        String ordersString = JSON.toJSONString(ordersJson, SerializerFeature.BrowserCompatible);
        return ordersString;
    }
}
