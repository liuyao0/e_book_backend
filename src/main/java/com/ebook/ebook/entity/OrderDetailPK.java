package com.ebook.ebook.entity;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderDetailPK implements Serializable {
    Integer orderId;
    Integer bookId;

    public OrderDetailPK(){
    }

    public OrderDetailPK(Integer orderId, Integer bookId){
        this.orderId = orderId;
        this.bookId = bookId;
    }

    public Integer getOrderId() {return orderId;}

    public void setOrderId(Integer order_id) {this.orderId = order_id;}

    public Integer getBookId() {return bookId;}

    public void setBookId(Integer book_id) {this.bookId = book_id; }

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null||getClass()!=o.getClass()) return false;
        OrderDetailPK that=(OrderDetailPK) o;
        if(this.orderId !=that.orderId) return false;
        if(this.bookId !=that.bookId) return false;
        return true;
    }

    @Override
    public int hashCode(){
        int result=18;
        result=result*31+this.orderId.hashCode();
        result+=result*31+this.bookId.hashCode();
        return result;
    }
}
