package com.ebook.ebook.daoimpl;

import com.ebook.ebook.dao.BookDao;
import com.ebook.ebook.dao.CartDao;
import com.ebook.ebook.entity.Book;
import com.ebook.ebook.entity.Cart;
import com.ebook.ebook.entity.CartPK;
import com.ebook.ebook.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CartDaoImpl implements CartDao {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookDao bookDao;

    @Override
    public Map<Book,Integer> getAllBookByUserId(Integer userId){
        List<Cart> cartList=cartRepository.findAll();
        Map<Book,Integer> cartBook=new HashMap<>();
        Iterator<Cart> iterator=cartList.iterator();
        while(iterator.hasNext()){
            Cart cart=(Cart) iterator.next();
            if(cart.getCartPK().getUserId().equals(userId)){
                Book book=bookDao.getOne(cart.getCartPK().getBookId());
                cartBook.put(book,cart.getCartBookNum());
            }
        }
        return cartBook;
    }

    @Override
    public void changeCartNum(Integer userId,Integer bookId,Integer cartNum){
        CartPK cartPK=new CartPK(userId,bookId);
        Cart cart=null;
        if(cartRepository.findByCartPK(cartPK).iterator().hasNext())
            cart=cartRepository.findByCartPK(cartPK).iterator().next();
        else
            return;
        cart.setCartBookNum(cartNum);
        cartRepository.save(cart);
    }

    @Override
    public String addToCart(Integer userId,Integer bookId,Integer cartNum){
        CartPK cartPK=new CartPK(userId,bookId);
        Cart cart=new Cart(cartPK,cartNum);
        if(cartRepository.findByCartPK(cartPK).iterator().hasNext())
            return "该书籍已在购物车中！";
        else
            cartRepository.save(cart);
        return "";
    }

    @Override
    public void deleteFromCart(Integer userId,Integer bookId){
        CartPK cartPK=new CartPK(userId,bookId);
        Cart cart=null;
        if(cartRepository.findByCartPK(cartPK).iterator().hasNext())
        {
            cart=cartRepository.findByCartPK(cartPK).iterator().next();
            cartRepository.delete(cart);
        }
    }
    @Override
    public void deleteBookFromCartById(Integer bookId)
    {
        List<Cart> cartList=cartRepository.findAll();
        for(Cart cart :cartList)
        {
            if(cart.getCartPK().getBookId()==bookId)
                cartRepository.delete(cart);
        }
    }

    @Override
    public String checkBookInCartInventory(Integer userId)
    {
        String res="";
        List<Cart> cartList=cartRepository.findAll().stream().filter((Cart cart)->
                cart.getCartPK().getUserId().equals(userId)).collect(Collectors.toList());

        if(cartList.isEmpty())
            return "购物车为空！";

        for(Cart cart:cartList)
        {
            Book book=bookDao.getOne(cart.getCartPK().getBookId());
            if(book.getInventory()<cart.getCartBookNum())
                res=res+"["+book.getName()+"]";
        }

        if(!res.isEmpty())
        {
            res+="库存不足！";
            return res;
        }
        return "";
    }
}
