package com.example.sprint2.repository;

import com.example.sprint2.dto.OrderDetailDTO;
import com.example.sprint2.model.Order;
import com.example.sprint2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public interface IOrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserAndOrderDateIsNull(User user);


    @Query(value = "select p.price as price, p.id as productId, p.product_name as productName, p.image as image," +
            "od.quantity as quantity, od.id as id,  u.address as address, u.phone as phone," +
            "u.name as name, u.email as email, p.quantity as quantityProduct" +
            "  from orders o\n" +
            "  join users u on u.id = o.user_id" +
            "    join order_details od on o.id = od.order_id\n" +
            "           join products p on p.id = od.product_id\n" +
            "    where o.user_id = :userId and od.pay_pal = false order by od.id desc",nativeQuery = true)
    List<OrderDetailDTO> findCartBy(@Param("userId") Long userId);

}
