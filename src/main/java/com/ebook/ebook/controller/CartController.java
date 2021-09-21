package com.ebook.ebook.controller;

import com.ebook.ebook.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/cart")
    public String getAllBookInCartByUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
         return cartService.getAllBookInCartByUserId(httpServletRequest,httpServletResponse);

    }

    @RequestMapping("/cartchange")
    public void changeCart(@RequestParam(value="book_id")Integer bookId,
                           @RequestParam(value="num")Integer num,
                           HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse

    )
    {
        cartService.changeCartNum(bookId,num,httpServletRequest,httpServletResponse);
    }

    @RequestMapping("/cartadd")
    public String addtoCart(@RequestParam(value="book_id")Integer bookId,
                            @RequestParam(value="num")Integer num,
                            HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse)
    {
       return cartService.addToCart(bookId,num,httpServletRequest,httpServletResponse).toString();
    }

    @RequestMapping("/cartdel")
    public void cartItemDel(@RequestParam(value="book_id")Integer bookId,
                            HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse)
    {
        cartService.deleteFromCart(bookId,httpServletRequest,httpServletResponse);
    }

}
