package com.ebook.ebook.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

public class Book {
    private Long book_id;
    private String isbn;
    private String name;
    private String author;
    private String press;
    private Double price;
    private Integer inventory;
    private String description;
    private String image;
    public Book(Long book_id,String isbn,String name,
                String author,String press,Double price,
                Integer inventory,String description,String image)
    {
        this.book_id=book_id;
        this.isbn=isbn;
        this.name=name;
        this.author=author;
        this.press=press;
        this.price=price;
        this.inventory=inventory;
        this.description=description;
        this.image=image;
    }

    public Book(Long book_id,String isbn,String name,
                String author,String press,Double price)
    {
        this.book_id=book_id;
        this.isbn=isbn;
        this.name=name;
        this.author=author;
        this.press=press;
        this.price=price;
    }

    public Long getBook_id(){return this.book_id;}
    public String getIsbn() {return this.isbn;}
    public String getName() {return this.name;}
    public String getAuthor() {return this.author;}
    public String getPress() {return this.press;}
    public Double getPrice() {return this.price;}
    public Integer getInventory() {return this.inventory;}
    public String getDescription() {return this.description;}
    public String getImage() {return this.image;}
}
