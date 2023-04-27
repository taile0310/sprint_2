package com.example.sprint2.service;

import com.example.sprint2.dto.OrderDTO;
import com.example.sprint2.model.Order;
import com.example.sprint2.model.Product;
import com.example.sprint2.model.User;

import java.util.List;

public interface IOrderService {
    void addToCart(Order order1, Product product, User user);
    List<Order> findByUserAndOrderDateIsNull(User user);

}