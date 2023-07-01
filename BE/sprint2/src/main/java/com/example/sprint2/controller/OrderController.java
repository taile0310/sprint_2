package com.example.sprint2.controller;

import com.example.sprint2.dto.OrderDetailDTO;
import com.example.sprint2.model.*;
import com.example.sprint2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/public")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;
    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


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

    /**
     * function to get detailed order list
     *
     * @param userId
     * @return
     */
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

    /**
     * function update status payment
     *
     * @param odId
     * @return
     */
    @GetMapping("/update")
    public ResponseEntity<?> updateSttPayPal(@RequestParam Long odId) {
        OrderDetail orderDetail = orderDetailService.findByIdOD(odId);
        if (orderDetail == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        orderDetailService.updateSttPayPal(odId);
        return new ResponseEntity<>("Cập nhập thành công trạng thái thanh toán", HttpStatus.OK);
    }

    /**
     * function get list history
     *
     * @param id
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/payment-history/{id}")
    public ResponseEntity<?> getListPaymentHistory(@PathVariable("id") Long id,
                                                   @RequestParam(required = false, defaultValue = "0") int page,
                                                   @RequestParam(required = false, defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderDetailDTO> order = orderDetailService.getListPaymentHistory(id, pageable);
        if (order.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getUser(@RequestParam Long id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * function get list customer
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list-customer")
    public ResponseEntity<?> getListCustomer(@RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false, defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userService.customerPage(pageable);
        if (userPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(userPage, HttpStatus.OK);
        }
    }

    /**
     * function sign-up
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>("Tên đăng nhập đã tồn tại", HttpStatus.BAD_REQUEST);
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>("Email đã tồn tại", HttpStatus.BAD_REQUEST);
        }


        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User registeredUser = userService.registerUser(user);

        // Đặt vai trò mặc định cho tài khoản
        Role defaultRole = userService.findByIdRole(3L);
        UserRole userRole = new UserRole();

        userRole.setRole(defaultRole);
        userRole.setUser(registeredUser);

        userService.setRoleOfUser(userRole);

        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    /**
     * function: thống kê
     *
     * @param statisticalOfYear
     * @return
     */
    @GetMapping("/list-statistical")
    public ResponseEntity<?> getListStatistical(@RequestParam(required = false, defaultValue = "2023") String statisticalOfYear) {
        List<OrderDetailDTO> list = orderDetailService.getListStatistical(statisticalOfYear);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}



