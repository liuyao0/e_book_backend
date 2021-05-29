package com.ebook.ebook.daoimpl;

import com.ebook.ebook.dao.CartDao;
import com.ebook.ebook.entity.Book;
import com.ebook.ebook.entity.Cart;
import com.ebook.ebook.entity.CartPK;
import com.ebook.ebook.repository.BookRepository;
import com.ebook.ebook.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
public class CartDaoImpl implements CartDao {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Map<Book,Integer> findByUserId(Integer userId){
        List<Cart> cartList=cartRepository.findAll();
        Map<Book,Integer> cartBook=new HashMap<>();
        Iterator<Cart> iterator=cartList.iterator();
        while(iterator.hasNext()){
            Cart cart=(Cart) iterator.next();
            if(cart.getCartPK().getUserId().equals(userId)){
                Book book=bookRepository.getOne(cart.getCartPK().getBookId());
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
    public Integer addToCart(Integer userId,Integer bookId,Integer cartNum){
        CartPK cartPK=new CartPK(userId,bookId);
        Cart cart=new Cart(cartPK,cartNum);
        if(cartRepository.findByCartPK(cartPK).iterator().hasNext())
            return -1;
        else
            cartRepository.save(cart);
        return 0;
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
}
