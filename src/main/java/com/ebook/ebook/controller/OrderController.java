package com.ebook.ebook.controller;

import com.ebook.ebook.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    MessageSendService messageSendService;

    @Autowired
    JmsTemplate jmsTemplate;

    @RequestMapping("/orderInfo")
    public String getOrderByUserId(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        return orderService.getOrderByUser(httpServletRequest,httpServletResponse);
    }

    @RequestMapping("/allOrder")
    public String getAllOrder()
    {
        return orderService.getAllOrder();
    }

    @RequestMapping("/carttoorder")
    public String cartToOrder(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return messageSendService.sendCartToOrderMessage(httpServletRequest,httpServletResponse);
    }
}
