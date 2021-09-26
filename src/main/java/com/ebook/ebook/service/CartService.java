package com.ebook.ebook.service;

import com.ebook.ebook.entity.Book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface CartService {
    void changeCartNum(Integer bookId, Integer cartNum, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
    String addToCart(Integer bookId,Integer cartNum,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse);
    void deleteFromCart(Integer bookId,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse);
    String getAllBookInCartByUserId(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse);
    String checkBookInCartInventory(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse);
}
