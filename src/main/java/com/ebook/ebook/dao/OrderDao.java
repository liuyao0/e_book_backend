package com.ebook.ebook.dao;

import com.ebook.ebook.entity.Order;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDao {
   // @Query("FROM Order where userId=?1")
    List<Order> findByUserId(Integer userId);
    Integer cartToOrder(Integer userId);
}
