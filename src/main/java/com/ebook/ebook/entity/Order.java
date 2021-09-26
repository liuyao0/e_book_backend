package com.ebook.ebook.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="`order`",schema = "e-book")
@JsonIgnoreProperties(value={"handler","hibernateLazyInitializer","filedHandler"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "orderId"
)
public class Order {
    private Integer orderId;
    private Integer userId;
    private Timestamp orderTime;
    List<OrderDetail> orderDetails=new ArrayList<>();

    @Id
    @Column(name="order_id")
    @GeneratedValue(strategy = IDENTITY)
    public Integer getOrderId() {return orderId;}
    public void setOrderId(Integer orderId) {this.orderId = orderId;}

    @Basic
    @Column(name="user_id")
    public Integer getUserId() {return userId;}
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name="order_time")
    public Timestamp getOrderTime() {return orderTime;}
    public void setOrderTime(Timestamp orderTime) {this.orderTime = orderTime;}

    @OneToMany(targetEntity = OrderDetail.class,cascade = CascadeType.MERGE)
    @JoinColumn(name="order_id")
    public List<OrderDetail> getOrderDetails() {return orderDetails;}
    public void setOrderDetails(List<OrderDetail> orderDetails) {this.orderDetails = orderDetails;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId.equals(order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    public Order() {
    }
}
