package com.ebook.ebook.dao;

import com.ebook.ebook.entity.Book;

import java.util.List;

public interface BookDao {
    List<Book> findAll();
    Book getOne(Integer bookId);
    void setBookDeletedByBookId(Integer bookId);
    Integer changeBook(Book book);
    void setBook(Book book);
}
