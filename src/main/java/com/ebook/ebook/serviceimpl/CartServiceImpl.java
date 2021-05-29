package com.ebook.ebook.serviceimpl;

import com.ebook.ebook.dao.CartDao;
import com.ebook.ebook.entity.Book;
import com.ebook.ebook.entity.Cart;
import com.ebook.ebook.entity.CartPK;
import com.ebook.ebook.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao cartDao;

    @Override
    public Map<Book,Integer> findByUserId(Integer userId){
        return cartDao.findByUserId(userId);
    }

    @Override
    public void changeCartNum(Integer userId,Integer bookId,Integer cartNum){
        cartDao.changeCartNum(userId,bookId,cartNum);
    }

    @Override
    public Integer addToCart(Integer userId,Integer bookId,Integer cartNum){
        return cartDao.addToCart(userId,bookId,cartNum);
    }

    @Override
    public void deleteFromCart(Integer userId,Integer bookId){
        cartDao.deleteFromCart(userId,bookId);
    }
}
