package com.ebook.ebook.daoimpl;

import com.ebook.ebook.dao.BookDao;
import com.ebook.ebook.entity.Book;
import com.ebook.ebook.repository.BookRepository;
import com.ebook.ebook.util.EBookRedisUtil;
import com.ebook.ebook.util.RedisUtil;
import com.ebook.ebook.util.SolrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Repository
@PropertySource("classpath:content.properties")
public class BookDaoImpl implements BookDao {

    @Value("${project-name}")
    String projectName;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EBookRedisUtil eBookRedisUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SolrUtil solrUtil;

    @Override
    public List<Book> findAll(){
        Set<Object> bookIdSet= redisUtil.sGet(projectName+"_bookIdSet");
        List<Book> bookList=new ArrayList<>();
        System.out.println(bookIdSet.size()+" "+bookRepository.count());
        if(bookIdSet.size()!=bookRepository.count())
        {
            eBookRedisUtil.cleanBookIdSet();
            List<Book> allBook= bookRepository.findAll();
            for(Book book:allBook)
            {
                redisUtil.sSet(projectName+"_bookIdSet",book.getBookId());
                Thread t= new Thread(() -> {
                    eBookRedisUtil.setBook(book);
                });
               t.start();
            }
            return bookRepository.findAll();
        }
        else
        {
            for(Object bookIdObject:bookIdSet)
            {
                Integer bookId=(Integer) bookIdObject;
                Book book=eBookRedisUtil.getBookByBookId(bookId);
                if(book==null)
                {
                    book=bookRepository.getOne(bookId);
                }
                bookList.add(book);
            }
        }
        return bookList;
    }
    @Override
    public Book getOne(Integer bookId){
        Book book=eBookRedisUtil.getBookByBookId(bookId);
        if(book==null)
        {
            eBookRedisUtil.setBook(book);
            book=bookRepository.getOne(bookId);
        }
        return book;
    }
    @Override
    public void setBookDeletedByBookId(Integer bookId){
        Book book=bookRepository.getOne(bookId);
        book.setDeleted(true);
        bookRepository.save(book);
        eBookRedisUtil.setBook(book);
    }

    @Override
    public Integer changeBook(Book book){
        Integer bookId=bookRepository.save(book).getBookId();
        book.setBookId(bookId);
        eBookRedisUtil.setBook(book);
        solrUtil.saveBook(book);
        return bookId;
    }

    @Override
    public void setBook(Book book)
    {
        eBookRedisUtil.setBook(book);;
        solrUtil.saveBook(book);
        bookRepository.save(book);
    }
}
