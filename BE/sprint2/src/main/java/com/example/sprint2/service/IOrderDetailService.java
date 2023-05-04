package com.example.sprint2.service;

import com.example.sprint2.model.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    void save(OrderDetail orderDetail);

    OrderDetail findByProductIdAndUserId( Long productId, Long userId);
    List<OrderDetail> orderDetailList();
}
