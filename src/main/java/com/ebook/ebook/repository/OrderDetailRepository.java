package com.ebook.ebook.repository;
import com.ebook.ebook.entity.OrderDetail;
import com.ebook.ebook.entity.OrderDetailPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailPK> {}
