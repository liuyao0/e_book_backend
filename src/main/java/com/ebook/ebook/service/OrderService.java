package com.ebook.ebook.service;

import com.ebook.ebook.entity.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface OrderService {
    String getOrderByUser(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse);
    void cartToOrder(Integer userId);
    String getAllOrder();
}
