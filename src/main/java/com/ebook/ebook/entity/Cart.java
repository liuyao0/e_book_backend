package com.ebook.ebook.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="cart")
@JsonIgnoreProperties(value={"handler","hibernateLazyInitializer","filedHandler"})
@JsonIdentityInfo(
        generator =ObjectIdGenerators.PropertyGenerator.class,
        property = "cartPK"
)
public
class Cart{
    @EmbeddedId
    @AttributeOverrides(
            {@AttributeOverride(name="userId",column =@Column(name="user_id")),
             @AttributeOverride(name="bookId",column = @Column(name="book_id"))}
    )
    private CartPK cartPK;
    private Integer cartBookNum;

    public Cart(CartPK cartPK, Integer cartBookNum) {
        this.cartPK = cartPK;
        this.cartBookNum = cartBookNum;
    }

    public Cart() {
    }

    public CartPK getCartPK() {return cartPK;}
    public void setCartPK(CartPK cartPK) {this.cartPK = cartPK; }

    @Basic
    @Column(name="cart_book_num")
    public Integer getCartBookNum() {return cartBookNum;}
    public void setCartBookNum(Integer cartBookNum) {this.cartBookNum = cartBookNum;}

}
