package com.ebook.ebook.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ebook.ebook.entity.Book;
import com.ebook.ebook.entity.Order;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class OrderController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/orderInfo")
    public String getOrder(@RequestParam(value="user_id")Integer user_id)
    {
        List<Order> result = new ArrayList<Order>();
        result=jdbcTemplate.query("SELECT orderDetail.order_id,order_time,orderDetail.book_id,name,isbn,author,press,quantity,realprice, `order`.user_id" +
                " FROM book,`order`,orderDetail " +
                "WHERE book.book_id=orderDetail.book_id AND `order`.order_id=orderDetail.order_id AND `order`.user_id="+user_id.toString()+
                " ORDER BY order_id",
                (rs, rowNum) -> new Order(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("order_time"),
                        rs.getLong("book_id"),
                        rs.getString("isbn"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getString("press"),
                        rs.getDouble("realprice"),
                        rs.getInt("quantity")
                ));
        ArrayList<JSONArray> ordersJson = new ArrayList<JSONArray>();
        Iterator<Order> iter = result.iterator();
        while (iter.hasNext()) {
            Order order = (Order) iter.next();
            ArrayList<Object> arrayList = new ArrayList<Object>();
            arrayList.add(order.getOrderId());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            arrayList.add(df.format(order.getDate()));
            arrayList.add(order.getBook().getBook_id());
            arrayList.add(order.getBook().getName());
            arrayList.add(order.getBook().getAuthor());
            arrayList.add(order.getBook().getPress());
            arrayList.add(order.getBook().getPrice());
            arrayList.add(order.getBook().getIsbn());
            arrayList.add(order.getQuantity());
            ordersJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        String ordersString = JSON.toJSONString(ordersJson, SerializerFeature.BrowserCompatible);
        return ordersString;
    }

    @RequestMapping("/carttoorder")
    public String cartToOrder(@RequestParam(value="user_id") Integer user_id)
    {
        List<Map<String,Object>> cart=new ArrayList<Map<String,Object>>();
        cart=jdbcTemplate.queryForList("select book.book_id,cart_book_num\n" +
                "from book,cart\n" +
                "where book.book_id=cart.book_id and user_id="+user_id.toString());
        if(cart.isEmpty())
            return "-2"; //购物车为空
        Iterator<Map<String,Object>> iter=cart.iterator();
        while(iter.hasNext())
        {
            Map<String,Object> cartItem=(Map<String, Object>) iter.next();

        }
    }



}
