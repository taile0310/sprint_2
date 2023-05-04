package com.example.sprint2.service.impl;


import com.example.sprint2.dto.OrderDetailDTO;
import com.example.sprint2.model.*;
import com.example.sprint2.repository.*;
import com.example.sprint2.service.IOrderService;
import org.hibernate.type.LocalDateTimeType;
import org.hibernate.type.LocalDateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class OrderService implements IOrderService {
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IProductRepository productRepository;


    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Autowired
    private IUserRepository userRepository;

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

    @Override
    public boolean changeQuantity(Long id, int quantity) {
        orderRepository.changeQuantity(id, quantity);
        return false;
    }

}

