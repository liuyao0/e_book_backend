package com.ebook.ebook.repository;
import com.ebook.ebook.entity.Cart;
import com.ebook.ebook.entity.CartPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,CartPK> {
    List<Cart> findByCartPK(CartPK cartPK);
}
