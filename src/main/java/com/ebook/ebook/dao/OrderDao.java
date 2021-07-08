package com.ebook.ebook.dao;

import com.ebook.ebook.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDao {
    List<Order> findByUserId(Integer userId);
    String cartToOrder(Integer userId);
    List<Order> findAll();
}
