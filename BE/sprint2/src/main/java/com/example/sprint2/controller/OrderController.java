package com.example.sprint2.controller;

import com.example.sprint2.dto.OrderDetailDTO;
import com.example.sprint2.model.*;
import com.example.sprint2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/public")
public class OrderController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;


    @GetMapping("/cart/{id}")
    public ResponseEntity<?> getListOrder(@PathVariable("id") Long id) {
        List<OrderDetailDTO> order = orderService.findCartBy(id);
        if (order.isEmpty()){
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
    public ResponseEntity<?> changeQuantity(@RequestParam Long orderDetailId, @RequestParam int quantity) {
        boolean checkChangeQuantity = orderService.changeQuantity(orderDetailId, quantity);
        if (!checkChangeQuantity) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/addCart")
    public ResponseEntity<?> addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer quantity) {
        Order order = new Order();
        order.setUser(new User(userId));
        order.setQuantity(1);
//        order.setTotalAmount(productService.getProductId(productId).getPrice() * quantity);
        order.setOrderDate(new Date());
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(new Product(productId));
        orderDetail.setQuantity(1);
        orderService.createCart(order);
        orderDetail.setOrder(order);
        orderDetailService.save(orderDetail);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}


