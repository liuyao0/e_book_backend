package com.ebook.ebook.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Document(collection = "e_book")
public class Comment implements Serializable {

    @Indexed
    @Field("book_id")
    private Integer bookId;

    @Field("user_id")
    private Integer userId;

    @Field("time")
    private Date time;

    @Field("content")
    private String content;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(bookId, comment.bookId) && Objects.equals(userId, comment.userId) && Objects.equals(time, comment.time) && Objects.equals(content, comment.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, userId, time, content);
    }
}
