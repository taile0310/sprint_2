package com.example.sprint2.dto;

import com.example.sprint2.model.User;

import java.util.Set;

public class OrderDTO {
    private User user;

    private Double totalAmount;

    private String note;

    private String paymentType;

    private Integer quantity;

    private Set<OrderDetailDTO> orderDetails;

    public User getUser() {
        return user;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Set<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
