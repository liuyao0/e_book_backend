package com.ebook.ebook.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ebook.ebook.entity.Book;
import com.ebook.ebook.entity.Order;
import com.ebook.ebook.entity.OrderDetail;
import com.ebook.ebook.entity.User;
import com.ebook.ebook.service.BookService;
import com.ebook.ebook.service.OrderService;
import com.ebook.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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

    @Autowired
    UserService userService;

    @RequestMapping("/orderInfo")
    public String getOrderByUserId(@RequestParam(value="user_id")Integer userId)
    {
        return orderService.getOrderByUserId(userId);
    }

    @RequestMapping("/allOrder")
    public String getAllOrder()
    {
        return orderService.getAllOrder();
    }

    @RequestMapping("/carttoorder")
    public String cartToOrder(@RequestParam(value="user_id") Integer userId) {
        return orderService.cartToOrder(userId).toString();
    }
}