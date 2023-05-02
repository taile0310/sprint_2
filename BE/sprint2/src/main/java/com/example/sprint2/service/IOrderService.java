package com.example.sprint2.service;


import com.example.sprint2.model.Order;
import com.example.sprint2.model.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrderService {
    List<Order> findByUserAndOrderDateIsNull(User user);
    Order findById(Long id);

    List<Order> findCartBy(Long user_id);

    void createCart(Order order);


}