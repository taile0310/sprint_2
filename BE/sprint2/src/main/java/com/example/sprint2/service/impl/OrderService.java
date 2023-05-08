package com.example.sprint2.service.impl;


import com.example.sprint2.dto.OrderDetailDTO;
import com.example.sprint2.model.*;
import com.example.sprint2.repository.*;
import com.example.sprint2.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class OrderService implements IOrderService {
    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public List<Order> findByUserAndOrderDateIsNull(User user) {
        return orderRepository.findByUserAndOrderDateIsNull(user);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<OrderDetailDTO> findCartBy(Long userId) {
        return orderRepository.findCartBy(userId);
    }

    @Override
    public void createCart(Order order) {
        orderRepository.save(order);
    }




}

