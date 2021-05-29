package com.ebook.ebook.service;

import com.ebook.ebook.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findByUserId(Integer userId);
    Integer cartToOrder(Integer userId);
}
