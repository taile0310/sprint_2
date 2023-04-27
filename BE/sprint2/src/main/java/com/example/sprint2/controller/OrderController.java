package com.example.sprint2.controller;

import com.example.sprint2.model.*;
import com.example.sprint2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/public")
public class OrderController {
    @Autowired
    IUserRepository userRepository;
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Autowired
    private IPaymentRepository paymentRepository;

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestParam Long productId, @RequestParam Integer quantity) {
        // find the product by id
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        Product product = productOptional.get();

        // check if the product is available
        if (product.getQuantity() < quantity) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not available in requested quantity.");
        }

        // create a new order detail with the given product and quantity
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setQuantity(quantity);
        orderDetail.setPrice(product.getPrice() * quantity);

        // add the order detail to the user's cart
        User user = getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to add items to your cart.");
        }
        Order order = user.getOrder();
        if (order == null) {
            order = new Order();
            order.setUser(user);
            order.setOrderDate(new Date());
            order.setTotalAmount(0.0);
            order.setNote("Cart");
            user.setOrder(order);
        }
        order.getOrderDetailList().add(orderDetail);
        order.setTotalAmount(order.getTotalAmount() + orderDetail.getPrice());
        orderDetail.setOrder(order);

        // save the changes to the database
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        orderRepository.save(order);
        orderDetailRepository.save(orderDetail);

        return ResponseEntity.ok("Product added to cart.");
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestParam String paymentType) {
        // get the current user and their cart
        User user = getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to checkout.");
        }
        Order cart = user.getOrder();
        if (cart == null || cart.getOrderDetailList().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your cart is empty.");
        }

        // create a new payment and set the order details to it
        Payment payment = new Payment();
        payment.setType(paymentType);
        payment.setStatus("Paid");
        payment.setOrderDetails(cart.getOrderDetailList());
        for (OrderDetail orderDetail : cart.getOrderDetailList()) {
            orderDetail.setPayment(payment);
        }

        // save the payment and remove the cart from the user
        paymentRepository.save(payment);
        user.setOrder(null);
        orderRepository.delete(cart);

        return ResponseEntity.ok("Checkout successful.");
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        String username = authentication.getName();
        Optional<User> userOptional = Optional.ofNullable(userRepository.findUserByUsername(username));
        if (!userOptional.isPresent()) {
            return null;
        }
        return userOptional.get();
    }
}
