package com.ebook.ebook.daoimpl;

import com.ebook.ebook.dao.BookDao;
import com.ebook.ebook.entity.Book;
import com.ebook.ebook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
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
    @Override
    public void setBookDeletedByBookId(Integer bookId){
        Book book=bookRepository.getOne(bookId);
        book.setDeleted(true);
        bookRepository.save(book);
    }

    @Override
    public Integer changeBook(Book book){
        return bookRepository.save(book).getBookId();
    }
}
