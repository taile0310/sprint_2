package com.example.sprint2.repository;

import com.example.sprint2.dto.OrderDetailDTO;
import com.example.sprint2.model.Order;
import com.example.sprint2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserAndOrderDateIsNull(User user);

    @Query(value = "select p.price as price, p.id as productId, p.product_name as productName, p.image as image" +
            "  from orders o\n" +
            "    join order_details od on o.id = od.order_id\n" +
            "           join products p on p.id = od.product_id\n" +
            "    where o.user_id = :userId",nativeQuery = true)
    List<OrderDetailDTO> findCartBy(@Param("userId") Long userId);

    @Modifying
    @Query(value = "UPDATE order_details od\n" +
            "JOIN orders o ON od.order_id = o.id\n" +
            "SET od.quantity = ?2\n" +
            "WHERE od.id = ?1",nativeQuery = true)
    void changeQuantity(Long orderDetail, int quantity);
}
