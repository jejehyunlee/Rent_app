package com.mbgm.Rent_Manage_System.repository;

import com.mbgm.Rent_Manage_System.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
