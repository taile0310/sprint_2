package com.example.sprint2.service.impl;


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

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IPaymentRepository paymentRepository;

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
    public List<Order> findCartBy(Long user_id) {
        return orderRepository.findCartBy(user_id);
    }

    @Override
    public void createCart(Order order) {
        orderRepository.save(order);
    }

}

