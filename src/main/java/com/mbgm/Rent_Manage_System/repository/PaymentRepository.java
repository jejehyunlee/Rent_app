package com.mbgm.Rent_Manage_System.repository;

import com.mbgm.Rent_Manage_System.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStatus(String status);

    Optional<Payment> findByRentalId(Long rentalId);

}
