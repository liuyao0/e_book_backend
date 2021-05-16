package com.ebook.ebook.entity;

import java.util.Date;

public class Order {
    private Integer orderId;
    private Integer userId;
    private Date orderTime;
    private Integer quantity;
    private Book book;

    public Integer getOrderId() {return this.orderId;}
    public Integer getUserId() {return this.userId;}
    public Date getDate() { return this.orderTime;}
    public Integer getQuantity() {return this.quantity;}
    public Book getBook() {return this.book;}

    public void setOrderId(Integer orderId) {this.orderId=orderId;}
    public void setUserId(Integer userId) {this.userId=userId;}
    public void setDate(Date orderTime) {this.orderTime=orderTime;}
    public void setQuantity(Integer Quantity) {this.quantity=quantity;}
    public void setBook(Book book) {this.book=book;}

    public Order(Integer orderId, Integer userId, Date orderTime,
                 Long book_id, String isbn, String name,
                 String author, String press,Double price,
                 Integer quantity)
    {
        this.orderId=orderId;
        this.userId=userId;
        this.orderTime=orderTime;
        this.quantity=quantity;
        this.book=new Book(book_id,isbn,name,author,press,price);
    }
}
