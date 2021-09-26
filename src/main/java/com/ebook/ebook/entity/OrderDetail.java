package com.ebook.ebook.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;


@Entity
@Table(name="orderdetail")
@JsonIgnoreProperties(value={"handler","hibernateLazyInitializer","filedHandler"})
@JsonIdentityInfo(
        generator =ObjectIdGenerators.PropertyGenerator.class,
        property = "orderDetailPK"
)
public class OrderDetail {
    @EmbeddedId
    @AttributeOverrides(
            {@AttributeOverride(name= "orderId",column = @Column(name="order_id")),
             @AttributeOverride(name= "bookId",column=@Column(name="book_id"))})
    private OrderDetailPK orderDetailPK;
    private BigDecimal realprice;
    private Integer quantity;

    public OrderDetail() {
    }


    public OrderDetailPK getOrderDetailPK() {return this.orderDetailPK;}
    public void setOrderDetailPK(OrderDetailPK orderDetailPK) {this.orderDetailPK=orderDetailPK;}

    @Basic
    @Column(name="realprice")
    public BigDecimal getRealprice() {return this.realprice;}
    public void setRealprice(BigDecimal realprice) {this.realprice=realprice;}
    @Basic
    @Column(name="quantity")
    public Integer getQuantity() {return this.quantity;}
    public void setQuantity(Integer quantity) {this.quantity=quantity;}
    public OrderDetail(OrderDetailPK orderDetailPK, BigDecimal realprice, Integer quantity) {
        this.orderDetailPK = orderDetailPK;
        this.realprice = realprice;
        this.quantity = quantity;
    }
}
