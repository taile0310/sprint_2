package com.example.sprint2.repository;

import com.example.sprint2.model.Order;
import com.example.sprint2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserAndOrderDateIsNull(User user);
}
