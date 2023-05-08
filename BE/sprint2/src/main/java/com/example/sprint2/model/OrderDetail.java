package com.example.sprint2.model;

import javax.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "pay_pal")
    private boolean payPal;



    public OrderDetail() {
    }


    public OrderDetail(Integer quantity, Product product,Order order) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public boolean isPayPal() {
        return payPal;
    }

    public void setPayPal(boolean payPal) {
        this.payPal = payPal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
