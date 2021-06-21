package com.ebook.ebook.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ebook.ebook.dao.BookDao;
import com.ebook.ebook.entity.Book;
import com.ebook.ebook.service.BookService;
import jdk.jfr.Percentage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public String getAllBook()
    {
        List<Book> books = new ArrayList<Book>();
        books=bookDao.findAll();
        ArrayList<JSONArray> booksJson = new ArrayList<JSONArray>();
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book book = (Book) iter.next();
            if(book.getDeleted())
                continue;
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

    @Override
    public String getBookDetail(Integer bookId)
    {
        ArrayList<JSONArray> booksJson = new ArrayList<JSONArray>();
        Book book=bookDao.getOne(bookId);
        ArrayList<Object> arrayList = new ArrayList<Object>();
        arrayList.add(book.getBookId());
        arrayList.add(book.getName());
        arrayList.add(book.getIsbn());
        arrayList.add(book.getAuthor());
        arrayList.add(book.getPress());
        arrayList.add(book.getPrice());
        arrayList.add(book.getDescription());
        arrayList.add(book.getInventory());
        arrayList.add(book.getImage());
        booksJson.add((JSONArray) JSONArray.toJSON(arrayList));
        String booksString = JSON.toJSONString(booksJson, SerializerFeature.BrowserCompatible);
        return booksString;
    }

    @Override
    public String getAllBookInManager(){
        List<Book> books = new ArrayList<Book>();
        books=bookDao.findAll();
        ArrayList<JSONArray> booksJson = new ArrayList<JSONArray>();
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book book = (Book) iter.next();
            if(book.getDeleted())
                continue;
            ArrayList<Object> arrayList = new ArrayList<Object>();
            arrayList.add(book.getBookId());
            arrayList.add(book.getName());
            arrayList.add(book.getIsbn());
            arrayList.add(book.getAuthor());
            arrayList.add(book.getPress());
            arrayList.add(book.getPrice());
            arrayList.add(book.getInventory());
            arrayList.add(book.getImage());
            booksJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        String booksString = JSON.toJSONString(booksJson, SerializerFeature.BrowserCompatible);
        return booksString;
    }

    @Override
    public void setBookDeletedByBookId(Integer bookId){
        bookDao.setBookDeletedByBookId(bookId);
    }

    @Override
    public Integer changeBook(Book book){
        return bookDao.changeBook(book);
    }
}
