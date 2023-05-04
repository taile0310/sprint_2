package com.example.sprint2.service;


import com.example.sprint2.dto.OrderDetailDTO;
import com.example.sprint2.model.Order;
import com.example.sprint2.model.User;

import java.util.List;

public interface IOrderService {
    List<Order> findByUserAndOrderDateIsNull(User user);
    Order findById(Long id);

    List<OrderDetailDTO> findCartBy(Long userId);

    void createCart(Order order);


    boolean changeQuantity(Long id, int quantity);
}