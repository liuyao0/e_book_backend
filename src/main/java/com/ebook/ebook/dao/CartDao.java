package com.ebook.ebook.dao;

import com.ebook.ebook.entity.Book;
import com.ebook.ebook.entity.Cart;
import com.ebook.ebook.entity.CartPK;

import java.util.Map;

public interface CartDao {
    Map<Book,Integer> getAllBookByUserId(Integer userId);
    void changeCartNum(Integer userId,Integer bookId,Integer cartNum);
    String addToCart(Integer userId,Integer bookId,Integer cartNum);
    void deleteFromCart(Integer userId,Integer bookId);
    void deleteBookFromCartById(Integer bookId);
    String checkBookInCartInventory(Integer userId);
}
