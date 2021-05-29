package com.ebook.ebook.service;

import com.ebook.ebook.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book getOne(Integer bookId);
}
