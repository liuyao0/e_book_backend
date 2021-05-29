package com.ebook.ebook.serviceimpl;

import com.ebook.ebook.dao.OrderDao;
import com.ebook.ebook.entity.Order;
import com.ebook.ebook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderDao orderDao;
    @Override
    public List<Order> findByUserId(Integer userId){
        return orderDao.findByUserId(userId);
    }

    @Override
    public Integer cartToOrder(Integer userId){
        return orderDao.cartToOrder(userId);
    }

}
