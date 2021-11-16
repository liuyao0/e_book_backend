package com.ebook.ebook.controller;

import com.ebook.ebook.entity.Book;
import com.ebook.ebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("/")
    public String getAllBook() {
        return bookService.getAllBook();
    }

    @RequestMapping("/bookdetail")
    public String BookDetail(@RequestParam(value="book-id") Integer bookId)
    {
        return bookService.getBookDetail(bookId);
    }

    @RequestMapping("/allBookInManager")
    public String getAllBookInManager(){
        return bookService.getAllBookInManager();
    }

    @RequestMapping("/delbook")
    public Integer DeleteBookById(@RequestParam(value="book-id") Integer bookId){
        bookService.setBookDeletedByBookId(bookId);
        return 0;
    }

    @RequestMapping("/changeBook")
    public Integer changeBook(@RequestBody Map<String,Object> map)
    {
        Book book=new Book(
                (Integer) map.get("book_id"),
                (String) map.get("book_name"),
                (String) map.get("isbn"),
                (String) map.get("author"),
                (String) map.get("press"),
                 new BigDecimal(map.get("price").toString()),
                (String) map.get("description"),
                (Integer) map.get("inventory"),
                (String) map.get("imgUrl"));
        book.setDeleted(false);
        return bookService.changeBook(book);
    }

    @RequestMapping("/getSalesRanking")
    public String getSalesRanking(@RequestParam(value="begin_time") Long beginTime, @RequestParam(value="end_time") Long endTime)
    {
        Timestamp beginTimestamp=new Timestamp(beginTime);
        Timestamp endTimestamp=new Timestamp(endTime);
        return bookService.getSalesRanking(beginTimestamp,endTimestamp);
    }

    @RequestMapping("/search")
    public String searchBooks(@RequestParam(value="key") String key)
    {
        return bookService.searchBook(key);
    }

    @RequestMapping("/searchByLabel")
    public String searchBooksByLabel(@RequestParam(value="label") String label)
    {
        return bookService.searchBooksByLabelName(label);
    }
}





