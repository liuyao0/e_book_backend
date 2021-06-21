package com.ebook.ebook.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ebook.ebook.entity.Book;
import com.ebook.ebook.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/cart")
    public String getAllBookInCartByUserId(@RequestParam(value = "user_id")Integer userId) {
         return cartService.getAllBookInCartByUserId(userId);

    }

    @RequestMapping("/cartchange")
    public void changeCart(@RequestParam(value="user_id")Integer userId,
                             @RequestParam(value="book_id")Integer bookId,
                             @RequestParam(value="num")Integer num

    )
    {
        cartService.changeCartNum(userId,bookId,num);
    }

    @RequestMapping("/cartadd")
    public String addtoCart(@RequestParam(value="user_id")Integer userId,
                          @RequestParam(value="book_id")Integer bookId,
                          @RequestParam(value="num")Integer num)
    {
       return cartService.addToCart(userId,bookId,num).toString();
    }

    @RequestMapping("/cartdel")
    public void cartItemDel(@RequestParam(value="user_id")Integer userId,
                            @RequestParam(value="book_id")Integer bookId)
    {
        cartService.deleteFromCart(userId,bookId);
    }

}
