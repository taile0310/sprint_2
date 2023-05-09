package com.example.sprint2.service;


import com.example.sprint2.model.OrderDetail;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrderDetailService {
    void save(OrderDetail orderDetail);
    List<OrderDetail> findOrderDetailByUserId(@Param("id") Long userId);

    List<OrderDetail> orderDetailList();

    OrderDetail findByIdOD(Long id);

    void deleteOrderDetailByProductIdAndOrderId(Long productId, Long orderId);

    void payPal(Long id);

    List<OrderDetail> getListOD(@Param("id") Long id);

    OrderDetail updateSttPayPal(@Param("id") Long odId);


}
