package com.example.sprint2.service.impl;

import com.example.sprint2.model.OrderDetail;
import com.example.sprint2.repository.IOrderDetailRepository;
import com.example.sprint2.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Override
    public void save(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);

    }

    @Override
    public OrderDetail findByProductIdAndUserId(Long productId, Long userId) {
        return orderDetailRepository.findByProductIdAndUserId(productId, userId);
    }

    @Override
    public List<OrderDetail> orderDetailList() {
        return orderDetailRepository.findAll();
    }
}

