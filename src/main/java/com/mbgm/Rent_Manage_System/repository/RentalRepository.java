package com.mbgm.Rent_Manage_System.repository;

import com.mbgm.Rent_Manage_System.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query("SELECT SUM(r.totalPrice) FROM Rental r")
    BigDecimal sumTotalRevenue();

    List<Rental> findTop5ByOrderByStartDateDesc();

}
