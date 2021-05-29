package com.ebook.ebook.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ebook.ebook.entity.Book;
import com.ebook.ebook.entity.Order;
import com.ebook.ebook.entity.OrderDetail;
import com.ebook.ebook.service.BookService;
import com.ebook.ebook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@CrossOrigin
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    BookService bookService;

    @RequestMapping("/orderInfo")
    public String getOrder(@RequestParam(value="user_id")Integer userId)
    {
        List<Order> orders=orderService.findByUserId(userId);
        ArrayList<JSONArray> ordersJson = new ArrayList<JSONArray>();
        Iterator<Order> iter = orders.iterator();
        while (iter.hasNext()) {
            Order order=(Order) iter.next();
            Iterator<OrderDetail> it=order.getOrderDetails().iterator();
            while(it.hasNext()){
                ArrayList<Object> arrayList=new ArrayList<>();
                OrderDetail orderDetail=(OrderDetail) it.next();
                Book book=bookService.getOne(orderDetail.getOrderDetailPK().getBookId());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                arrayList.add(order.getOrderId());
                arrayList.add(df.format(order.getOrderTime()));
                arrayList.add(book.getName());
                arrayList.add(book.getAuthor());
                arrayList.add(book.getPress());
                arrayList.add(book.getPrice());
                arrayList.add(orderDetail.getQuantity());
                ordersJson.add((JSONArray) JSONArray.toJSON(arrayList));
            }
        }
        String ordersString = JSON.toJSONString(ordersJson, SerializerFeature.BrowserCompatible);
        return ordersString;
    }

    @RequestMapping("/carttoorder")
    public String cartToOrder(@RequestParam(value="user_id") Integer userId) {
        return orderService.cartToOrder(userId).toString();
    }
}