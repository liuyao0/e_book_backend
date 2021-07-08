package com.ebook.ebook.daoimpl;

import com.ebook.ebook.dao.OrderDao;
import com.ebook.ebook.entity.*;
import com.ebook.ebook.repository.BookRepository;
import com.ebook.ebook.repository.CartRepository;
import com.ebook.ebook.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookRepository bookRepository;
    @Override
    public List<Order> findByUserId(Integer userId){
        return orderRepository.findByUserId(userId);
    }

    @Override
    public String cartToOrder(Integer userId){
        String res="";
        List<Cart> cartList=cartRepository.findAll().stream().filter((Cart cart)->
            cart.getCartPK().getUserId().equals(userId)).collect(Collectors.toList());

        if(cartList.isEmpty())
            return "购物车为空！";

        for(Cart cart:cartList)
        {
            Book book=bookRepository.getOne(cart.getCartPK().getBookId());
            if(book.getInventory()<cart.getCartBookNum())
                res=res+"["+book.getName()+"]";
        }

        if(!res.isEmpty())
        {
            res+="库存不足！";
            return res;
        }

        Iterator<Cart> iterator=cartList.iterator();
        Order order=new Order();

        List<OrderDetail> orderDetailList=new ArrayList<>();
        Timestamp orderTime=new Timestamp(System.currentTimeMillis());

        order.setUserId(userId);
        order.setOrderTime(orderTime);

        orderRepository.save(order);
        Integer orderId=order.getOrderId();
        while(iterator.hasNext()){
            Cart cart=(Cart) iterator.next();
            OrderDetailPK orderDetailPK=new OrderDetailPK(orderId,cart.getCartPK().getBookId());
            Book book=bookRepository.getOne(cart.getCartPK().getBookId());
            OrderDetail orderDetail=new OrderDetail(orderDetailPK,book.getPrice(),cart.getCartBookNum());
            orderDetailList.add(orderDetail);
            cartRepository.delete(cart);
            book.setInventory(book.getInventory()-cart.getCartBookNum());
            bookRepository.save(book);
        }
        order.setOrderDetails(orderDetailList);
        orderRepository.save(order);
        return "";
    }

    @Override
    public List<Order> findAll(){
        return orderRepository.findAll();
    }
}
