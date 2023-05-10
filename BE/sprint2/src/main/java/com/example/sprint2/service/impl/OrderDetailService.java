package com.example.sprint2.service.impl;

import com.example.sprint2.dto.OrderDetailDTO;
import com.example.sprint2.model.OrderDetail;
import com.example.sprint2.model.Product;
import com.example.sprint2.repository.IOrderDetailRepository;
import com.example.sprint2.repository.IProductRepository;
import com.example.sprint2.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Autowired
    private IProductRepository productRepository;

    @Override
    public void save(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<OrderDetail> findOrderDetailByUserId(Long userId) {
        return orderDetailRepository.findOrderDetailByUserId(userId);
    }


    @Override
    public List<OrderDetail> orderDetailList() {
        return orderDetailRepository.findAll();
    }

    @Override
    public OrderDetail findByIdOD(Long id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteOrderDetailByProductIdAndOrderId(Long productId, Long orderId) {
        orderDetailRepository.deleteOrderDetailByProductIdAndOrderId(productId, orderId);
    }

    @Override
    public void payPal(Long id) {
        List<OrderDetail> orderDetailDTOS = orderDetailRepository.getListOD(id);
        for (int i = 0; i < orderDetailDTOS.size(); i++) {
            int quantityOD = orderDetailDTOS.get(i).getQuantity();
            Product product = productRepository.findById(orderDetailDTOS.get(i).getProduct().getId()).orElse(null);
            int paid = product.getQuantity() - quantityOD;
            product.setQuantity(paid);
            productRepository.save(product);
        }
    }

    @Override
    public List<OrderDetail> getListOD(Long id) {
        return orderDetailRepository.getListOD(id);
    }

    @Override
    public void updateSttPayPal(Long odId) {
         orderDetailRepository.updateSttPayPal(odId);
    }

    @Override
    public Page<OrderDetailDTO> getListPaymentHistory(Long userId, Pageable pageable) {
        return orderDetailRepository.getListPaymentHistory(userId, pageable);
    }


}

