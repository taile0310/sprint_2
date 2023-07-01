package com.example.sprint2.repository;

import com.example.sprint2.dto.OrderDetailDTO;
import com.example.sprint2.model.Order;
import com.example.sprint2.model.OrderDetail;
import com.example.sprint2.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    @Modifying
    @Query(value = "update order_details set order_details.pay_pal = true, date_payment = NOW() where order_details.id = :id", nativeQuery = true)
    void updateSttPayPal(@Param("id") Long odId);

    @Query(value = "select p.price as price, p.id as productId, p.product_name as productName, p.image as image," +
            "od.quantity as quantity, od.id as id,  u.address as address, u.phone as phone," +
            "u.name as name, u.email as email, od.date_payment as datePayment" +
            "  from orders o\n" +
            "  join users u on u.id = o.user_id" +
            "    join order_details od on o.id = od.order_id\n" +
            "           join products p on p.id = od.product_id\n" +
            "    where o.user_id = :userId and od.pay_pal = true order by id desc", nativeQuery = true)
    Page<OrderDetailDTO> getListPaymentHistory(@Param("userId") Long userId, Pageable pageable);

    /**
     * function: lấy ra thống kê doanh thu hàng tháng theo năm
     *
     * @param statisticalOfYear
     * @return
     */
    @Query(value = "SELECT\n" +
            "    months.month,\n" +
            "    IFNULL(SUM(order_details_subquery.total_price), 0) AS totalAmount,\n" +
            "    IFNULL(SUM(order_details_subquery.quantityOrdersOfPayPal), 0) AS quantityOrder\n" +
            "FROM\n" +
            "    (SELECT 1 AS month UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4\n" +
            "     UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8\n" +
            "     UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12) AS months\n" +
            "        LEFT JOIN\n" +
            "    (SELECT\n" +
            "         MONTH(order_details.date_payment) AS month,\n" +
            "         SUM(order_details.quantity * products.price) AS total_price,\n" +
            "         COUNT(order_details.pay_pal = true) AS quantityOrdersOfPayPal,\n" +
            "         YEAR(order_details.date_payment) AS year\n" +
            "     FROM\n" +
            "         order_details\n" +
            "             JOIN\n" +
            "         products ON order_details.product_id = products.id\n" +
            "     WHERE\n" +
            "             YEAR(order_details.date_payment) = :year_statistical\n" +
            "     GROUP BY\n" +
            "         MONTH(order_details.date_payment), YEAR(order_details.date_payment)) AS order_details_subquery ON months.month = order_details_subquery.month\n" +
            "GROUP BY\n" +
            "    months.month\n" +
            "ORDER BY\n" +
            "    months.month;\n", nativeQuery = true)
    List<OrderDetailDTO> getListStatistical(@Param("year_statistical") String statisticalOfYear);



}
