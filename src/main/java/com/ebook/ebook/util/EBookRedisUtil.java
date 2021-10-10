package com.ebook.ebook.util;

import com.ebook.ebook.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@PropertySource("classpath:content.properties")
public class EBookRedisUtil {
    @Value("${project-name}")
    String projectName;

    @Autowired
    RedisUtil redisUtil;

    public String getRedisKeyByBookId(Integer bookId)
    {
        return projectName+"_book"+bookId;
    }

    public void cleanBookIdSet()
    {
        redisUtil.del(projectName+"_bookIdSet");
    }

    public void setBook(Book book)
    {
        redisUtil.set(getRedisKeyByBookId(book.getBookId())+":isbn", book.getIsbn());
        redisUtil.set(getRedisKeyByBookId(book.getBookId())+":name",book.getName());
        redisUtil.set(getRedisKeyByBookId(book.getBookId())+":author",book.getAuthor());
        redisUtil.set(getRedisKeyByBookId(book.getBookId())+":press",book.getPress());
        redisUtil.set(getRedisKeyByBookId(book.getBookId())+":price",book.getPrice());
        redisUtil.set(getRedisKeyByBookId(book.getBookId())+":inventory",book.getInventory());
        redisUtil.set(getRedisKeyByBookId(book.getBookId())+":description",book.getDescription());
        redisUtil.set(getRedisKeyByBookId(book.getBookId())+":image",book.getImage());
        redisUtil.set(getRedisKeyByBookId(book.getBookId())+":book_deleted",book.getDeleted());

        redisUtil.set(getRedisKeyByBookId(book.getBookId())+":book_id", book.getBookId());

        redisUtil.sSet(projectName+"_bookIdSet",book.getBookId());
    }

    public Book getBookByBookId(Integer bookId)
    {
        if(!redisUtil.hasKey(getRedisKeyByBookId(bookId)+":book_id"))
            return null;
        return new Book(
                bookId,
                (String) redisUtil.get(getRedisKeyByBookId(bookId)+":isbn"),
                (String) redisUtil.get(getRedisKeyByBookId(bookId)+":name"),
                (String) redisUtil.get(getRedisKeyByBookId(bookId)+":author"),
                (String) redisUtil.get(getRedisKeyByBookId(bookId)+":press"),
                (BigDecimal) redisUtil.get(getRedisKeyByBookId(bookId)+":price"),
                (Integer) redisUtil.get(getRedisKeyByBookId(bookId)+":inventory"),
                (String) redisUtil.get(getRedisKeyByBookId(bookId)+":description"),
                (String) redisUtil.get(getRedisKeyByBookId(bookId)+":image"),
                (Boolean) redisUtil.get(getRedisKeyByBookId(bookId)+":book_deleted")
        );
    }
}
