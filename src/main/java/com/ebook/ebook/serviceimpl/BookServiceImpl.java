package com.ebook.ebook.serviceimpl;

import com.ebook.ebook.dao.BookDao;
import com.ebook.ebook.entity.Book;
import com.ebook.ebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public List<Book> findAll(){
        return bookDao.findAll();
    }

    @Override
    public Book getOne(Integer bookId){
        return bookDao.getOne(bookId);
    }
}
