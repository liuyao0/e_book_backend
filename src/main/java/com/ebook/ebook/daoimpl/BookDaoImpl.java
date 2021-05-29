package com.ebook.ebook.daoimpl;

import com.ebook.ebook.dao.BookDao;
import com.ebook.ebook.entity.Book;
import com.ebook.ebook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findAll(){
        return bookRepository.findAll();
    }
    @Override
    public Book getOne(Integer bookId){
        return bookRepository.getOne(bookId);
    }
}
