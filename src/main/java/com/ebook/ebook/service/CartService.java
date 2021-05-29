package com.ebook.ebook.service;

import com.ebook.ebook.entity.Book;

import java.util.Map;

public interface CartService {
    Map<Book,Integer> findByUserId(Integer UserId);
    void changeCartNum(Integer userId,Integer bookId,Integer cartNum);
    Integer addToCart(Integer userId,Integer bookId,Integer cartNum);
    void deleteFromCart(Integer userID,Integer bookId);
}
