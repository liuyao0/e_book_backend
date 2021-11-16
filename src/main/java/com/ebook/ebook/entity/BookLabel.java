package com.ebook.ebook.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="book_label",schema = "e-book")
public class BookLabel implements Serializable {
    @Id
    @Column(name="book_label_id")
    @GeneratedValue(strategy = IDENTITY)
    private Integer bookLabelId;

    @Basic
    @Column(name="book_id")
    private Integer bookId;

    @Basic
    @Column(name="label_name")
    private String labelName;

    public BookLabel() {
    }

    public Integer getBookLabelId() {
        return bookLabelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookLabel bookLabel = (BookLabel) o;
        return Objects.equals(bookLabelId, bookLabel.bookLabelId) && Objects.equals(bookId, bookLabel.bookId) && Objects.equals(labelName, bookLabel.labelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookLabelId, bookId, labelName);
    }

    public void setBookLabelId(Integer bookLabelId) {
        this.bookLabelId = bookLabelId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }


}
