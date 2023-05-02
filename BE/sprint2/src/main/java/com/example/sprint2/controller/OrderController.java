package com.example.sprint2.controller;

import com.example.sprint2.dto.OrderDTO;
import com.example.sprint2.model.*;
import com.example.sprint2.service.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    @Autowired
    private IPaymentService paymentService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getListOrder(@PathVariable("id") Long id) {
        List<Order> order = orderService.findCartBy(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrUpdate(@RequestBody OrderDTO orderDTO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order, "userId", "productId");
        User user = userService.findById(orderDTO.getUserId());
        order.setUser(user);
        Product product = productService.findById(orderDTO.getProductId());
        OrderDetail orderDetail = new OrderDetail(product, order);
        order.setOrderDetailList(Arrays.asList(orderDetail));
        orderService.createCart(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/increase")
    public ResponseEntity<?> increaseQuantity(@RequestBody  OrderDTO orderDTO) {
        Order order = orderService.findById(orderDTO.getId());
        order.setQuantity(order.getQuantity() + 1);
        orderService.createCart(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/reduce")
    public ResponseEntity<?> reduceQuantity(@RequestBody  OrderDTO orderDTO) {
        Order order = orderService.findById(orderDTO.getId());
        order.setQuantity(order.getQuantity() - 1);
        orderService.createCart(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}


