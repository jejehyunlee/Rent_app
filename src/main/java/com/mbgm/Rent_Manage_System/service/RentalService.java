package com.mbgm.Rent_Manage_System.service;

import com.mbgm.Rent_Manage_System.entity.Product;
import com.mbgm.Rent_Manage_System.entity.Rental;
import com.mbgm.Rent_Manage_System.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;

    private final ProductService productService;

    public List<Rental> getAll() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getById(Long id) {
        return rentalRepository.findById(id);
    }

    public Rental save(Rental rental) {
        long days = ChronoUnit.DAYS.between(rental.getStartDate(), rental.getEndDate());
        if (days <= 0) {
            throw new IllegalArgumentException("End date must be after start date.");
        }

        BigDecimal pricePerDay = rental.getProduct().getDailyRate();
        BigDecimal total = pricePerDay.multiply(BigDecimal.valueOf(days));

        rental.setTotalPrice(total);
        return rentalRepository.save(rental);
    }


    public void delete(Long id) {
        rentalRepository.deleteById(id);
    }
}

