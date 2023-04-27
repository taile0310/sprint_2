package com.example.sprint2.service.impl;


import com.example.sprint2.model.*;
import com.example.sprint2.repository.IOrderDetailRepository;
import com.example.sprint2.repository.IOrderRepository;
import com.example.sprint2.repository.IPaymentRepository;
import com.example.sprint2.repository.IProductRepository;
import com.example.sprint2.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService implements IOrderService {
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private IOrderDetailRepository orderDetailRepository;



    public void addToCart(Order order1, Product product, User user){
        Order order = new Order(product, order1.getQuantity(), user);
        orderRepository.save(order);
    }

    @Override
    public List<Order> findByUserAndOrderDateIsNull(User user) {
        return orderRepository.findByUserAndOrderDateIsNull(user);
    }


}

