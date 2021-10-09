package com.ebook.ebook.entity;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="book",schema = "e-book")
@JsonIgnoreProperties(value={"handler","hibernateLazyInitializer","filedHandler"})
@JsonIdentityInfo(
        generator =ObjectIdGenerators.PropertyGenerator.class,
        property = "bookId"
)
public class Book {
    private Integer bookId;
    private String isbn;
    private String name;
    private String author;
    private String press;
    private BigDecimal price;
    private Integer inventory;
    private String description;
    private String image;
    private Boolean deleted;

    @Id
    @Column(name="book_id")
    @GeneratedValue(strategy = IDENTITY)
    public Integer getBookId(){return this.bookId;}
    public void setBookId(Integer bookId){this.bookId = bookId;}

    @Basic
    @Column(name="isbn")
    public String getIsbn() {return this.isbn;}
    public void setIsbn(String isbn) {this.isbn=isbn;}

    @Basic
    @Column(name="name")
    public String getName() {return this.name;}
    public void setName(String name) {this.name=name;}

    @Basic
    @Column(name="author")
    public String getAuthor() {return this.author;}
    public void setAuthor(String author){this.author=author;}

    @Basic
    @Column(name="press")
    public String getPress() {return this.press;}
    public void setPress(String press) {this.press=press;}

    @Basic
    @Column(name="price")
    public BigDecimal getPrice() {return this.price;}
    public void setPrice(BigDecimal price) {this.price=price;}

    @Basic
    @Column(name="inventory")
    public Integer getInventory() {return this.inventory;}
    public void setInventory(Integer inventory){this.inventory=inventory;}

    @Basic
    @Column(name="description")
    public String getDescription() {return this.description;}
    public void setDescription(String description){this.description=description;}

    @Basic
    @Column(name="image")
    public String getImage() {return this.image;}
    public void setImage(String image) {this.image=image;}

    @Basic
    @Column(name="book_deleted")
    public Boolean getDeleted() {return deleted;}
    public void setDeleted(Boolean deleted) {this.deleted = deleted;}

    public Book(Integer bookId,
                String name,
                String isbn,
                String author,
                String press,
                BigDecimal price,
                String description,
                Integer inventory,
                String image)
    {
        this.bookId = bookId;
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.press = press;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.image = image;
    }

    public Book(Integer bookId, String isbn, String name, String author, String press, BigDecimal price, Integer inventory, String description, String image, Boolean deleted) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.press = press;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.image = image;
        this.deleted = deleted;
    }

    public Book()
    {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(bookId, book.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId);
    }


}
