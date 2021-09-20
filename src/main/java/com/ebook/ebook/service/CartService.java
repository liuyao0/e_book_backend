package com.ebook.ebook.service;

import com.ebook.ebook.entity.Book;

import java.util.Map;

public interface CartService {
    void changeCartNum(Integer userId,Integer bookId,Integer cartNum);
    Integer addToCart(Integer userId,Integer bookId,Integer cartNum);
    void deleteFromCart(Integer userID,Integer bookId);
    String getAllBookInCartByUserId(Integer userId);
    String checkBookInCartInventory(Integer userId);
}
