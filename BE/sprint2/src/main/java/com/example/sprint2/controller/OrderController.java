package com.example.sprint2.controller;

import com.example.sprint2.model.*;
import com.example.sprint2.repository.*;
import com.example.sprint2.service.*;
import com.example.sprint2.service.impl.BadRequestException;
import com.example.sprint2.service.impl.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

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
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody Order order) throws ResourceNotFoundException {
        User user = userService.findById(order.getId());
        Product product = productService.findById(order.getId());
        System.out.println("product to add" + product.getProductName());
        orderService.addToCart(order, product, user);
        return new ResponseEntity(HttpStatus.CREATED);

    }
}
