package com.ebook.ebook.service;

import com.ebook.ebook.entity.Book;

import java.sql.Timestamp;
import java.util.List;

public interface BookService {
    String getAllBookInManager();
    String getAllBook();
    String getBookDetail(Integer bookId);
    void setBookDeletedByBookId(Integer bookId);
    Integer changeBook(Book book);
    String getSalesRanking(Timestamp beginTime,Timestamp endTime);
    String searchBook(String string);
}
