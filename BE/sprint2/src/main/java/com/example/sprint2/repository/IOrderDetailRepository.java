package com.example.sprint2.repository;

import com.example.sprint2.model.Order;
import com.example.sprint2.model.OrderDetail;
import com.example.sprint2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    Optional<OrderDetail> findByOrderAndProduct(Order order, Product product);
}
