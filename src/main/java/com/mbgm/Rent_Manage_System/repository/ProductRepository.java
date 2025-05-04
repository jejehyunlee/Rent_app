package com.mbgm.Rent_Manage_System.repository;

import com.mbgm.Rent_Manage_System.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.deleted = false")
    List<Product> findAllNotDeleted();

}

