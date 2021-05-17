package com.ebook.ebook.controller;
import com.ebook.ebook.entity.Book;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.*;
import java.util.Map;

@CrossOrigin
@RestController
public class BookController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/")
    public String AllBook() {
        List<Book> result = new ArrayList<Book>();

        result = jdbcTemplate.query(
                "SELECT * FROM book ORDER BY book_id",
                (rs, rowNum) -> new Book(rs.getLong("book_id"),
                        rs.getString("isbn"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getString("press"),
                        rs.getDouble("price"),
                        rs.getInt("inventory"),
                        rs.getString("description"),
                        rs.getString("image"))
        );

        ArrayList<JSONArray> booksJson = new ArrayList<JSONArray>();
        Iterator<Book> iter = result.iterator();
        while (iter.hasNext()) {
            Book book = (Book) iter.next();
            ArrayList<Object> arrayList = new ArrayList<Object>();
            arrayList.add(book.getBook_id());
            arrayList.add(book.getName());
            arrayList.add(book.getAuthor());
            arrayList.add(book.getPress());
            arrayList.add(book.getPrice());
            arrayList.add(book.getIsbn());
            arrayList.add(book.getInventory());
            arrayList.add(book.getImage());
            arrayList.add(book.getDescription());
            booksJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        String booksString = JSON.toJSONString(booksJson, SerializerFeature.BrowserCompatible);
        return booksString;
    }

    @RequestMapping("/bookdetail")
    public String BookDetail(@RequestParam(value="book-id") Long bookId)
    {
        List<Book> result = new ArrayList<Book>();
        result = jdbcTemplate.query(
                "SELECT * FROM book WHERE book_id="+bookId.toString()+" ORDER BY book_id ",
                (rs, rowNum) -> new Book(
                        rs.getLong("book_id"),
                        rs.getString("isbn"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getString("press"),
                        rs.getDouble("price"),
                        rs.getInt("inventory"),
                        rs.getString("description"),
                        rs.getString("image"))
        );
        ArrayList<JSONArray> booksJson = new ArrayList<JSONArray>();
        Iterator<Book> iter = result.iterator();
        while (iter.hasNext()) {
            Book book = (Book) iter.next();
            ArrayList<Object> arrayList = new ArrayList<Object>();
            arrayList.add(book.getBook_id());
            arrayList.add(book.getName());
            arrayList.add(book.getAuthor());
            arrayList.add(book.getPress());
            arrayList.add(book.getPrice());
            arrayList.add(book.getIsbn());
            arrayList.add(book.getInventory());
            arrayList.add(book.getImage());
            arrayList.add(book.getDescription());
            booksJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        String booksString = JSON.toJSONString(booksJson, SerializerFeature.BrowserCompatible);
        return booksString;
    }
}





