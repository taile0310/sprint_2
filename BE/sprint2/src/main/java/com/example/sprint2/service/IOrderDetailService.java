package com.example.sprint2.service;


import com.example.sprint2.dto.OrderDetailDTO;
import com.example.sprint2.model.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    void updateSttPayPal( Long odId);

    Page<OrderDetailDTO> getListPaymentHistory(@Param("userId") Long userId, Pageable pageable);


    List<OrderDetailDTO> getListStatistical(String statisticalOfYear);


}
