package com.ebook.ebook.controller;
import com.ebook.ebook.entity.Book;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ebook.ebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.*;

@CrossOrigin
@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @RequestMapping("/")
    public String AllBook() {
        List<Book> books = new ArrayList<Book>();
        books=bookService.findAll();
        ArrayList<JSONArray> booksJson = new ArrayList<JSONArray>();
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book book = (Book) iter.next();
            ArrayList<Object> arrayList = new ArrayList<Object>();
            arrayList.add(book.getBookId());
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
    public String BookDetail(@RequestParam(value="book-id") Integer bookId)
    {
        ArrayList<JSONArray> booksJson = new ArrayList<JSONArray>();
        Book book=bookService.getOne(bookId);
        ArrayList<Object> arrayList = new ArrayList<Object>();
        arrayList.add(book.getBookId());
        arrayList.add(book.getName());
        arrayList.add(book.getAuthor());
        arrayList.add(book.getPress());
        arrayList.add(book.getPrice());
        arrayList.add(book.getIsbn());
        arrayList.add(book.getInventory());
        arrayList.add(book.getImage());
        arrayList.add(book.getDescription());
        booksJson.add((JSONArray) JSONArray.toJSON(arrayList));
        String booksString = JSON.toJSONString(booksJson, SerializerFeature.BrowserCompatible);
        return booksString;
    }
}





