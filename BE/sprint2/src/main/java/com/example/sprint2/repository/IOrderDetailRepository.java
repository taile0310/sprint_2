package com.example.sprint2.repository;

import com.example.sprint2.model.Order;
import com.example.sprint2.model.OrderDetail;
import com.example.sprint2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    Optional<OrderDetail> findByOrderAndProduct(Order order, Product product);

    @Query(value = "select od.* from order_details od\n" +
            "            join orders o on od.order_id = o.id\n" +
            "            join users u on u.id = o.user_id\n" +
            "            where  u.id = :id", nativeQuery = true)
    List<OrderDetail> findOrderDetailByUserId(@Param("id") Long userId);

    void deleteOrderDetailByProductIdAndOrderId(Long productId, Long cartId);

    @Query(value = "select * from order_details\n" +
            "         join orders o on o.id = order_details.order_id\n" +
            "         join users u on u.id = o.user_id\n" +
            "         where u.id = :id and order_details.pay_pal = false", nativeQuery = true)
    List<OrderDetail> getListOD(@Param("id") Long id);

    @Query(value = "update order_details set pay_pal = 0 where order_details.id = :id\n", nativeQuery = true)
    OrderDetail updateSttPayPal(@Param("id") Long odId);
}
