package com.ebook.ebook.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartPK implements Serializable {
    Integer userId;
    Integer bookId;

    public CartPK(Integer userId, Integer bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public CartPK() {}

    public Integer getUserId() {return userId;}

    public void setUserId(Integer userId) {this.userId = userId;}

    public Integer getBookId() {return bookId;}

    public void setBookId(Integer bookId) {this.bookId = bookId;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartPK cartPK = (CartPK) o;
        return userId.equals(cartPK.userId) && bookId.equals(cartPK.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookId);
    }
}