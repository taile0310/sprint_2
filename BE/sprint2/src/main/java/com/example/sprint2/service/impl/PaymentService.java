package com.example.sprint2.service.impl;

import com.example.sprint2.model.Payment;
import com.example.sprint2.repository.IPaymentRepository;
import com.example.sprint2.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService {
    @Autowired
    private IPaymentRepository paymentRepository;
    @Override
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }
}
