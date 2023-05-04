package com.example.sprint2.repository;

import com.example.sprint2.model.Order;
import com.example.sprint2.model.OrderDetail;
import com.example.sprint2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    Optional<OrderDetail> findByOrderAndProduct(Order order, Product product);
    @Query(value = "select od.* from order_details od join orders o on od.order_id = o.id where od.product_id = ?1 and o.user_id = ?2",nativeQuery = true)
    OrderDetail findByProductIdAndUserId( Long productId, Long userId);
}
