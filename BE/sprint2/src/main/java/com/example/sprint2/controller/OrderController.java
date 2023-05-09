package com.example.sprint2.controller;

import com.example.sprint2.dto.OrderDetailDTO;
import com.example.sprint2.model.*;
import com.example.sprint2.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/public")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;


    @GetMapping("/cart/{id}")
    public ResponseEntity<?> getListOrder(@PathVariable("id") Long id) {
        List<OrderDetailDTO> order = orderService.findCartBy(id);
        if (order.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/cart-detail")
    public ResponseEntity<?> getListOrderDetail() {
        List<OrderDetail> orderDetails = orderDetailService.orderDetailList();
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }


    @GetMapping("/changeQuantity")
    public ResponseEntity<?> changeQuantityOD(@RequestParam Long orderDetailId, @RequestParam Integer quantity) {
        OrderDetail orderDetail = orderDetailService.findByIdOD(orderDetailId);
        if (orderDetail == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        orderDetail.setQuantity(quantity);
        orderDetailService.save(orderDetail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // add in shop
    @GetMapping("/addCart")
    public ResponseEntity<?> addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer quantity) {

        List<OrderDetailDTO> orderDetailDTOList = orderService.findCartBy(userId);
        int count = 0;
        OrderDetail orderDetail = null;
        for (OrderDetailDTO detailDTO : orderDetailDTOList) {
            if (detailDTO.getProductId().equals(productId)) {
                orderDetail = this.orderDetailService.findByIdOD(detailDTO.getId());
                orderDetail.setQuantity(orderDetail.getQuantity() + 1);
                count++;

            }
        }
        if (count == 1) {
            this.orderDetailService.save(orderDetail);
            return new ResponseEntity<>(HttpStatus.OK);
        } else if (count == 0) {
            Order order = new Order();
            order.setUser(new User(userId));
            order.setQuantity(1);
            order.setOrderDate(new Date());
            OrderDetail orderDetail1 = new OrderDetail();
            orderDetail1.setProduct(new Product(productId));
            orderDetail1.setQuantity(1);
            orderService.createCart(order);
            orderDetail1.setOrder(order);
            orderDetailService.save(orderDetail1);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    // add in detail-shop
    @GetMapping("/addCart2")
    public ResponseEntity<?> addToCart2(@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer quantity) {
        List<OrderDetailDTO> orderDetailDTOList = orderService.findCartBy(userId);
        int count = 0;
        OrderDetail orderDetail = null;
        for (OrderDetailDTO detailDTO : orderDetailDTOList) {
            if (detailDTO.getProductId().equals(productId)) {
                orderDetail = this.orderDetailService.findByIdOD(detailDTO.getId());
                orderDetail.setQuantity(orderDetail.getQuantity() + quantity);
                count++;
            }
        }
        if (count == 1) {
            this.orderDetailService.save(orderDetail);
            return new ResponseEntity<>(HttpStatus.OK);
        } else if (count == 0) {
            Order order = new Order();
            order.setUser(new User(userId));
            order.setQuantity(1);
            order.setOrderDate(new Date());
            OrderDetail orderDetail1 = new OrderDetail();
            orderDetail1.setProduct(new Product(productId));
            orderDetail1.setQuantity(quantity);
            orderService.createCart(order);
            orderDetail1.setOrder(order);
            orderDetailService.save(orderDetail1);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("order-detail/{productId}/{orderId}")
    public ResponseEntity<?> deleteCartDetailByProductId(@PathVariable Long productId, @PathVariable Long orderId) {
        orderDetailService.deleteOrderDetailByProductIdAndOrderId(productId, orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/detail/{userId}")
    public ResponseEntity<?> findOrderDetailByUserId(@PathVariable Long userId) {
        List<OrderDetail> orderDetails = orderDetailService.findOrderDetailByUserId(userId);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @GetMapping("/paypal")
    public ResponseEntity<?> payPal(@RequestParam Long id) {
        orderDetailService.payPal(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateSttPayPal(@RequestParam Long odId) {
        OrderDetail orderDetail = orderDetailService.findByIdOD(odId);
        if ( orderDetail != null) {
            orderDetailService.updateSttPayPal(odId);
            return new ResponseEntity<>("Cập nhập thành công trạng thái thanh toán", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}


