package com.example.sprint2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User user;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "note")
    private String note;


    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private Set<OrderDetail> orderDetailList;



    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Set<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(Set<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
