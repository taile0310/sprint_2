package com.example.sprint2.repository;

import com.example.sprint2.model.Order;
import com.example.sprint2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserAndOrderDateIsNull(User user);

    @Query(value = "select  * from orders\n" +
            "join order_details od on orders.id = od.order_id\n" +
            "          join products p on p.id = od.product_id\n" +
            "          where orders.user_id = :user_id",nativeQuery = true)
    List<Order> findCartBy(@Param("user_id") Long user_id);
}
