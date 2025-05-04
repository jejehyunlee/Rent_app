package com.mbgm.Rent_Manage_System.service;

import com.mbgm.Rent_Manage_System.entity.Payment;
import com.mbgm.Rent_Manage_System.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public List<Payment> getByStatusUnpaid(String status) {
        return paymentRepository.findByStatus("UNPAID");
    }

    public List<Payment> getByStatusPaid(String status) {
        return paymentRepository.findByStatus("PAID");
    }

    public Optional<Payment> getByRentalId(Long rentalId) {
        return paymentRepository.findByRentalId(rentalId);
    }

    public Optional<Payment> getById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }
}

