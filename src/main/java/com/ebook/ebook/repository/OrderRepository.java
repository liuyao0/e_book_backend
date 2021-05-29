package com.ebook.ebook.repository;
import com.ebook.ebook.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer>{
    List<Order> findByUserId(Integer userId);
}

