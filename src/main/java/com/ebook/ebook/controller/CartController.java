package com.ebook.ebook.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/cart")
    public String  cartData(@RequestParam(value = "user_id")Integer user_id) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        result = jdbcTemplate.queryForList(
                "select book.book_id,book.isbn,book.image,book.name,book.author,book.press,book.price,cart_book_num " +
                        "from book,cart " +
                        "where user_id=" + user_id.toString() + " and book.book_id=cart.book_id;"
        );

        Iterator<Map<String, Object>> iter = result.iterator();
        ArrayList<JSONArray> booksJson = new ArrayList<JSONArray>();
        while (iter.hasNext()) {
            Map<String, Object> res = (Map<String, Object>) iter.next();
            ArrayList<Object> arrayList = new ArrayList<Object>();
            arrayList.add(res.get("book_id"));
            arrayList.add(res.get("isbn"));
            arrayList.add(res.get("image"));
            arrayList.add(res.get("name"));
            arrayList.add(res.get("author"));
            arrayList.add(res.get("press"));
            arrayList.add(res.get("price"));
            arrayList.add(res.get("cart_book_num"));
            booksJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        String booksString = JSON.toJSONString(booksJson, SerializerFeature.BrowserCompatible);
        return booksString;
    }

    @RequestMapping("/cartchange")
    public void changeCart(@RequestParam(value="user_id")Integer user_id,
                             @RequestParam(value="book_id")Integer book_id,
                             @RequestParam(value="num")Integer num


    )
    {
        jdbcTemplate.update(
                "UPDATE cart SET cart_book_num = "+num.toString()+" " +
                        "WHERE book_id = "+book_id.toString()+" and user_id="+user_id.toString()
        );
    }

    @RequestMapping("/cartadd")
    public String addtoCart(@RequestParam(value="user_id")Integer user_id,
                          @RequestParam(value="book_id")Integer book_id,
                          @RequestParam(value="num")Integer num)
    {
        List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
        result=jdbcTemplate.queryForList("select book.book_id from" +
                " book,cart " +
                "where user_id=" + user_id.toString() + " and book.book_id=cart.book_id and book.book_id="+book_id.toString());
        if(!result.isEmpty())
            return "-1";
        jdbcTemplate.update("INSERT INTO cart (user_id, book_id,cart_book_num) VALUES ("+user_id.toString()+","+book_id.toString()+","+num.toString()+")");
        return  "0";
    }


}
