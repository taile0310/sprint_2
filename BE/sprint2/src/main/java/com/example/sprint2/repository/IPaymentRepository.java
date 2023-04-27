package com.example.sprint2.repository;

import com.example.sprint2.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentRepository extends JpaRepository<Payment,Long> {
}
