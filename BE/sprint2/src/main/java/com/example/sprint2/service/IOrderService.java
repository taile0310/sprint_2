package com.example.sprint2.service;

import com.example.sprint2.dto.OrderDTO;
import com.example.sprint2.model.Order;
import com.example.sprint2.model.User;

public interface IOrderService {
    void createOrder(Long productId, Integer quantity, User user);
}