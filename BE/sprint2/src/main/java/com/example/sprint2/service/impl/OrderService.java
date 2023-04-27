package com.example.sprint2.service.impl;

import com.example.sprint2.dto.OrderDTO;
import com.example.sprint2.dto.OrderDetailDTO;
import com.example.sprint2.model.*;
import com.example.sprint2.repository.IOrderDetailRepository;
import com.example.sprint2.repository.IOrderRepository;
import com.example.sprint2.repository.IPaymentRepository;
import com.example.sprint2.repository.IProductRepository;
import com.example.sprint2.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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


    @Override
    public void createOrder(Long productId, Integer quantity, User user) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        if (quantity > product.getQuantity()) {
            throw new BadRequestException("Requested quantity is not available");
        }
        Order order = orderRepository.findByUserAndOrderDateIsNull(user).orElseGet(() -> {
            Order newOrder = new Order();
            newOrder.setUser(user);
            newOrder.setOrderDate(new Date());
            newOrder.setTotalAmount(0.0);
            return orderRepository.save(newOrder);
        });
        OrderDetail orderDetail = orderDetailRepository.findByOrderAndProduct(order, product).orElseGet(() -> {
            OrderDetail newOrderDetail = new OrderDetail();
            newOrderDetail.setOrder(order);
            newOrderDetail.setProduct(product);
            newOrderDetail.setQuantity(0);
            newOrderDetail.setPrice(product.getPrice());
            return orderDetailRepository.save(newOrderDetail);
        });
        orderDetail.setQuantity(orderDetail.getQuantity() + quantity);
        orderDetail.setPrice(product.getPrice() * orderDetail.getQuantity());
        orderDetailRepository.save(orderDetail);
        order.setTotalAmount(order.getTotalAmount() + product.getPrice() * quantity);
        orderRepository.save(order);
    }

    public void checkout(User user, Payment payment, String note) {
        Order order = orderRepository.findByUserAndOrderDateIsNull(user).orElseThrow(() -> new ResourceNotFoundException("Order", "user", user.getId()));
        if (order.getOrderDetailList().isEmpty()) {
            throw new BadRequestException("No items in the cart");
        }
        payment.setStatus("PAID");
        payment = paymentRepository.save(payment);
        order.setOrderDate(new Date());
        order.setNote(note);
        order.setPayment(payment);
        orderRepository.save(order);
    }

}

